package com.zfpt.web.common;

/**
 * 项目名称：zfpt   
 * 类名称：MapperNameSpace   
 * 类描述：存储所有的sqlMapper的命名空间namespace   
 * 创建人：chens
 * 创建时间：2015年11月23日 下午3:36:03   
 * 修改备注：   
 * @version
 */
public interface MapperNameSpace {
	
	public final static String dot=".";
	/**新增标记 **/
	public final static String mapper_save=dot+"save";
	/**批量新增标记 **/
	public final static String mapper_batch_save=dot+"batchSave";
	/**修改标记 **/
	public final static String mapper_update=dot+"update";
	/**根据主键删除 **/
	public final static String mapper_delete=dot+"delete";
	/**根据实例参数删除 **/
	public final static String mapper_deleteByExample=dot+"deleteByExample";
	/**根据主键集合批量删除 **/
	public final static String mappper_batch_delete=dot+"batchDelete";
	/**根据主键查询明细 **/
	public final static String mapper_selectOne=dot+"selectOne";
	/**根据实例参数查询明细 **/
	public final static String mapper_findByExample=dot+"findByExample";
	/**根据传入的Map值，返回明细 **/
	public final static String mapper_findByMapValues=dot+"findByMapValues";
	/**列表查询标记 **/
	public final static String mapper_selectList=dot+"selectList";
	/**分页查询标记 **/
	public final static String mapper_listPage=dot+"listPage";
	/**验证唯一性 **/
	public final static String mapper_queryUnique=dot+"queryUnique";
	
}
