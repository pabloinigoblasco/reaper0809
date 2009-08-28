function checkBlockingLinkNavigator()
{
	try
	{
		if(selenium.isElementPresent("link=Show all *"))
			selenium.doClick("link=Show all *");
	}
	catch(e)
		alert(e);
}
