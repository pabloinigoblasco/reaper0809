/* 
 * Authors:  
 * 	Pablo Iñigo Blasco
 * 	Rosa María Burrueco Jiménez
 *  
 * Advisors:
 *  	Rafael Corchuelo Gil
 *  	Inmaculada Hernández Salmerón
 *  
 * Universidad de Sevilla 2009
 *  
 * */

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
