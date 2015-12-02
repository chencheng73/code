package com.zfpt.framework.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.zfpt.framework.model.AttachmentVo;
import com.zfpt.web.common.AppCons;

/**
 * 项目名称：PPSPV1.0   
 * 类名称：FileUtils   
 * 类描述：附件上传/下载工具类
 * web基于Spring mvc  
 * 创建人：chens
 * 创建时间：2014-8-24 下午4:12:12   
 * 修改备注：   
 * @version
 */
public class FileUtils  {
 
	/**
	 * 方法名称: upload ；
	 * 方法描述: 附件上传；
	 * 返回类型: List<AttachmentVo> ；
	 * 创建人：chens  ；
	 * 创建时间：2014-8-24 下午5:01:15；
	 * @throws
	 */
    public  static List<AttachmentVo> upload(MultipartHttpServletRequest  request,String bizCode) throws IOException{
    	/**附件目录**/
    	String dirPath=AppCons.file_upload_path;
    	if(StringUtils.isEmpty(dirPath)){
    		dirPath=request.getSession().getServletContext().getRealPath("/");
    	}
    	/**返回上传的附件信息**/
    	List<AttachmentVo> attachmentVos=new ArrayList<AttachmentVo>();
    	/**获取上传附件**/
        Map<String, List<MultipartFile>> fileMap = request.getMultiFileMap();  
        String uploadDir = uploadPath(dirPath,bizCode);
        String fileName = null;  
        StringBuffer   ids=new StringBuffer();
        for (Iterator<Entry<String, List<MultipartFile>>> iterator = fileMap.entrySet().iterator(); iterator.hasNext();) {
        	 Entry<String, List<MultipartFile>> entry = iterator.next();  
        	 List<MultipartFile> fileList = entry.getValue();  
        	 for(MultipartFile mFile : fileList){
        		 fileName = mFile.getOriginalFilename();
        		 String storeName=null;
    			 if(StringUtils.isNotEmpty(fileName)){
    				 storeName = rename(fileName);  /**重命名 **/
        			 String storePath=uploadDir+File.separator+storeName;  /**文件路径 **/
    				 copyFile(storePath, mFile.getInputStream());
    				 ids.append(UUID.randomUUID().toString());
    				 pushAttachment(attachmentVos,bizCode,fileName,storeName,uploadDir);
        		 }
        	 }
		}
        return attachmentVos;
    }
    /**
     * 方法名称: pushAttachment 
     * 方法描述: 封装AttachmentVo 
     * 返回类型: String 
     * 创建人：chens  
     * 创建时间：2014-8-24 下午4:38:13
     * @throws
     */
    private static void pushAttachment(List<AttachmentVo> attachmentVos,String bizCode,String fileName,String storeName,String uploadDir){
		/**分配id**/
		String id =UUIDUtils.getUUID2String();
		AttachmentVo attachmentVo = new AttachmentVo();
		if(StringUtils.isNotEmpty(storeName)){
			attachmentVo.setPkId( id );
			attachmentVo.setMod_code( bizCode );
			attachmentVo.setCreatedby("admin") ;//TODO:设置创建人编码
			attachmentVo.setCreateAt(DateUtils.currentDatetime());
			attachmentVo.setOriginal_name( fileName ) ;
			attachmentVo.setPath(uploadDir+File.separator+storeName) ;
			int lastPointIndex = storeName.lastIndexOf( "." );
			if( lastPointIndex > -1 && lastPointIndex <  ( storeName.length() - 1 ) ){
				attachmentVo.setFile_type( storeName.substring( lastPointIndex + 1 )  ) ;
			}
		}
		attachmentVos.add(attachmentVo);
	}
    /**
	 * 方法名称: uploadPath ；
	 * 方法描述: 获取附件路径；
	 * 返回类型: String ；
	 * 创建人：chens  ；
	 * 创建时间：2014-8-20 上午11:09:22；
	 * @throws
	 */
	private static String uploadPath(String dirPath,String bizCode){
	    String uploadPath=bizCode+File.separator;
		File f = new File(AppCons.file_upload_path+uploadPath);
		if(!f.exists() ){
		   f.mkdirs(); 
		}
		return uploadPath;
	}
	/**
	 * 方法名称: rename ；
	 * 方法描述: 附件重命名   ；
	 * 返回类型: String ；
	 * 创建人：chens  ；
	 * 创建时间：2014-8-22 下午9:26:12；
	 * @throws
	 */
	 private static String rename(String name) {  
		String surrString=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());   
        return surrString+"_"+name;  
    }  
	/**
	 * 方法名称: copyFile ；
	 * 方法描述: 写入文件 ；
	 * 返回类型: void ；
	 * 创建人：chens  ；
	 * 创建时间：2014-8-22 下午9:39:55；
	 * @throws
	 */
	private static void copyFile(String filePath, InputStream inputStream) throws IOException{
	    FileOutputStream fileOutputStream = null;
	    try {
	     	 fileOutputStream = new FileOutputStream(AppCons.file_upload_path+filePath);
	         byte[] tmp = new byte[1024];
	         int len = -1;
	         while ((len = inputStream.read(tmp)) != -1)
	           fileOutputStream.write(tmp, 0, len);
	         }
	       catch (FileNotFoundException e) {
	          e.printStackTrace();
	       } finally {
	      if (fileOutputStream != null) {
	        fileOutputStream.close();
	      }
	      if (inputStream != null)
	        inputStream.close();
	    }
	}
	
	 
	/**
	 * @throws IOException 
	 * 方法名称: downloadFile ；
	 * 方法描述: 下载附件；
	 * 返回类型: void ；
	 * 创建人：chens  ；
	 * 创建时间：2014-8-20 上午11:20:33；
	 * @throws
	 */
	public static void downloadFile(String  path,HttpServletResponse response) throws IOException{
	    File file=new File(path);
		String fileName = URLEncoder.encode( file.getName() , "UTF-8") ;
		response.setContentType("application/x-download; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.addHeader("Content-Length", "" + file.length());
        OutputStream outputStream = response.getOutputStream();
        InputStream inputStream = null;
        try{
	         inputStream = new FileInputStream( file );
	         byte[] buffer = new byte[1024];
	         int i = -1;
	         while((i = inputStream.read(buffer)) != -1) {
	        	 outputStream.write(buffer, 0, i);
	         }
        } catch (Exception e) {
			e.printStackTrace();
		}
        finally{
       	 if( inputStream != null ){
       		 inputStream.close();
       	 }
        }
        outputStream.flush();
        outputStream.close();
	}
	/**
	 * 方法名称: forceDeleteFile ；
	 * 方法描述: 物理删除附件；
	 * 返回类型: boolean ；
	 * 创建人：chens  ；
	 * 创建时间：2014-8-24 下午11:18:42；
	 * @throws
	 *//*
	public  static boolean forceDeleteFile(AttachmentVo attachmentVo){
		try {
			String dirPath=BaseDataHelper.getNameByCode(BusinessCons.UPLOAD_FILE, BusinessCons.UPLOAD_FILE_PATH);
	    	if(com.rayootech.ppsp.common.util.StringUtils.isEmpty(dirPath)){
	    		dirPath=getRequest().getSession().getServletContext().getRealPath("/");
	    	}
		    File temFile=new File(dirPath+attachmentVo.getPath());
		    org.apache.commons.io.FileUtils.forceDelete(temFile);
		} catch (IOException e) {
			log.error( "文件删除异常" , e );
			return false ;
		}
		return true ;
	}*/
	

}
