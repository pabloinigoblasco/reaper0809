function removeTargetBlankOfProductAnchor()
{
	var anchor=selenium.browserbot.findElement(currentProductLocator);
	anchor.target=null;
	
	if(anchor.href.toString().match(".pdf"))
	{
		lastHref=anchor.href;
		anchor.href="java script:void(0);"
	}
}
