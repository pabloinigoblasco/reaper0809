
function showAlertWithAllScriptVariables()
{
	var str="Reaper Variables:\n\n";
	if(currentEvent!=null)
		str="currentEvent: "+currentEvent.toString()+"\n";
	if(currentField!=null)
		str+="currentField: "+currentField.toString()+"\n";
	if(currentFieldLocator!=null)
		str+="currentFieldLocator: "+currentFieldLocator.toString()+"\n";
	if(currentValue!=null)
		str+="currentValue: "+currentValue.toString()+"\n";
	if(currentAction!=null)
	str+="currentAction: "+currentAction.toString()+"\n";
	
	alert(str);
}

function showScriptExceptionMessage()
{
	alert(scriptExceptionMessage);
}

function getToday()
{
	return new Date();
}

function waitMillisecs(millis)
{
	var date = new Date();
	var curDate = null;

	do { curDate = new Date(); }
	while(curDate-date < millis);
} 
