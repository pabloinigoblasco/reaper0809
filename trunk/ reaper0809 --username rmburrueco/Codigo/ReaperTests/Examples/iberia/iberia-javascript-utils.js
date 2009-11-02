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
	val=Math.abs(val-currentDate.getMonth())%12;
	var text=select.options[val].text;
	return text;
}


function waitOnCountryChanged(field,val)
{
	if(field=="departureCountry")
	{
		var wait = selenium.isElementPresent("TB_overlay");
		return wait;
	}
}

function avoidSpanishLanguage()
{
	if(selenium.isElementPresent("link=English"))
	{
		selenium.doClick("link=English");
	}

	//ejemplo de eventos como punto de sincronización por condición
	return !selenium.isElementPresent("link=Fligths");

	//ejemplo con otro lenguaje
	//	if(selenium.isElementPresent("link=Español"))
	//	{
	//		selenium.doClick("link=Español");
	//	}
	//	return !selenium.isElementPresent("link=Vuelos");
}
