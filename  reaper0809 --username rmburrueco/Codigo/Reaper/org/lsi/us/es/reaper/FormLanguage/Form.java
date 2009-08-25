package org.lsi.us.es.reaper.FormLanguage;

import java.util.List;

import org.lsi.us.es.reaper.Core.IFormFiller;
import org.lsi.us.es.reaper.Core.LogSystem;
import org.lsi.us.es.reaper.Core.ReapingProcess;
import org.lsi.us.es.reaper.Core.Exceptions.LoadingModelException;

public class Form 
{
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
	
	public boolean checkFields()
	{
		boolean error=false;
		IFormFiller filler=ReapingProcess.getFormFiller();
		if(this.getFields()!=null)
			for(Field f:this.getFields())
			{
				if(!filler.isElementPresent(f.getLocator()))
				{
					error=true;
					LogSystem.warn("Field: "+ f.getFieldId()+" with locator:"+f.getLocator().getValue()+ " of type "+f.getLocator().getClass().toString());
				}
			}
		return error;
	}
	
	public Field getFieldByFieldId(String fieldId) throws LoadingModelException
	{
		for(Field f:getFields())
			if(fieldId.equals(f.getFieldId()))
				return f;
		
		LoadingModelException ex=new LoadingModelException("Field incoherence, field not found:"+fieldId);
		throw ex;
	}

}
