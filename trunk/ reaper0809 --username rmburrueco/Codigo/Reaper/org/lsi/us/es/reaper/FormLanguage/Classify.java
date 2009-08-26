/* Authors:
 *  
 * Pablo Iñigo Blasco
 * Rosa María Burrueco
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


public class Classify implements Action{

	String tag;
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public boolean apply(IFormFiller formFiller,Query q) throws ReapingProccessException {
	    try 
	    {
			q.launcEvent(EventEnumeration.actionBegin);
			
			
	    	File directory=new File(Configurations.OutputDirectory+tag+"/");
	    	if(!directory.exists())
	    		directory.mkdir();
	    	
	        PrintWriter fw =
	            new PrintWriter(new FileOutputStream(Configurations.OutputDirectory+tag+"/" + ReapingProcess.getAttemptId() +".html"));
	        fw.write(formFiller.getCurrentHtmlContent());
	        fw.close();
	        
	        q.launcEvent(EventEnumeration.actionFinished);
	    } 
	    catch (FileNotFoundException ex) {
	    	throw new ReapingProccessException(ex);
        }
	    return true;
	}
	public boolean validate(List<String> errorDescriptions) 
	{
		return false;
	}

}
