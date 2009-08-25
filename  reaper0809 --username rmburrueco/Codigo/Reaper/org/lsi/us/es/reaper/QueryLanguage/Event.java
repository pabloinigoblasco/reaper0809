package org.lsi.us.es.reaper.QueryLanguage;

public class Event 
{
	EventEnumeration name;
	String scriptExpression;
	public EventEnumeration getName() {
		return name;
	}
	public void setName(EventEnumeration name) {
		this.name = name;
	}
	public String getScriptExpression() {
		return scriptExpression;
	}
	public void setScriptExpression(String scriptExpression) {
		this.scriptExpression = scriptExpression;
	}

}
