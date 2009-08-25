package org.lsi.us.es.reaper.QueryLanguage;

public enum EventEnumeration {
	scriptException,
	reapingProcessBegin,
	reapingProcessFinished,
	
	fieldAssignmentsSetBegin,
	fieldAssignmentsSetEnd,
	
	fieldAssignmentBegin,
	fieldAssignmentEnd,
	
	submitStart,
	submitEnd,
	
	actionBegin,
	actionFinished,
	
	//específicos de iterateHub
	iterateHubPageFinished,
	iterateHubNavigateInBegin,
	iterateHubNavigateInFinished,
}
