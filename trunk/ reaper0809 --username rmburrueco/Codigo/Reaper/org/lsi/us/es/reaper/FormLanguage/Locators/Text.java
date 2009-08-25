package org.lsi.us.es.reaper.FormLanguage.Locators;

import org.lsi.us.es.reaper.FormLanguage.Locator;

public class Text extends Locator{

	@Override
	public String getExpression() 
	{
		return toXpathLocator().getExpression();
	}
	
	@Override
	public Locator toXpathLocator() {
		Xpath xpathLocator=new Xpath();
		xpathLocator.setValue("//*[text()=\""+this.getValue()+"\"]");
		return xpathLocator;
	}

}
