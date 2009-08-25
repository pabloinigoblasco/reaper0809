function getArrivalMonthLabel(val)
{
	locator=currentFieldLocator;
	var select=selenium.browserbot.findElement(locator);
	var currentDate=new Date();
	val=(val-currentDate.getMonth())%12;
	var text=select.options[val].text;
	return text;
}