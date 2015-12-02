package com.zfpt.framework.exception;

/**
 * 基础异常
 */
public class BaseException extends RuntimeException {

	private static final long serialVersionUID = -1527774656241007450L;
 
	public BaseException(){
		super();
	}
	
	public BaseException( Throwable e ){
		super( e );
	}
	
	public BaseException( String msg , Throwable e  ){
		super( msg ,  e ) ;
	}
	
	public BaseException( String msg ){
		super( msg ) ;
	}
	
}
