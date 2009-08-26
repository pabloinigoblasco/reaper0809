/* Authors:
 *  
 * Pablo Iñigo Blasco
 * Rosa María Burrueco
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
