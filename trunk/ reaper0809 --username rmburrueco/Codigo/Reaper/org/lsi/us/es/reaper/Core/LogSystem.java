/* 
 * Authors:  
 * 	Pablo Iñigo Blasco
 * 	Rosa María Burrueco
 *  
 * Directed by:
 *  	Rafael Corchuelo Gil
 *  	Inmaculada Hernández Salmerón
 *  
 * Universidad de Sevilla 2009
 *  
 * */

package org.lsi.us.es.reaper.Core;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lsi.us.es.reaper.Core.Exceptions.JavaScriptException;
import org.lsi.us.es.reaper.Core.Exceptions.LoadingModelException;
import org.lsi.us.es.reaper.Core.Exceptions.ReapingProccessException;
import org.lsi.us.es.reaper.QueryLanguage.EventEnumeration;
import org.lsi.us.es.reaper.QueryLanguage.Query;
import org.lsi.us.es.reaper.QueryLanguage.ScriptVariable;

public class LogSystem {
	static protected Logger log;
	static {
		log = Logger.getLogger(ReapingProcess.class.getName());
	}

	public static void notifyError(String error) {
		log.log(Level.SEVERE, error);
	}

	public static void notifyError(Exception ex) {
		log.log(Level.SEVERE, ex.getMessage());
	}

	public static void warn(String warning) {
		log.log(Level.WARNING, warning);
	}

	public static void Log(String text) {
		log.log(Level.INFO, text);
	}

	public static void submitFailed(ReapingProccessException ex) {
		log.log(Level.SEVERE, ex.getMessage());

	}

	public static void settingValueFail(LoadingModelException e) {
		// TODO Auto-generated method stub
		log.log(Level.SEVERE, e.getMessage());
	}

	public static void settingValueFail(JavaScriptException e) {
		// TODO Auto-generated method stub
		log.log(Level.SEVERE, e.getMessage());
	}

	public static void applingResultFail(ReapingProccessException e) {
		// TODO Auto-generated method stub
		log.log(Level.SEVERE, e.getMessage());
	}

	public static void settingValueFail(ReapingProccessException e) {
		// TODO Auto-generated method stub
		log.log(Level.SEVERE, e.getMessage());
	}

	public static void scriptEventFailed(Exception ex,Query q) 
	{
		IFormFiller formFiller=ReapingProcess.getFormFiller();
		formFiller.registerVariable(ScriptVariable.scriptExceptionMessage,ex.getMessage());
		q.launcEvent(EventEnumeration.scriptException);
		// TODO Auto-generated method stublogXmlCoherenceErrors
		log.warning(ex.getMessage());
		formFiller.registerVariable(ScriptVariable.scriptExceptionMessage,null);
	}

	public static void logXmlCoherenceErrors(List<String> errors) 
	{
		// TODO Auto-generated method stub
		for(String err:errors)
			log.severe(err);
		
		log.severe("Reaping process interrupted due xml-coherence errors");
	}
}