function getArrivalMonthLabel(val)
{
	locator=currentFieldLocator;
	var select=selenium.browserbot.findElement(locator);
	var currentDate=new Date();
	val=(val-currentDate.getMonth())%12;
	var text=select.options[val].text;
	return text;
}

function avoidSpanishLanguage()
{
	if(selenium.isElementPresent("link=English"))
	{
		selenium.doClick("link=English");
		selenium.doWaitForPageToLoad("30000");
	/*	selenium.doClick("//a[@id='change']/span");
		selenium.doWaitForPageToLoad("30000");
		selenium.doSelect("country", "label=Reino Unido");
		selenium.doWaitForPageToLoad("30000");
		selenium.doClick("//input[@value='Cambiar...']");
		selenium.doWaitForPageToLoad("30000");*/
	}
}