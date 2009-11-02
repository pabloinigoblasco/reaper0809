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

package org.lsi.us.es.reaper.FormLanguage.Locators;

import org.lsi.us.es.reaper.FormLanguage.Locator;

public class Xpath extends Locator{


	@Override
	public String getExpression() {
		return "xpath="+this.getValue();
	}

	@Override
	public Locator toXpathLocator() {
		return this;
	}

}
