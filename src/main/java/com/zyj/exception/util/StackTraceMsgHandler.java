package com.zyj.exception.util;

/**
 * 堆栈跟踪信息格式化工具
 * @author zengyuanjun
 *
 */
public class StackTraceMsgHandler implements ExceptionMessageFormat {

	private StackTraceMsgHandler() {
	}

	private static class SingletonHolder {
		private static final StackTraceMsgHandler instance = new StackTraceMsgHandler();
	}

	public static StackTraceMsgHandler getInstance() {
		return SingletonHolder.instance;
	}

	/**
	 * 格式化堆栈跟踪信息
	 */
	@Override
	public String formate(Exception e) {
		StackTraceElement[] stackTrace = e.getStackTrace();
		StringBuffer sb = new StringBuffer();
		for (StackTraceElement stackTraceElement : stackTrace) {
			sb.append("\tat " + stackTraceElement + "\n");
		}
		return sb.toString();
	}

}
