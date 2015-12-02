package com.zfpt.framework.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;

import com.zfpt.framework.exception.DaoException;
import com.zfpt.framework.exception.ServiceException;
import com.zfpt.framework.filter.Pager;

/**
 * 项目名称：zfpt   
 * 类名称：GenericDao   
 * 类描述：数据访问层接口  
 * 创建人：chens
 * 创建时间：2015年11月23日 下午3:19:42   
 * 修改备注：   
 * @version
 */
public interface GenericDao {

	/**
     * 新增对象
     * @param model对象
     */
    <Model extends Object> int insert(Model model) throws DaoException;
    
    /**
     * 批量新增对象
     * @param model对象
     */
    <Model extends Object> int batchInsert(Class<?> claz,List<Model> models) throws DaoException;

    /**
     * 更新对象
     * @param model 对象
     */
    <Model extends Object> int update(Model model)throws DaoException;

    /**
     * 通过主键, 删除对象
     * @param id 主键
     */
    <PK extends Object>  int delete(Class<?> claz,PK pk)throws DaoException;
    
    <Model extends Object> int delete(Model model)throws DaoException;

    /**
     * 通过主键, 查询对象
     * @param Model 对象
     * @return model 对象
     */
    <Model extends Object> Model selectOne(Model model)throws DaoException;
    
    
    <PK extends Object> Object selectOne(Class<?> claz,PK pk)throws DaoException;
    
   <Model> Model selectOne(Class<?> claz,Map<String,Object> valueMap)throws DaoException;


    /**
     * 查询多个对象
     * @return 对象集合
     */
    <Model extends Object> List<Model>  selectList(Model model)throws DaoException;
    
    /**
     * 返回Map集合
     * @return 对象集合
     */
    List<Map<String,Object>>  selectListMap(Class<?> claz,Map<String,Object> dataMap)throws DaoException;
    
    /**
     * 返回分页数据
     * @return 对象集合
     */
    public  <Model extends Object> Pager<List<Model>>  queryForPage(Class<?> claz,Pager<List<Model>> page) throws ServiceException;
    
    /**
     * 验证唯一性
     * @return true/false
     **/
    public <Model extends Object> Boolean queryIsUnique(Class<?> claz,Map<String,Object> queryMap);
    
	public SqlSessionTemplate getSqlSessionTemplate();
}
