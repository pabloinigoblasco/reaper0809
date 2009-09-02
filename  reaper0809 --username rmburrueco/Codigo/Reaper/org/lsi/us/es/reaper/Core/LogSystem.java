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

import org.lsi.us.es.reaper.Core.Exceptions.JavaScriptException;
import org.lsi.us.es.reaper.Core.Exceptions.LoadingModelException;
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
	static protected PrintWriter fw;
	
	static 
	{
		log = Logger.getLogger(ReapingProcess.class.getName());
		try {
			fw =new PrintWriter(new FileOutputStream(ReapingProcess.getCurrentDirectoryName()+"/"+ "ResultLog.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void releaseResources()
	{
		if(fw!=null)
			fw.close();
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
		fw.println(ex.getMessage());

	}

	
	public static void settingValueFail(ReapingProccessException e, Field f,
			String calculatedValue) {
		String msg=f.getFieldId()+"<-"+calculatedValue+"\n";
		msg+=e.getMessage();
		
		log.warning(msg);
		fw.println(msg);

	}
	public static void settingValueFail(JavaScriptException e, Field f, String calculatedValue) {
		String msg=f.getFieldId()+"<-"+calculatedValue+"\n";
		msg+=e.getMessage();
		
		log.warning(msg);
		fw.println(msg);
	}

	public static void applingResultFail(ReapingProccessException e) {
		// TODO Auto-generated method stub
		log.log(Level.SEVERE, e.getMessage());
		fw.println(e.getMessage());
	}

	
	public static void scriptEventFailed(Exception ex,Query q) 
	{
		IFormFiller formFiller=ReapingProcess.getFormFiller();
		formFiller.setVariableValue(ScriptVariable.scriptExceptionMessage,ex.getMessage());
		q.launchEvent(EventEnumeration.scriptException);
		// TODO Auto-generated method stublogXmlCoherenceErrors
		log.warning(ex.getMessage());
		formFiller.setVariableValue(ScriptVariable.scriptExceptionMessage,null);
	}

	public static void logXmlCoherenceErrors(List<String> errors) 
	{
		// TODO Auto-generated method stub
		fw.println("Initializing..");
		for(String err:errors)
		{
			log.severe(err);
			fw.println("err");
		}
		
		log.severe("Reaping process interrupted due xml-coherence errors");
		fw.println("Reaping process interrupted due xml-coherence errors");
	}

	public static void NotifyStartingSubmitActivity(
			Map<Simple, Value> assignmentsMap) 
	{
		fw.println("------------------------------------");
		fw.println("Query: "+ReapingProcess.getAttemptId());
		
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	    Date date = new Date();
	    String d= dateFormat.format(date);
	    
	    fw.println("Time: "+d+"\n");
	    fw.println("Asignments:");
		for(Entry<Simple,Value> entry:assignmentsMap.entrySet())
		{
			String assignmentLog="\t"+entry.getKey().getFieldId()+" : ";
			if(entry.getValue().isJavascriptExpression())
				assignmentLog+="${"+entry.getValue().getVal()+"}";
			else 
				assignmentLog+=entry.getValue().getVal();
			fw.println(assignmentLog);
		}
		fw.println();
		fw.flush();
	}
	public static void notifyAssignmentEvaluated(String expression, String calculatedValue,Field f) 
	{
		fw.println("[dynamic assignment] "+f.getFieldId() +" <= "+calculatedValue);
	}
	
}
