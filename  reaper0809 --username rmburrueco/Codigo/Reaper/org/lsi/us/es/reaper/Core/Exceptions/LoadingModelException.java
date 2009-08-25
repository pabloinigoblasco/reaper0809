package org.lsi.us.es.reaper.Core.Exceptions;



public class LoadingModelException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3522263843148257345L;
	public LoadingModelException(Exception ex)
	{
		super(ex);
	}
	public LoadingModelException(String msg)
	{
		super(msg);
	}
	
	
}
