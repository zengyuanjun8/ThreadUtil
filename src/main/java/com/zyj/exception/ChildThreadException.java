package com.zyj.exception;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.zyj.exception.util.ExceptionMessageFormat;
import com.zyj.exception.util.factory.ExceptionMsgFormatFactory;

/**
 * 子线程异常，当子线程出现异常时抛出
 * @author zengyuanjun
 */
public class ChildThreadException extends Exception {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5682825039992529875L;
	/**
	 * 子线程异常列表
	 */
	private List<Exception> exceptionList;
	/**
	 * 异常信息格式化工具
	 */
	private ExceptionMessageFormat formatter;
	/**
	 * 锁
	 */
	private Lock lock;

	public ChildThreadException() {
		super();
		initial();
	}

	public ChildThreadException(String message) {
		super(message);
		initial();
	}

	public ChildThreadException(String message, StackTraceElement[] stackTrace) {
		this(message);
		setStackTrace(stackTrace);
	}

	private void initial() {
		exceptionList = new ArrayList<Exception>();
		lock = new ReentrantLock();
		formatter = ExceptionMsgFormatFactory.getInstance().getFormatter(ExceptionMsgFormatFactory.STACK_TRACE);
	}

	/**
	 * 是否有异常
	 * @return
	 */
	public boolean hasException() {
		return exceptionList.size() > 0;
	}

	/**
	 * 添加异常到子线程异常列表
	 * @param e
	 */
	public void addException(Exception e) {
		try {
			lock.lock();
			e.setStackTrace(e.getStackTrace());
			exceptionList.add(e);
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 获取子线程异常列表
	 * @return
	 */
	public List<Exception> getExceptionList() {
		return exceptionList;
	}

	/**
	 * 清空子线程异常列表
	 */
	public void clearExceptionList() {
		exceptionList.clear();
	}

	/**
	 * 获取所有子线程异常列表中异常的堆栈跟踪信息
	 * @return
	 */
	public String getAllStackTraceMessage() {
		StringBuffer sb = new StringBuffer();
		for (Exception e : exceptionList) {
			sb.append(e.getClass().getName());
			sb.append(": ");
			sb.append(e.getMessage());
			sb.append("\n");
			sb.append(formatter.formate(e));
		}
		return sb.toString();
	}

	/**
	 * 打印所有子线程异常列表中异常的堆栈跟踪信息
	 */
	public void printAllStackTrace() {
		printAllStackTrace(System.err);
	}

	/**
	 * 打印所有子线程异常列表中异常的堆栈跟踪信息
	 * @param s
	 */
	public void printAllStackTrace(PrintStream s) {
		for (Exception e : exceptionList) {
			e.printStackTrace(s);
		}
	}

}
