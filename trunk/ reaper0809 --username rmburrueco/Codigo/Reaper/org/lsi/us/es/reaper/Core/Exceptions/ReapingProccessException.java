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

public class ReapingProccessException extends Exception 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6777546487782145737L;

	public ReapingProccessException(Exception ex)
	{
		super(ex);
	}
}
