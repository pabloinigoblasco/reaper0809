

function waitLoadingUlisesResults()
{
	/*while(selenium.isElementPresent('id=porcentaje'))
	{

	}*/
	
	var divOculto=selenium.browserbot.findElement('id=loading');
	var wait= (divOculto.style.visibility!="hidden");
	return wait;
}

function removeTargetBlank()
{
	var anchor=selenium.browserbot.findElement(currentProductLocator);
	anchor.target=null;
}
