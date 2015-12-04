package com.zfpt.framework.filter;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import javax.xml.bind.PropertyException;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import com.zfpt.framework.util.ReflectHelper;
import com.zfpt.framework.util.StringUtils;
/**
 * 项目名称：zfpt   
 * 类名称：PagePlugin   
 * 类描述：自定义分页插件   
 * 创建人：chens
 * 创建时间：2015年11月23日 下午2:15:36   
 * 修改备注：   
 * @version
 */
@Intercepts({@Signature(type=StatementHandler.class,method="prepare",args={Connection.class})})
public class PagePlugin implements Interceptor {

	private static String dialect = "";	//数据库方言
	private static String pageSqlId = ""; //mapper.xml中需要拦截的ID(正则匹配)
	
	public Object intercept(Invocation ivk) throws Throwable {
		if(ivk.getTarget() instanceof RoutingStatementHandler){
			RoutingStatementHandler statementHandler = (RoutingStatementHandler)ivk.getTarget();
			BaseStatementHandler delegate = (BaseStatementHandler) ReflectHelper.getValueByFieldName(statementHandler, "delegate");
			MappedStatement mappedStatement = (MappedStatement) ReflectHelper.getValueByFieldName(delegate, "mappedStatement");
			if(mappedStatement.getId().matches(pageSqlId)){ //拦截需要分页的SQL
				BoundSql boundSql = delegate.getBoundSql();
				Object parameterObject = boundSql.getParameterObject();//分页SQL<select>中parameterType属性对应的实体参数，即Mapper接口中执行分页方法的参数,该参数不得为空
				if(parameterObject==null){
					throw new NullPointerException("parameterObject尚未实例化！");
				}else{
					Connection connection = (Connection) ivk.getArgs()[0];
					String sql = boundSql.getSql();
					String countSql = "select count(0) from "+suffixStr(removeOrderBys(sql)); //记录统计 == oracle 加 as 报错(SQL command not properly ended)
					
					
					PreparedStatement countStmt = connection.prepareStatement(countSql);
					BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(),countSql,boundSql.getParameterMappings(),parameterObject);
					setParameters(countStmt,mappedStatement,countBS,parameterObject);
					ResultSet rs = countStmt.executeQuery();
					int count = 0;
					if (rs.next()) {
						count = rs.getInt(1);
					}
					rs.close();
					countStmt.close();
					Pager page = null;
					if(parameterObject instanceof Pager){	//参数就是Page实体
						 page = (Pager) parameterObject;
						 page.setTotalCount(count);
					}else{	//参数为某个实体，该实体拥有Page属性
						Field pageField = ReflectHelper.getFieldByFieldName(parameterObject,"page");
						if(pageField!=null){
							page = (Pager) ReflectHelper.getValueByFieldName(parameterObject,"page");
							if(page==null)
							   page = new Pager();
							   page.setTotalCount(count);
							ReflectHelper.setValueByFieldName(parameterObject,"page", page); //通过反射，对实体对象设置分页对象
						}else{
							throw new NoSuchFieldException(parameterObject.getClass().getName()+"不存在 page 属性！");
						}
					}
					String pageSql = generatePageSql(sql,page);
					ReflectHelper.setValueByFieldName(boundSql, "sql", pageSql); //将分页sql语句反射回BoundSql.
				}
			}
		}
		return ivk.proceed();
	}

	
	/**
	 * 对SQL参数(?)设值,参考org.apache.ibatis.executor.parameter.DefaultParameterHandler
	 * @param ps
	 * @param mappedStatement
	 * @param boundSql
	 * @param parameterObject
	 * @throws SQLException
	 */
	private void setParameters(PreparedStatement ps,MappedStatement mappedStatement,BoundSql boundSql,Object parameterObject) throws SQLException {
		ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		if (parameterMappings != null) {
			Configuration configuration = mappedStatement.getConfiguration();
			TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
			MetaObject metaObject = parameterObject == null ? null: configuration.newMetaObject(parameterObject);
			for (int i = 0; i < parameterMappings.size(); i++) {
				ParameterMapping parameterMapping = parameterMappings.get(i);
				if (parameterMapping.getMode() != ParameterMode.OUT) {
					Object value;
					String propertyName = parameterMapping.getProperty();
					PropertyTokenizer prop = new PropertyTokenizer(propertyName);
					if (parameterObject == null) {
						value = null;
					} else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
						value = parameterObject;
					} else if (boundSql.hasAdditionalParameter(propertyName)) {
						value = boundSql.getAdditionalParameter(propertyName);
					} else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX)&& boundSql.hasAdditionalParameter(prop.getName())) {
						value = boundSql.getAdditionalParameter(prop.getName());
						if (value != null) {
							value = configuration.newMetaObject(value).getValue(propertyName.substring(prop.getName().length()));
						}
					} else {
						value = metaObject == null ? null : metaObject.getValue(propertyName);
					}
					TypeHandler typeHandler = parameterMapping.getTypeHandler();
					if (typeHandler == null) {
						throw new ExecutorException("There was no TypeHandler found for parameter "+ propertyName + " of statement "+ mappedStatement.getId());
					}
					typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());
				}
			}
		}
	}
	
	/**
	 * 根据数据库方言，生成特定的分页sql
	 * @param sql
	 * @param page
	 * @return
	 */
	private String generatePageSql(String sql,Pager page){
		if(page!=null && StringUtils.isNotEmpty(dialect)){
			StringBuffer pageSql = new StringBuffer();
			if("mysql".equals(dialect)){
				pageSql.append(sql);
				pageSql.append(" limit "+page.getOffset()+","+page.getPageSize());
			}else if("oracle".equals(dialect)){
				pageSql.append("select * from (select tmp_tb.*,ROWNUM row_id from (");
				pageSql.append(sql);
				pageSql.append(") tmp_tb where ROWNUM<=");
				pageSql.append(page.getOffset()+page.getPageSize());
				pageSql.append(") where row_id>");
				pageSql.append(page.getOffset());
			}
			return pageSql.toString();
		}else{
			return sql;
		}
	}
	
	/**
	   *   select
	     *  id,
		 * 	articleNo,
		 * sum(ddd) ss,
		 * 	articleName,
	     *  (SELECT loginName from ly_userinfo u where u.id=userId) loginName,
		 * 	(SELECT userName from ly_userinfo u where u.id=userId) userName,
		 * sum(ddd) ss
		 * from article	
		 * 兼容以上子查询
		 * //去除sql ..from 前面的字符。考虑 aafrom fromdd 等等情况
	   */
	public static String suffixStr(String toSql){
		toSql=toSql.toLowerCase();
		int sun = toSql.indexOf("from ");
		String f1 = toSql.substring(sun-1,sun);
		String f2 = toSql.substring(sun+4,sun+5);
		if(f1.trim().isEmpty()&&f2.trim().isEmpty()){//判断第一个from的前后是否为空
			String s1 = toSql.substring(0,sun);
			int s0 =s1.indexOf("(");
			if(s0>-1){
				int se1 =s1.indexOf("select");
				if(s0<se1){
					if(se1>-1){
						String ss1 = s1.substring(se1-1,se1);
						String ss2 = s1.substring(se1+6,se1+7);
						if(ss1.trim().isEmpty()&&ss2.trim().isEmpty()){//判断第一个from的前后是否为空
							return suffixStr(toSql.substring(sun+5));
						}
					}
				}	
				int se2 =s1.indexOf("(select");
					if(se2>-1){
						String ss2 = s1.substring(se2+7,se2+8);
						if(ss2.trim().isEmpty()){//判断第一个from的前后是否为空
							return suffixStr(toSql.substring(sun+5));
						}
					}
					if(se1==-1&&se2==-1){
						return toSql.substring(sun+5);
					}else{
						toSql=toSql.substring(sun+5);
					}
			}else{
				toSql=toSql.substring(sun+5);
			}
		}
		return toSql;
	}
	
	 /** 
	   * 去除Sql的orderBy。 
	   * @param toSql 
	   * @return String
	   *
	   */  
	  private static String removeOrderBys(String toSql) {  
		  	toSql=toSql.toUpperCase();
		  	int sun = toSql.indexOf("order");
		  	if(sun>-1){
		  	  	String f1 = toSql.substring(sun-1,sun);
		  		String f2 = toSql.substring(sun+5,sun+5);
		  		if(f1.trim().isEmpty()&&f2.trim().isEmpty()){//判断第一个from的前后是否为空
		  		  	String zb = toSql.substring(sun);
		  		  	int s0 =zb.indexOf(")");
		  		  	if(s0>-1){//from之前是否有括号
		  		  		String s1=toSql.substring(0,sun);
		  		  		String s2 =zb.substring(s0);
		  		  		return removeOrderBys(s1+s2);
		  		  	}else{
		  		  		toSql=toSql.substring(0,sun);
		  		  	}
		  		}
		  	}
			return toSql;
	  }
		
	
	public Object plugin(Object arg0) {
		return Plugin.wrap(arg0, this);
	}

	public void setProperties(Properties p) {
		dialect = p.getProperty("dialect");
		if (StringUtils.isEmpty(dialect)) {
			try {
				throw new PropertyException("dialect property is not found!");
			} catch (PropertyException e) {
				e.printStackTrace();
			}
		}
		pageSqlId = p.getProperty("pageSqlId");
		if (StringUtils.isEmpty(pageSqlId)) {
			try {
				throw new PropertyException("pageSqlId property is not found!");
			} catch (PropertyException e) {
				e.printStackTrace();
			}
		}
	}
	
}
