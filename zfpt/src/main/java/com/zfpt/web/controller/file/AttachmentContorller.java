package com.zfpt.web.controller.file;

import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.zfpt.framework.model.AttachmentVo;
import com.zfpt.framework.util.ExportExcelUtils;
import com.zfpt.framework.util.FileUtils;
import com.zfpt.framework.util.ImportExcelUtils;
import com.zfpt.web.common.AppCons;
import com.zfpt.web.common.ResponseResult;
import com.zfpt.web.controller.system.UserController;
import com.zfpt.web.service.file.AttachmentService;

/**      
 * 项目名称：zfpt   
 * 类名称：FileManageContorller   
 * 类描述： 附件管理控制器   
 * 创建人：chens
 * 创建时间：2015年11月24日 下午3:17:25   
 * 修改备注：   
 * @version      
 */
@Controller 
@RequestMapping("/file")
public class AttachmentContorller  {
	private Log logger=LogFactory.getLog(UserController.class);
	 @Resource(name="fileService")
	 private AttachmentService fileService;  
	
	/**
     * 方法名称: importExcel
     * 方法描述: 导入excel
     * 返回类型: ResponseResult<User>
     * 创建人：chens
     * 创建时间：2015年11月24日 上午10:38:55
     * @throws
     */
    @RequestMapping(value="/import",method=RequestMethod.POST)
   	public @ResponseBody ResponseResult<String>  importExcel(@RequestParam MultipartFile excelData){
    	ImportExcelUtils imExcelTemplate =null;
    	List<String []> dataList=null;
    	ResponseResult<String> responseResult=new ResponseResult<String>(); 
    	if(excelData!=null){
    	  try{
    		  /**初始化文件 **/
    		  imExcelTemplate=new ImportExcelUtils(excelData);
    		  /**读取文件数据 **/
    		  dataList = imExcelTemplate.readFile2(1);
    		  if(!dataList.isEmpty()&&dataList.size()>0){
    			 for(String [] params: dataList) {
					 System.out.println(params[0]+params[1]+params[2]);
				} 
    		  }
    		  responseResult.setData("文件导入成功!");
		  } catch (IOException e) {
			  logger.error("文件导入失败!");
			  responseResult.setErrorMessage("文件导入失败:"+e);
		  } 
    	}
    	return responseResult;
   	}
    
    
    /**
     * 方法名称: exportExcel
     * 方法描述: 导出excel
     * 返回类型: ResponseResult<User>
     * 创建人：chens
     * 创建时间：2015年11月24日 上午10:39:07
     * @throws
     */
    @RequestMapping(value="/export",method=RequestMethod.GET)
   	public @ResponseBody ResponseResult<String>  testExportExcel(HttpServletResponse response)throws Exception{
    	ResponseResult<String> responseResult=new ResponseResult<String>(); 
    	String templatePath=AppCons.system_path+"\\WEB-INF\\classes\\template\\监管主体数据模板.xlsx";
    	ExportExcelUtils excelTemplate =new ExportExcelUtils(templatePath);
		/**写入数据 **/
		for (int i = 1; i <=5; i++) {
			excelTemplate.createRow(i); 
			excelTemplate.setCell(0,i);/**序号**/
			excelTemplate.setCell(1,"test"+i);/**编号**/
			excelTemplate.setCell(2,"企业名称"+i);/**企业名称*
			excelTemplate.setCell(3, "其它信息"+i);/**其它信息**/
		}
	    excelTemplate.export(response, "监管主体数据列表");//导出开始
    	return responseResult;
   	}
    
    /**
     * 方法名称: fileDownLoad
     * 方法描述: 文件下载
     * 返回类型: ResponseResult<AttachmentVo>
     * 创建人：chens
     * 创建时间：2015年11月24日 上午10:39:20
     * @throws
     */
    @RequestMapping(value="/down/{id}")
   	public @ResponseBody ResponseResult<AttachmentVo>  testFileDownLoad(HttpServletResponse response,@PathVariable String id){
    	ResponseResult<AttachmentVo> responseResult=new ResponseResult<AttachmentVo>();
    	/**获取附件明细 **/
    	AttachmentVo attachmentVo=fileService.queryAttachmentById(id);
    	/**删除附件信息 **/
    	/** fileService.deleteAttachmentById(id);**/
    	/**获取附件列表 **/
    	/**List<AttachmentVo> list=fileService.queryAttachmentForList(attachmentVo);**/
    	if(attachmentVo!=null){
    		String path=AppCons.file_upload_path+attachmentVo.getPath() ;//AppCons.system_path+"\\WEB-INF\\classes\\template\\监管主体数据模板.xlsx";
        	try{
    			FileUtils.downloadFile(path, response);
    		} catch (IOException e) {
    			responseResult.setErrorMessage("下载附件异常："+e);
    		};	
    	}else{
    		responseResult.setErrorMessage("找不到对应的附件");
    	}
    	return responseResult;
   	}
    
    /**
     * 方法名称: fileUpload
     * 方法描述: 文件上传
     * 返回类型: ResponseResult<List<AttachmentVo>>
     * 创建人：chens
     * 创建时间：2015年11月24日 上午10:39:31
     * @throws
     */
    @RequestMapping(value="/upload",method=RequestMethod.POST)
   	public @ResponseBody ResponseResult<List<AttachmentVo>>  fileUpload(MultipartHttpServletRequest request){
    	ResponseResult<List<AttachmentVo>> responseResult=new ResponseResult<List<AttachmentVo>>(); 
    	List<AttachmentVo>  attachmentVos = null;
    	try{
    		attachmentVos=FileUtils.upload(request, AppCons.file_mod_code);
    		fileService.batchInsertAttachment(attachmentVos);
    		responseResult.setData(attachmentVos);
		} catch (IOException e) {
			logger.error("上传附件异常:"+e); 
			responseResult.setErrorMessage("上传附件异常："+e);
		}
    	return responseResult;
   	}
}
