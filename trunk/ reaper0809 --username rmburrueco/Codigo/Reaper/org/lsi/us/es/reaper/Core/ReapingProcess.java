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

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;
import org.exolab.castor.xml.XMLContext;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.Unmarshaller;
import org.lsi.us.es.reaper.Core.Exceptions.JavaScriptException;
import org.lsi.us.es.reaper.Core.Exceptions.LoadingModelException;
import org.lsi.us.es.reaper.Core.Exceptions.ReapingProccessException;
import org.lsi.us.es.reaper.FormLanguage.*;
import java.util.List;
import org.lsi.us.es.reaper.QueryLanguage.*;
import org.xml.sax.InputSource;

public class ReapingProcess {

	public boolean start(String formModelFilepath, String queryModelFilepath)
			throws LoadingModelException, JavaScriptException,
			ReapingProccessException {
		IFormFiller filler = null;

		try 
		{
			
			LogSystem.Log("Loading query model...");
			Query query = loadQueryModel(queryModelFilepath);
			LogSystem.Log("Loading form model...");
			Form form = loadFormModel(formModelFilepath);
			LogSystem.Log("setting up form filling system...");
			filler = getFormFiller();
			form.getReachFormMethod().navigateToForm();
			List<String> errors = new ArrayList<String>();
			LogSystem.Log("generating output directory...");
			generateCurrentDirectoryName(form);
			LogSystem.Log("validating form model...");
			boolean formValidationErrors = form.validate(errors);
			LogSystem.Log("form model [OK]");
			LogSystem.Log("validating query model...");
			LogSystem.Log("query model [OK]");
			boolean queryValidationErrors = query.validate(errors, form);
			if (!formValidationErrors && !queryValidationErrors) {
				LogSystem.Log("Initializating system...");
				initScriptVariables(filler,form);
				linkData(form, query);
				LogSystem.Log("Reaping process started.");
				query.executeQuery(form, filler);
				return false;
			} else {
				LogSystem.logXmlCoherenceErrors(errors);
				return true;
			}

		} catch (LoadingModelException e) {
			LogSystem.notifyError(e);
			throw e;
		} catch (JavaScriptException e) {
			LogSystem.notifyError(e);
			throw e;
		} 
		// al terminar, haya error o no, liberar recursos.
		finally 
		{
			if (filler != null)
			{
				filler.releaseResources();
				LogSystem.releaseResources();
			}
			singletonInstance = null;
		}
	}

	static String currentDirectoryName;
	private static void generateCurrentDirectoryName(Form f)
	{
		
		DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy_HHmm");
	    Date date = new Date();
	    String d= dateFormat.format(date);
		    	
		currentDirectoryName=Configurations.OutputDirectory;
	    File directory=new File(currentDirectoryName);
    	if(!directory.exists())
    	{
    		LogSystem.Log("creating directory:" + directory.getPath());
    		directory.mkdir();
    	}
    	
    	currentDirectoryName+="/"+f.getIdentificationUrl();
    	directory=new File(currentDirectoryName);
    	if(!directory.exists())
    	{
    		LogSystem.Log("creating directory:" + directory.getPath());
    		directory.mkdir();
    	}
    	
    	currentDirectoryName+="/"+d;
    	directory=new File(currentDirectoryName);
    	if(!directory.exists())
    	{
    		LogSystem.Log("creating directory:" + directory.getPath());
    		directory.mkdir();
    	}
    	LogSystem.Log("working directory:" + directory.getPath());
    	
	}
	
	public static String getCurrentDirectoryName()
	{
		return currentDirectoryName;
	}
	
	private void initScriptVariables(IFormFiller f,Form form) {
		for (ScriptVariable v : ScriptVariable.values())
			f.registerVariable(v, null);
		f.registerFormLocators(form);

	}

	private void linkData(Form f, Query q) throws LoadingModelException {
		// crear marca de orden en todos los simpleAssignments
		int cont = 0;
		for (Assignment a : q.getAssignments()) {
			if (a instanceof Simple) {
				Simple s = (Simple) a;
				s.setPositionOrder(cont++);
				for (Value v : s.getValues()) {
					v.setParentAssignment(s);
				}
				s.setField(f.getFieldByFieldId(s.getFieldId()));
			} else {
				for (Group g : ((Dependant) a).getGroups())
					for (Simple s : g.getSimpleAssigments()) {
						s.setPositionOrder(cont++);
						for (Value v : s.getValues())
							v.setParentAssignment(s);
						s.setField(f.getFieldByFieldId(s.getFieldId()));
					}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private Object loadModel(String modelFilepath,
			String modelMappinglFilePath, Class c) throws LoadingModelException {

		// Load Mapping
		Mapping mapping = new Mapping();
		try {
			
			File f=new File(modelMappinglFilePath);
			InputSource is;
			if(!f.exists())
			{
				InputStream ins=getClass().getClassLoader().getResource(modelMappinglFilePath).openStream();
				if(ins==null || ins.available()==0)
					LogSystem.Log("mapping file not found:"+modelMappinglFilePath);
				is= new InputSource(ins);
			}
			else
				is= new InputSource(new FileReader(f));
			LogSystem.Log("loading model-mapping file: "+modelMappinglFilePath);
			
			mapping.loadMapping(is);

			XMLContext context = new XMLContext();
			context.addMapping(mapping);

			// Create a Reader to the file to unmarshal from
			FileReader reader = new FileReader(modelFilepath);

			// Create a new Unmarshaller
			Unmarshaller unmarshaller = context.createUnmarshaller();
			unmarshaller.setIgnoreExtraElements(false);
			unmarshaller.setIgnoreExtraAttributes(false);
			unmarshaller.setClass(c);

			// Unmarshal the person object
			return unmarshaller.unmarshal(reader);

		} catch (IOException e) {
			throw new LoadingModelException(e);
		} catch (MappingException e) {
			throw new LoadingModelException(e);
		} catch (MarshalException e) {
			throw new LoadingModelException(e);
		} catch (ValidationException e) {
			throw new LoadingModelException(e);
		}
	}

	private static IFormFiller singletonInstance;
	static {
		singletonInstance = null;
	}

	public synchronized static IFormFiller getFormFiller() {
		try {
			if (singletonInstance == null)
				singletonInstance = (IFormFiller) Class.forName(
						Configurations.FormFillerStrategy).newInstance();
			return singletonInstance;

		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	public Form loadFormModel(String modelFilepath)
			throws LoadingModelException {
		return (Form) loadModel(modelFilepath,
				Configurations.FormModelMappingFile, Form.class);
	}

	public Query loadQueryModel(String modelFilepath)
			throws LoadingModelException {
		return (Query) loadModel(modelFilepath,
				Configurations.QueryModelMappingFile, Query.class);
	}

	static int attemptId;

	public static int getAttemptId() {
		return attemptId;
	}

	public static int nextAttempt() {
		return ++attemptId;
	}
}
