/* 
 * Authors:  
 * 	Pablo Iñigo Blasco
 * 	Rosa María Burrueco
 *  
 * Advisors:
 *  	Rafael Corchuelo Gil
 *  	Inmaculada Hernández Salmerón
 *  
 * Universidad de Sevilla 2009
 *  
 * */

package org.lsi.us.es.reaper.FormLanguage;

import java.util.List;

import org.lsi.us.es.reaper.Core.IFormFiller;
import org.lsi.us.es.reaper.QueryLanguage.Query;

public class Discard implements Action{

	public boolean apply(IFormFiller formFiller,Query q)  {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean validate(List<String> errorDescriptions) {
		// TODO Auto-generated method stub
		return true;
	}

}
