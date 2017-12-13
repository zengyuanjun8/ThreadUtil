package com.zyj.exception.util.factory;

import com.zyj.exception.util.DefaultExceptionMsgHandler;
import com.zyj.exception.util.ExceptionMessageFormat;
import com.zyj.exception.util.StackTraceMsgHandler;

public class ExceptionMsgFormatFactory {
	
	public static final String STACK_TRACE = "StackTraceHandler";

	private ExceptionMsgFormatFactory() {
	}
	
	private static class SingletonHolder{
		private static final ExceptionMsgFormatFactory instance = new ExceptionMsgFormatFactory();
	}
	
	public static ExceptionMsgFormatFactory getInstance(){
		return SingletonHolder.instance;
	}
	
	public ExceptionMessageFormat getFormatter(String formatterName){
		switch (formatterName) {
		case STACK_TRACE:
			return StackTraceMsgHandler.getInstance();
			
		default:
			break;
		}
		return DefaultExceptionMsgHandler.getInstance();
	}
}
