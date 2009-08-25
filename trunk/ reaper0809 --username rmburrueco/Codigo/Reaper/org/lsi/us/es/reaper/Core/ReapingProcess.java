package org.lsi.us.es.reaper.Core;

import java.io.FileReader;
import java.io.IOException;

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
import org.lsi.us.es.reaper.QueryLanguage.*;
import org.xml.sax.InputSource;

public class ReapingProcess {
	private Object loadModel(String modelFilepath,
			String modelMappinglFilePath, Class c) throws LoadingModelException {
		// Load Mapping
		Mapping mapping = new Mapping();
		try {
			InputSource is = new InputSource(new FileReader(
					modelMappinglFilePath));
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

	public void start(String formModelFilepath, String queryModelFilepath)
			throws LoadingModelException, JavaScriptException,
			ReapingProccessException {
		IFormFiller filler = null;

		try {
			Query query = loadQueryModel(queryModelFilepath);
			Form form = loadFormModel(formModelFilepath);
			filler = getFormFiller();
			form.getReachFormMethod().navigateToForm();
			boolean errors = form.checkFields();
			filler.importScripts(query.getJavaScriptImports());
			initScriptVariables(filler);
			linkData(form, query);
			query.executeQuery(form, filler);

		} catch (LoadingModelException e) {
			LogSystem.notifyError(e);
			throw e;
		} catch (JavaScriptException e) {
			LogSystem.notifyError(e);
			throw e;
		} finally {
			if (filler != null)
				filler.releaseResources();
			singletonInstance = null;
		}
	}

	private void initScriptVariables(IFormFiller f) 
	{
		for(ScriptVariable  v:ScriptVariable.values())
			f.registerVariable(v,null);
		
	}

	private void linkData(Form f, Query q) throws LoadingModelException {
		// TODO:crear marca de orden en todos los simpleAssignments

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

	private boolean checkFormModelCoherence(Form f, Query q) {
		// TODO:comprobar que al menos hay un result

		// TODO:comprobar que al menos de cada resultado
		// está relleno o el regExp o el urlregexp

		// TODO:Comprobar que todos los campos existen??
		// y lanzar warnings

		// TODO:si existe una iterate hub action comprobar
		// que existe la cadena ::child una y solo una vez product

		// TODO:Comprobar que todos los campos del lenguaje de queries
		// existen en el lenguaje de forms.

		// TODO:Comprobar que en el lenguaje de queries no hay ningún
		// Dependency assignment sin ningún group

		// TODO:Comprobar que en el lenguaje de queries no hay ningún
		// group sin ningún simple

		// TODO:Comprobar si dos valores de un mismo simple assignment están
		// repetidos
		filler.registerVariable(ScriptVariable.currentEvent,
				null);
		// TODO: Comrpobar si hay alguna aplicación de reslutado y que si hay
		// una no sea solo discard

		// TODO: Comprobar si las expresiones regulares están correctamente
		// escritas

		// TODO: Comprobar en los warnings a ver si se ha ignorado algo en el
		// xml

		return false;
	}

	static int attemptId;

	public static int getAttemptId() {
		return attemptId;
	}

	public static int nextAttempt() {
		return ++attemptId;
	}
}
