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

package org.lsi.us.es.reaper.FormLanguage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.List;
import org.lsi.us.es.reaper.Core.Configurations;
import org.lsi.us.es.reaper.Core.IFormFiller;
import org.lsi.us.es.reaper.Core.ReapingProcess;
import org.lsi.us.es.reaper.Core.Exceptions.ReapingProccessException;
import org.lsi.us.es.reaper.QueryLanguage.EventEnumeration;
import org.lsi.us.es.reaper.QueryLanguage.Query;


public class Store implements Action 
{
	public boolean apply(IFormFiller formFiller,Query q) throws ReapingProccessException
	{
	    try 
	    {
			q.launchEvent(EventEnumeration.actionBegin);
			
	        PrintWriter fw =
	            new PrintWriter(new FileOutputStream(ReapingProcess.getCurrentDirectoryName()+"/"+ ReapingProcess.getAttemptId() +".html"));
	        fw.write(formFiller.getCurrentHtmlContent());
	        fw.close();
	        q.launchEvent(EventEnumeration.actionFinished);
	    } 
	    catch (FileNotFoundException ex) 
	    {
	    	throw new ReapingProccessException(ex);
        }
	    return true;
	}

	public boolean validate(List<String> errorDescriptions) {
		return false;
	}

}
