package com.taobao.gulu.tools;

/**
 * 代表远程执行命令过程中的异常
 * 
 *
 */
public class OperationException extends Exception
{
	private static final long serialVersionUID = 1123415143L;
	
	private Exception innerException;

	/**
	 * 默认构造函数。
	 */
	public OperationException()
	{
		super();
	}
	
	/**
	 * 构造函数。
	 * @param msg - 异常的描述信息
	 */
	public OperationException(String msg)
	{
		super(msg);
	}
	
	/**
	 * 构造函数。
	 * @param e - 表示异常的内部异常
	 */
	public OperationException(Exception e)
	{
		super();
		this.innerException = e;
	}
	
	public OperationException(Exception e, String message)
	{
		super(message);
		this.innerException = e;
	}

	/**
	 * 获取内部异常。
	 * @return
	 */
	public Exception getInnerException()
	{
		return innerException;
	}

	/**
	 * 设置内部异常。
	 * @param innerException
	 */
	public void setInnerException(Exception innerException)
	{
		this.innerException = innerException;
	}
}
