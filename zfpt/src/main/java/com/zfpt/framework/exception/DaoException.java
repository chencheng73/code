package com.zfpt.framework.exception;
/**
 * 项目名称：zfpt   
 * 类名称：DaoException   
 * 类描述： 数据访问层异常类  
 * 创建人：chens
 * 创建时间：2015年11月23日 下午5:24:55   
 * 修改备注：   
 * @version
 */
public class DaoException extends BaseException {
		
	private static final long serialVersionUID = 1L;

	public DaoException(){
		super();
	}
	
	public DaoException( Throwable e ){
		super( e );
	}
	
	public DaoException( String msg , Throwable e  ){
		super( msg ,  e ) ;
	}
}
