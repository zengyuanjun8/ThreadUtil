package com.zyj.exception;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.zyj.exception.util.factory.ExceptionMsgFormatFactory;

public class SubThreadException extends Exception{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2294774579324653886L;
	private List<SubThreadException> exceptionList;
	private StackTraceElement[] stackTrace = new StackTraceElement[0];
	private Lock lock;
	

	public SubThreadException() {
		super();
		initial();
	}

	public SubThreadException(String message) {
		super(message);
		initial();
	}
	
	private void initial() {
		exceptionList = new ArrayList<SubThreadException>();
		lock = new ReentrantLock();
	}

	@Override
	public StackTraceElement[] getStackTrace() {
		return stackTrace;
	}

	@Override
	public void printStackTrace() {
		// TODO Auto-generated method stub
		super.printStackTrace();
	}

	@Override
	public void printStackTrace(PrintStream s) {
		// TODO Auto-generated method stub
		super.printStackTrace(s);
	}

	@Override
	public void printStackTrace(PrintWriter s) {
		// TODO Auto-generated method stub
		super.printStackTrace(s);
	}

	public void addException(Exception e) {
		try {
			lock.lock();
			SubThreadException exception = new SubThreadException(e.getMessage());
			exception.setStackTrace(e.getStackTrace());
			exceptionList.add(exception);
		} finally {
			lock.unlock();
		}
	}
	
}
