function checkBlockingLinkNavigator()
{
	try
	{
		if(selenium.isElementPresent("link=Show all *"))
		{
			selenium.doClick("link=Show all *");
			selenium.doWaitForPageToLoad("30000");
		}
	}
	catch(e)
	{
		alert(e);
	}
}
