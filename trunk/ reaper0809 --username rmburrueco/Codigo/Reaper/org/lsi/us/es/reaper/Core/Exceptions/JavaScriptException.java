/* 
 * Authors:  
 * 	Pablo Iñigo Blasco
 * 	Rosa María Burrueco
 *  
 * Directed by:
 *  	Rafael Corchuelo Gil
 *  	Inmaculada Hernández Salmerón
 *  
 * Universidad de Sevilla 2009
 *  
 * */


package org.lsi.us.es.reaper.Core.Exceptions;

public class JavaScriptException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8514039330252840850L;

	public JavaScriptException(Exception ex)
	{
		super(ex);
	}

}
