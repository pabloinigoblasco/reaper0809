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

public class Name extends Locator{

	@Override
	public String getExpression() 
	{
		return "name="+getValue();
	}
	
	@Override
	public Locator toXpathLocator() 
	{
		Xpath xpathLocator=new Xpath();
		xpathLocator.setValue("//*[@name=\""+this.getValue()+"\"]");
		return xpathLocator;
	}

}
