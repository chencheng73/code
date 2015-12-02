package com.zfpt.web.service.file;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.zfpt.framework.dao.GenericDao;
import com.zfpt.framework.exception.ServiceException;
import com.zfpt.framework.model.AttachmentVo;
import com.zfpt.framework.service.GenericServiceImpl;
import com.zfpt.web.model.system.User;

/**      
 * 项目名称：zfpt   
 * 类名称：FileManageService   
 * 类描述： 附件管理业务逻辑层  
 * 创建人：chens
 * 创建时间：2015年11月24日 下午3:20:10   
 * 修改备注：   
 * @version      
 */
@Service("fileService")
public class AttachmentService extends GenericServiceImpl{
  @Resource(name="baseDao")
  private GenericDao baseDao;  
  
  /**
   * 方法名称: insertFlie
   * 方法描述: 根据业务id获取附件列表
   * 返回类型: String
   * 创建人：chens
   * 创建时间：2015年11月24日 下午5:47:24
   * @throws
   */
  public List<AttachmentVo> queryAttachmentForList(AttachmentVo attachmentVo)throws ServiceException{
	 return  baseDao.selectList(attachmentVo);
  }
  
  /**
   * 方法名称: insertAttachment
   * 方法描述: 保存附件信息
   * 返回类型: int
   * 创建人：chens
   * 创建时间：2015年11月24日 下午5:47:24
   * @throws
   */
  public int insertAttachment(AttachmentVo attachmentVo)throws ServiceException{
	  return  baseDao.insert(attachmentVo);
  }
  /**
   * 批量保存附件
   * @param attachmentVos
   * @return
   * @throws ServiceException
   */
  public int batchInsertAttachment(List<AttachmentVo> attachmentVos)throws ServiceException{
	  return baseDao.batchInsert(AttachmentVo.class,attachmentVos);
  }
  
  /**
   * 方法名称: updateAttachment
   * 方法描述: 修改附件信息
   * 返回类型: int
   * 创建人：chens
   * 创建时间：2015年11月24日 下午5:47:24
   * @throws
   */
  public int updateAttachment(AttachmentVo attachmentVo)throws ServiceException{
	  return baseDao.update(attachmentVo);
  }
  
  /**
   * 方法名称: queryAttachmentById
   * 方法描述: 根据附件id，查询明细
   * 返回类型: AttachmentVo
   * 创建人：chens
   * 创建时间：2015年11月24日 下午5:54:53
   * @throws
   */
  public AttachmentVo queryAttachmentById(String  id)throws ServiceException{
	  Object object=baseDao.selectOne(AttachmentVo.class,id);
	  return  object!=null?(AttachmentVo)object:null;
  }
  
  /**
   * 方法名称: deleteAttachmentById
   * 方法描述: 根据主键id删除附件信息
   * 返回类型: int
   * 创建人：chens
   * 创建时间：2015年11月24日 下午5:51:24
   * @throws
   */
  public int deleteAttachmentById(String id){
	  return baseDao.delete(AttachmentVo.class, id);
  }
  
  /**
   * 方法名称: deleteAttachmentById
   * 方法描述: 根据业务关联主键bizid删除附件信息
   * 返回类型: String
   * 创建人：chens
   * 创建时间：2015年11月24日 下午5:51:24
   * @throws
   */
  public String deleteAttachmentByBizId(AttachmentVo attachmentVo){
	  baseDao.delete(attachmentVo);
	  return attachmentVo.getPkId();
  }
  
}
