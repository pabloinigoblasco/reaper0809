function checkBlockingLinkNavigator()
{
	if(selenium.isElementPresent("link=Show all *"))
		selenium.doClick("link=Show all *");
}
