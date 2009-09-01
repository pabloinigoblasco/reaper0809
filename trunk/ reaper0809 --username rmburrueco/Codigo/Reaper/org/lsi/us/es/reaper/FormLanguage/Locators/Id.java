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

package org.lsi.us.es.reaper.FormLanguage.Locators;

import org.lsi.us.es.reaper.FormLanguage.Locator;

public class Id extends Locator{

	@Override
	public String getExpression() {
		return "id="+this.getValue();
		
	}

	@Override
	public Locator toXpathLocator() {
		Xpath xpathLocator=new Xpath();
		xpathLocator.setValue("//*[@id=\""+this.getValue()+"\"]");
		return xpathLocator;
	}

}