package org.lsi.us.es.reaper.QueryLanguage;

import org.lsi.us.es.reaper.Core.ReapingProcess;
import org.lsi.us.es.reaper.Core.Exceptions.JavaScriptException;

public class Value {

	String val;
	boolean isJavascriptExression;
	Simple parentAssignment;

	public String evaluate(String htmlContent) throws JavaScriptException {
		String result;
		
		
		
		if (isJavascriptExpression())
		{	
			try
			{
				result = ReapingProcess.getFormFiller().evalScript(getVal());
			}
			catch(Exception ex)
			{
				throw new JavaScriptException(ex);
			}
		}
		else
			result = val;


		return result;
	}

	public boolean isJavascriptExpression() {
		return isJavascriptExression;
	}

	public void setJavascriptExpression(boolean isJavascriptExression) {
		this.isJavascriptExression = isJavascriptExression;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public Simple getParentAssignment() {
		return parentAssignment;
	}

	public void setParentAssignment(Simple parentAssignment) {
		this.parentAssignment = parentAssignment;
	}

}
