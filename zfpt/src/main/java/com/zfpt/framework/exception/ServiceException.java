package com.zfpt.framework.exception;
/**
 * 项目名称：zfpt   
 * 类名称：ServiceException   
 * 类描述：业务层异常类  
 * 创建人：chens
 * 创建时间：2015年11月23日 下午5:24:07   
 * 修改备注：   
 * @version
 */
public class ServiceException extends BaseException {
	private static final long serialVersionUID = 1L;

	public ServiceException(){
		super();
	}
	
	public ServiceException( Throwable e ){
		super( e );
	}
	
	public ServiceException( String msg , Throwable e  ){
		super( msg ,  e ) ;
	}
}
