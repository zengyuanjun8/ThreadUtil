package com.zyj.exception.util;


public class DefaultExceptionMsgHandler implements ExceptionMessageFormat {

	private DefaultExceptionMsgHandler() {
	}
	
	private static class SingletonHolder{
		private static final DefaultExceptionMsgHandler instance = new DefaultExceptionMsgHandler();
	}
	
	public static DefaultExceptionMsgHandler getInstance(){
		return SingletonHolder.instance;
	}
	
	@Override
	public String formate(Exception e) {
		return e.getMessage() + "\n";
	}

}
