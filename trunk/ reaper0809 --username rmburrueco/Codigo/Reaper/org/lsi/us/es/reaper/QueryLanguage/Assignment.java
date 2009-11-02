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

package org.lsi.us.es.reaper.QueryLanguage;

import java.util.List;

import org.lsi.us.es.reaper.FormLanguage.Form;

public abstract class Assignment {

	public abstract boolean validate(List<String> errors,Form f) ;

}
