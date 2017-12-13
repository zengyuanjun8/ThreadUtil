package com.zyj.exception;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.zyj.exception.util.ExceptionMessageFormat;
import com.zyj.exception.util.factory.ExceptionMsgFormatFactory;

public class ChildThreadException extends Exception {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5682825039992529875L;

	private List<ChildThreadException> exceptionList;
	private ExceptionMessageFormat formatter;
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
		exceptionList = new ArrayList<ChildThreadException>();
		lock = new ReentrantLock();
		formatter = ExceptionMsgFormatFactory.getInstance().getFormatter(ExceptionMsgFormatFactory.STACK_TRACE);
	}

	public boolean hasException() {
		return exceptionList.size() > 0;
	}

	public void addException(Exception e) {
		try {
			lock.lock();
			ChildThreadException childException = new ChildThreadException(e.getMessage(), e.getStackTrace());
			exceptionList.add(childException);
		} finally {
			lock.unlock();
		}
	}

	public List<ChildThreadException> getExceptionList() {
		return exceptionList;
	}

	public void clearExceptionList() {
		exceptionList.clear();
	}

	public String getAllMessage() {
		StringBuffer sb = new StringBuffer();
		for (ChildThreadException e : exceptionList) {
			sb.append(e.getMessage());
			sb.append("\n");
			sb.append(formatter.formate(e));
		}
		return sb.toString();
	}
	
	public void printAllStackTrace() {
		printAllStackTrace(System.err);
	}
	
	public void printAllStackTrace(PrintStream s) {
		for (ChildThreadException e : exceptionList) {
			e.printStackTrace(s);
		}
    }

}
