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

public class Group {
	List<Simple> simpleAssigments;

	public List<Simple> getSimpleAssigments() {
		return simpleAssigments;
	}

	public void setSimpleAssigments(List<Simple> simpleAssigments) {
		this.simpleAssigments = simpleAssigments;
	}

}