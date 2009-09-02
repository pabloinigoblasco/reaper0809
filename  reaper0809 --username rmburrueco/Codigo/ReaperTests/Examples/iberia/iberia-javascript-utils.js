var auxiliarDepartureDate=new Date();
var auxiliarReturnDate=new Date();
var lastCountry="";

function pushDepartureDate(date)
{
	auxiliarDepartureDate.setDate(date.getDate());
	return date;
}
function getDepartureDay()
{
	return auxiliarDepartureDate.getDate();
}

function pushReturnDate(date)
{
	auxiliarReturnDate.setDate(date.getDate());
	return date;
}
function getReturnDay()
{
	return auxiliarReturnDate.getDate();
}

function getMonthLabel(val)
{
	locator=currentFieldLocator;
	var select=selenium.browserbot.findElement(locator);
	var currentDate=new Date();
	val=(val-currentDate.getMonth())%12;
	var text=select.options[val].text;
	return text;
}

function waitOnCountryChanged(field,val)
{
	if(field=="departureCountry")
	{
		if(val!=lastCountry)
		{
			selenium.doWaitForPageToLoad("30000");
			waitMillisecs(3000);
		}
	
		lastCountry=val;
	}

}

function avoidSpanishLanguage()
{
	if(selenium.isElementPresent("link=English"))
	{
		selenium.doClick("link=English");
	}
}
