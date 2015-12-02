package com.zfpt.framework.service;

import java.util.List;
import java.util.Map;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import com.zfpt.framework.dao.GenericDao;
import com.zfpt.framework.exception.DaoException;
import com.zfpt.framework.exception.ServiceException;
import com.zfpt.framework.filter.Pager;

/**
 * 项目名称：zfpt   
 * 类名称：GenericServiceImpl   
 * 类描述：GenericService的实现类, 其他的自定义 ServiceImpl, 继承自它,可以获得常用的增删查改操作,
 * 未实现的方法有 子类各自实现
 * T : 代表数据库中的表 映射的Java对象类型
 * PK :代表对象的主键类型   
 * 创建人：chens
 * 创建时间：2015年11月25日 上午11:37:38   
 * 修改备注：   
 * @version
 */
public abstract      class GenericServiceImpl implements GenericService {
	@Autowired
	private GenericDao baseDao;
 
	public <T extends Object> int insert(T model)throws ServiceException {
		return baseDao.insert(model);
	}

	public <T extends Object> int update(T model)throws ServiceException {
		return baseDao.update(model);
	}

	public <Model extends Object> int batchInsert(Class<?> claz, List<Model> models) throws DaoException {
		 
		return baseDao.batchInsert(claz, models);
	}

	public <PK> int delete(Class<?> claz, PK pk) throws DaoException {
		return  baseDao.delete(claz, pk);
	}

	public <Model extends Object> Model selectOne(Model model) throws DaoException {
		return baseDao.selectOne(model);
	}

	public <Model extends Object> List<Model> selectList(Model model) throws DaoException {
		return baseDao.selectList(model);
	}

	public List<Map<String, Object>> selectListMap(Class<?> claz, Map<String, Object> dataMap) throws DaoException {
		return baseDao.selectListMap(claz, dataMap);
	}

	public <Model> Pager<List<Model>> queryForPage(Class<?> claz, Pager<List<Model>> page) throws ServiceException {
		return baseDao.queryForPage(claz, page);
	}

	public <Model> Boolean queryIsUnique(Class<?> claz,Map<String,Object> queryMap) {
		return baseDao.queryIsUnique(claz,queryMap);
	}

	public <Model> Model selectOne(Class<?> claz, Map<String, Object> valueMap) throws DaoException {
		return baseDao.selectOne(claz, valueMap);
	}

	public SqlSessionTemplate getSqlSessionTemplate() {
		return baseDao.getSqlSessionTemplate();
	}

	public <PK> Object selectOne(Class<?> claz, PK pk) throws DaoException {
		return baseDao.selectOne(claz, pk);
	}
	
 
}
