/* 
 * Authors:  
 * 	Pablo Iñigo Blasco
 * 	Rosa María Burrueco
 *  
 * Advisors:
 *  	Rafael Corchuelo Gil
 *  	Inmaculada Hernández Salmerón
 *  
 * Universidad de Sevilla 2009
 *  
 * */

package org.lsi.us.es.reaper.Core;

import java.util.List;

import org.lsi.us.es.reaper.Core.Exceptions.LoadingModelException;
import org.lsi.us.es.reaper.FormLanguage.Form;
import org.lsi.us.es.reaper.FormLanguage.Locator;
import org.lsi.us.es.reaper.FormLanguage.Locators.Xpath;
import org.lsi.us.es.reaper.QueryLanguage.JavaScript;
import org.lsi.us.es.reaper.QueryLanguage.ScriptVariable;
import org.lsi.us.es.reaper.QueryLanguage.ValueEntryType;

public interface IFormFiller {
	void navigate(String url);

	void releaseResources();

	boolean isElementPresent(Locator f);

	String getCurrentHtmlContent();

	void setCheckBoxValue(Locator checkbox, boolean checked);

	void setHiddenField(Locator hiddenFiled, String value);

	void setListValues(Locator listLocator, List<String> values,
			ValueEntryType valueEntryType);

	void setSelectedComboElement(Locator combo, String value,
			ValueEntryType valueEntryType);

	void setRadioButtonElement(Locator radioLocator, String value,
			ValueEntryType valueEntryType);

	void typeText(Locator textbox, String value);

	void submit(Locator submit);

	String evalScript(String script);

	String getCurrentUrl();

	void click(Locator anchorLocator);

	void waitForPageToLoad(String submitwaitmilliseconds);

	void goBack();

	int getXpathCount(Xpath x);

	void importScripts(List<JavaScript> javaScriptImports)
			throws LoadingModelException;



	void unregisterScript(String string);

	void setVariableValue(ScriptVariable key, String value);

	void registerVariable(ScriptVariable v, String value);

	void registerFormLocators(Form form);
}
