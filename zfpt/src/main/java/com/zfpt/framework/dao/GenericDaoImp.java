package com.zfpt.framework.dao;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import com.zfpt.framework.exception.DaoException;
import com.zfpt.framework.exception.ServiceException;
import com.zfpt.framework.filter.Pager;
import com.zfpt.web.common.MapperNameSpace;
/**
 * 项目名称：zfpt   
 * 类名称：GenericDaoImp   
 * 类描述：数据访问层实现   
 * 创建人：chens
 * 创建时间：2015年11月23日 下午3:20:41   
 * 修改备注：   
 * @version
 */
@SuppressWarnings("restriction")
@Repository("baseDao")
public  class GenericDaoImp  implements GenericDao,MapperNameSpace {
	
	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;


	public <Model> int insert(Model model) throws DaoException {
		return this.sqlSessionTemplate.insert(getModelName(model)+mapper_save, model);
	}
	
	public <Model> int batchInsert(Class<?> claz, List<Model> models) throws DaoException {
		return this.sqlSessionTemplate.insert(claz.getName()+mapper_batch_save, models);
	}


	public <Model> int update(Model model) throws DaoException {
		return this.sqlSessionTemplate.update(getModelName(model)+mapper_update, model);
	}

	public <PK extends Object> int delete(Class<?> claz,PK pk) throws DaoException {
		return this.sqlSessionTemplate.delete(claz.getName()+mapper_delete, pk);
	}

	public <Model> Model selectOne(Model model) throws DaoException {
		return this.sqlSessionTemplate.selectOne(getModelName(model)+mapper_findByExample,model);
	}


	public <PK> Object selectOne(Class<?> claz, PK pk) throws DaoException {
		return this.sqlSessionTemplate.selectOne(claz.getName()+mapper_selectOne,pk);
	}

	public <Model> Model selectOne(Class<?> claz,Map<String,Object> valueMap) throws DaoException {
		return this.sqlSessionTemplate.selectOne(claz.getName()+mapper_findByMapValues, valueMap);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public  <Model extends Object> Pager<List<Model>>  queryForPage(Class<?> claz,Pager<List<Model>> page) throws ServiceException {
	 	List<Model> list= this.sqlSessionTemplate.selectList(claz.getName()+mapper_listPage, page);
	 	if(list.size()>0){
	 		page.setResult(list);
	 		return page;
	 	}else{
	 		return new Pager();
	 	}
	}

	public <Model extends Object> List<Model> selectList(Model model) throws DaoException {
		return this.sqlSessionTemplate.selectList(getModelName(model)+mapper_selectList, model);
	}


	public  <Model extends Object> Boolean queryIsUnique(Class<?> claz,Map<String,Object> queryMap) {
		Integer result=this.sqlSessionTemplate.selectOne(claz.getName()+mapper_queryUnique,queryMap);
		return result>0?false:true;
	}
	
	/**
     * 返回实体名称
     */
	private static String getModelName(Object t){
		return t.getClass().getName();
	}


	public List<Map<String, Object>> selectListMap(Class<?> claz, Map<String, Object> dataMap) throws DaoException {
		return null;
	}
  
	/**
	 * 批量更新
	 * @param str
	 * @param obj
	 * @return
	 * @throws DaoException
	 */
	@SuppressWarnings("rawtypes")
	public void batchUpdate(String str, List objs )throws DaoException{
		SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
		/**批量执行器 **/
		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
		try{
			if(objs!=null){
				for(int i=0,size=objs.size();i<size;i++){
					sqlSession.update(str, objs.get(i));
				}
				sqlSession.flushStatements();
				sqlSession.commit();
				sqlSession.clearCache();
			}
		}finally{
			sqlSession.close();
		}
	}

	public <Model> int delete(Model model) throws DaoException {
		return 0;
	}

	public SqlSessionTemplate getSqlSessionTemplate() {
		return this.sqlSessionTemplate;
	}
}
