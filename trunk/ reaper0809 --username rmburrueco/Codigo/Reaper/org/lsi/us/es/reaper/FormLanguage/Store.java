package org.lsi.us.es.reaper.FormLanguage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.lsi.us.es.reaper.Core.Configurations;
import org.lsi.us.es.reaper.Core.IFormFiller;
import org.lsi.us.es.reaper.Core.ReapingProcess;
import org.lsi.us.es.reaper.Core.Exceptions.ReapingProccessException;
import org.lsi.us.es.reaper.QueryLanguage.EventEnumeration;
import org.lsi.us.es.reaper.QueryLanguage.Query;
import org.lsi.us.es.reaper.QueryLanguage.ScriptVariable;

public class Store implements Action 
{
	public boolean apply(IFormFiller formFiller,Query q) throws ReapingProccessException
	{
	    try 
	    {
			q.launcEvent(EventEnumeration.actionBegin);
			
	    	File directory=new File(Configurations.OutputDirectory);
	    	if(!directory.exists())
	    		directory.mkdir();
	    	
	        PrintWriter fw =
	            new PrintWriter(new FileOutputStream(Configurations.OutputDirectory + ReapingProcess.getAttemptId() +".html"));
	        fw.write(formFiller.getCurrentHtmlContent());
	        fw.close();
	        q.launcEvent(EventEnumeration.actionFinished);
	    } 
	    catch (FileNotFoundException ex) 
	    {
	    	throw new ReapingProccessException(ex);
        }
	    return true;
	}

}
