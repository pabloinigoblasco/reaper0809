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

package org.lsi.us.es.reaper.Core;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lsi.us.es.reaper.Core.Exceptions.ReapingProccessException;
import org.lsi.us.es.reaper.FormLanguage.Field;
import org.lsi.us.es.reaper.QueryLanguage.EventEnumeration;
import org.lsi.us.es.reaper.QueryLanguage.Query;
import org.lsi.us.es.reaper.QueryLanguage.ScriptVariable;
import org.lsi.us.es.reaper.QueryLanguage.Simple;
import org.lsi.us.es.reaper.QueryLanguage.Value;

/*Esta clase recive eventos de todo lo que va ocurriendo, podría implementarse otra estrategia 
 * que en lugar de escribirlo en el log, por ejemplo escribiera estadísticas o los resultados
 * en una base de datos*/
public class LogSystem 
{
	static protected Logger log;
	static protected PrintWriter fwx;
	
	static protected PrintWriter getFwx()
	{
		if(fwx==null)
		{
			try {
				fwx =new PrintWriter(new FileOutputStream(ReapingProcess.getCurrentDirectoryName()+"/"+ "ResultLog.txt"));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return fwx;
	}
	static 
	{
		log = Logger.getLogger(ReapingProcess.class.getName());
		
	}

	public static void releaseResources()
	{
		if(getFwx()!=null)
			getFwx().close();
	}
	public static void notifyError(String error) {
		log.log(Level.SEVERE, error);
	}

	public static void notifyError(Exception ex) {
		
		log.log(Level.SEVERE, ex.toString()+"\n"+ex.getLocalizedMessage());
	}

	public static void warn(String warning) {
		log.log(Level.WARNING, warning);
	}

	public static void logConsole(String text) {
		log.log(Level.INFO, text);

	}

	public static void submitFailed(ReapingProccessException ex) {
		log.log(Level.SEVERE, ex.getMessage());
		getFwx().println(ex.getMessage());

	}

	
	public static void settingDynamicFieldValueException(ReapingProccessException e, Field f, String calculatedValue, IFormFiller filler, Query q) {
		String msg=f.getFieldId()+"<-"+calculatedValue+"\n";
		msg+=e.getMessage();
		
		log.warning(msg);
		getFwx().println(msg);
		
		filler.setVariableValue(ScriptVariable.scriptExceptionMessage,msg);
		q.launchEvent(EventEnumeration.scriptException);

	}

	public static void applingResultFail(ReapingProccessException e) {
		// TODO Auto-generated method stub
		log.log(Level.SEVERE, e.getMessage());
		getFwx().println(e.getMessage());
	}

	
	public static void scriptEventFailed(Exception ex,Query q,EventEnumeration failedEvent) 
	{
		IFormFiller formFiller=ReapingProcess.getFormFiller();
		String msg="Javascript code failed at the reaping event: '"+failedEvent+"':"+ex.getMessage();
		
		formFiller.setVariableValue(ScriptVariable.scriptExceptionMessage,msg);
		q.launchEvent(EventEnumeration.scriptException);
		log.warning(msg);
		formFiller.setVariableValue(ScriptVariable.scriptExceptionMessage,null);
	}

	public static void logXmlCoherenceErrors(List<String> errors) 
	{
		// TODO Auto-generated method stub
		getFwx().println("Initializing..");
		for(String err:errors)
		{
			log.severe(err);
			getFwx().println("err");
		}
		
		log.severe("Reaping process interrupted due xml-coherence errors");
		getFwx().println("Reaping process interrupted due xml-coherence errors");
	}

	public static void NotifyStartingSubmitActivity(
			Map<Simple, Value> assignmentsMap) 
	{
		getFwx().println("------------------------------------");
		getFwx().println("Query: "+ReapingProcess.getAttemptId());
		
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	    Date date = new Date();
	    String d= dateFormat.format(date);
	    
	    getFwx().println("Time: "+d+"\n");
	    getFwx().println("Asignments:");
		for(Entry<Simple,Value> entry:assignmentsMap.entrySet())
		{
			String assignmentLog="\t"+entry.getKey().getFieldId()+" : ";
			if(entry.getValue().isJavascriptExpression())
				assignmentLog+="${"+entry.getValue().getVal()+"}";
			else 
				assignmentLog+=entry.getValue().getVal();
			getFwx().println(assignmentLog);
		}
		getFwx().println();
		getFwx().flush();
	}
	
	public static void notifyAssignmentEvaluated(String expression, String calculatedValue,Field f) 
	{
		getFwx().println("[dynamic assignment] "+f.getFieldId() +" <= "+calculatedValue);
	}
	
	public static void LogInResults(String msg)
	{
		getFwx().println(msg);
		getFwx().flush();
	}	
}
