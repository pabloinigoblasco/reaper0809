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

package org.lsi.us.es.reaper.FormLanguage;

import java.util.List;

import org.lsi.us.es.reaper.Core.IFormFiller;
import org.lsi.us.es.reaper.Core.LogSystem;
import org.lsi.us.es.reaper.Core.ReapingProcess;

public class Form {
	String namespaceUrl;
	List<Result> results;
	Locator submit;
	List<Field> fields;
	ReachFormMethod reachFormMethod;

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public ReachFormMethod getReachFormMethod() {
		return reachFormMethod;
	}

	public void setReachFormMethod(ReachFormMethod reachFormMethod) {
		this.reachFormMethod = reachFormMethod;
	}

	public List<Result> getResults() {
		return results;
	}

	public void setResults(List<Result> results) {
		this.results = results;
	}

	public Locator getSubmit() {
		return submit;
	}

	public void setSubmit(Locator submit) {
		this.submit = submit;
	}

	public String getIdentificationUrl() {
		return namespaceUrl;
	}

	public void setIdentificationUrl(String namespaceUrl) {
		this.namespaceUrl = namespaceUrl;
	}

	public boolean validate(List<String> errorDescriptions) {
		IFormFiller filler = ReapingProcess.getFormFiller();

		boolean errors = false;
		// comprobar que al menos hay un result
		if (this.getResults() == null || this.getResults().size() == 0) {
			errors = true;
			errorDescriptions
					.add("FormModel.Results.length must be greather than 0");
		}

		for(Result r:this.getResults())
			errors|=r.validate(errorDescriptions);
		

		// Comprobar que todos los campos existen??
		// y lanzar warnings
		if (this.getFields() != null)
			for (Field f : this.getFields()) {
				if (!filler.isElementPresent(f.getLocator())) {
					LogSystem.warn("Field: " + f.getFieldId()
							+ " with locator:" + f.getLocator().getValue()
							+ " of type "
							+ f.getLocator().getClass().toString());
				}
			}

		return errors;
	}

	public Field getFieldByFieldId(String fieldId)  {
		Field retF=null;
		for (Field f : getFields())
			if (fieldId.equals(f.getFieldId()))
				retF=f;
			
		return retF;
		
	}

}
