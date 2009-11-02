/* 
 * Authors:  
 * 	Pablo Iñigo Blasco
 * 	Rosa María Burrueco Jiménez
 *  
 * Advisors:
 *  	Rafael Corchuelo Gil
 *  	Inmaculada Hernández Salmerón
 *  
 * Universidad de Sevilla 2009
 *  
 * */

package org.lsi.us.es.reaper.QueryLanguage;

public enum EventEnumeration {
	scriptException,
	reapingProcessBegin,
	reapingProcessFinished,
	
	queryBegin,
	queryFinished,
	
	fieldAssignmentBegin,
	fieldAssignmentFinished,
	
	submitBegin,
	submitFinished,
	
	actionBegin,
	actionFinished,
	
	//específicos de iterateHub
	iterateHubPageFinished,
	iterateHubPageBegin,
	iterateHubProductDetailBegin,
	iterateHubProductDetailFinished
}
