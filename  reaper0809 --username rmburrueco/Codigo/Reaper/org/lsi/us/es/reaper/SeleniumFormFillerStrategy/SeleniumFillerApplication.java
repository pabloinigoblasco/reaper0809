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

package org.lsi.us.es.reaper.SeleniumFormFillerStrategy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.lsi.us.es.reaper.Core.Configurations;
import org.lsi.us.es.reaper.Core.IFormFiller;

import org.lsi.us.es.reaper.Core.Exceptions.LoadingModelException;
import org.lsi.us.es.reaper.Core.Exceptions.ReapingProccessException;
import org.lsi.us.es.reaper.FormLanguage.Field;
import org.lsi.us.es.reaper.FormLanguage.Form;
import org.lsi.us.es.reaper.FormLanguage.Locator;
import org.lsi.us.es.reaper.FormLanguage.Locators.Xpath;
import org.lsi.us.es.reaper.QueryLanguage.JavaScript;
import org.lsi.us.es.reaper.QueryLanguage.ScriptVariable;
import org.lsi.us.es.reaper.QueryLanguage.ValueEntryType;
import org.openqa.selenium.server.*;
import com.thoughtworks.selenium.*;

public class SeleniumFillerApplication implements IFormFiller {
	private SeleniumServer server;

	protected DefaultSelenium browser;
	List<JavaScript> scripts;

	public SeleniumFillerApplication() throws ReapingProccessException {
		try {
			server = new SeleniumServer();
		} catch (Exception ex) {
			throw new ReapingProccessException(ex);
		}

		try {
			server.start();
		} catch (Exception ex) {
			throw new ReapingProccessException(ex);
		}
	}

	public boolean isElementPresent(Locator l) {

		String locatorExpression = l.getExpression();
		return browser.isElementPresent(locatorExpression);
	}

	public void navigate(String url) {

		if (browser == null) {
			browser = new DefaultSelenium("localhost", 4444,
					Configurations.SeleniumNavigator, url);

			browser.start();
			browser.setBrowserLogLevel("info");
			browser.setSpeed(Configurations.BrowserSpeed);
		}

		browser.open(url);
		browser.waitForPageToLoad(Configurations.submitWaitMilliseconds);
	}

	public void releaseResources() {

		try {
			if (browser != null) {
				browser.close();
				browser = null;
			}
		} catch (Exception ex) {
		}
		try {
			if (server != null) {
				server.stop();
				server = null;
			}
		} catch (Exception ex) {
		}
	}

	public void setHiddenField(Locator hiddenFiled, String value) {
		// TODO Auto-generated method stub
		// debería eliminarse esta feature
		throw new RuntimeException(
				"set hidden field value- invalid feature of the reaper");
		// importScripts();
	}

	public String getCurrentHtmlContent() {
		return browser.getHtmlSource();
	}

	public void setSelectedComboElement(Locator combo, String value,
			ValueEntryType valueEntryType) {
		// String optionxpath=combo.toXpathLocator().getExpression();
		// if(valueEntryType==ValueEntryType.display)
		// optionxpath+="/option[text()=\""+value+"\"]";
		// else
		// optionxpath+="/option[@value=\""+value+"\"]";

		browser.select(combo.getExpression(), value);
	}

	public void setListValues(Locator listLocator, List<String> values,
			ValueEntryType valueEntryType) {
		browser.removeAllSelections(listLocator.getExpression());

		for (String value : values) {
			// String optionxpath=listLocator.toXpathLocator().getExpression();
			// if(valueEntryType==ValueEntryType.display)
			// optionxpath+="/option[text()=\""+value+"\"]";
			// else
			// optionxpath+="/option[@value=\""+value+"\"]";

			browser.addSelection(listLocator.getExpression(), value);

		}
	}

	public void setRadioButtonElement(Locator radioLocator, String value,
			ValueEntryType valueEntryType) {
		String optionxpath = radioLocator.toXpathLocator().getExpression();
		if (valueEntryType == ValueEntryType.display)
			optionxpath += "/.[text()=\"" + value + "\"]";
		else
			optionxpath += "/.[@value=\"" + value + "\"]";
		browser.check(optionxpath);
	}

	public void typeText(Locator textbox, String value) {
		browser.type(textbox.getExpression(), value);
	}

	public void setCheckBoxValue(Locator checkbox, boolean checked) {
		if (checked)
			browser.check(checkbox.getExpression());
		else
			browser.uncheck(checkbox.getExpression());
	}

	public void submit(Locator submit) {
		browser.click(submit.getExpression());
		browser.waitForPageToLoad(Configurations.submitWaitMilliseconds);
	}

	public String getCurrentUrl() {
		return browser.getLocation();
	}

	public void click(Locator anchorLocator) {
		browser.click(anchorLocator.getExpression());
	}

	public int getXpathCount(Xpath x) {

		return (Integer) browser.getXpathCount(x.getValue());
	}

	public void goBack() {
		browser.goBack();
	}

	public void waitForPageToLoad(String submitwaitmilliseconds) {
		browser.waitForPageToLoad(submitwaitmilliseconds);
	}

	public void importScripts(List<JavaScript> javaScriptImports)
			throws LoadingModelException {

		scripts = javaScriptImports;
		importScripts();
	}

	public void importScripts() throws LoadingModelException {

		JavaScript j = null;
		if (scripts != null) {
			for (int i = 0; i < scripts.size(); i++) {
				j = scripts.get(i);
				try {
					BufferedReader reader;
					try {
						reader = new BufferedReader(new InputStreamReader(j
								.toUrl().toURL().openStream()));
					} catch (Exception ex) {
						try {
							reader = new BufferedReader(new FileReader(j
									.toUrl().getPath()));
						} catch (Exception exb) {
							try
							{
							reader = new BufferedReader(new InputStreamReader(
									getClass().getClassLoader().getResource(
											j.getUrl()).openStream()));
							}catch(Exception exc)
							{
								throw new LoadingModelException(j.getUrl());
							}
						}

					}

					String line = null;
					StringBuilder stringBuilder = new StringBuilder();
					String ls = System.getProperty("line.separator");

					while ((line = reader.readLine()) != null) {
						stringBuilder.append(line);
						stringBuilder.append(ls);
					}
					browser.addScript(stringBuilder.toString(), j.hashCode()
							+ "");

				} catch (IOException e) {
					throw new LoadingModelException(e.getMessage() + "\n"
							+ j.getUrl());
				}
			}
		}

	}

	// ------------------sistema de script----------------------
	Map<ScriptVariable, String> variableMap = new TreeMap<ScriptVariable, String>();

	public String evalScript(String script) {

		flushVariables();
		String ret = browser.getEval(script);
		return ret;
	}

	public void runScript(String script) {
		browser.removeScript("running_Script");
		browser.addScript(script, "running_Script");
	}

	public void addScript(String src, String tag) {
		browser.removeScript(tag);
		flushVariables();
		browser.addScript(src, tag);

	}

	public void unregisterScript(String tag) {
		browser.removeScript(tag);
	}

	public void setVariableValue(ScriptVariable key, String value) {

		variableMap.put(key, value);
	}

	private String auxiliarSetVariableValue(String v, String value,
			boolean define) {
		String setValue = "null";
		if (value != null)
			setValue = "'" + value.replace("\"", "\\\"").replace("'", "\\'")
					+ "'";

		if (define)
			setValue = "var " + v + "=" + setValue + ";";
		else
			setValue = v.toString() + "=" + setValue + ";";
		return setValue;
	}

	public void registerVariable(ScriptVariable v, String value) {
		browser.addScript(auxiliarSetVariableValue(v.toString(), value, true),
				v.toString());
	}

	private void flushVariables() {
		browser.removeScript("updateVariables");
		String variables = new String();
		for (Entry<ScriptVariable, String> e : variableMap.entrySet()) {

			String line = variables += auxiliarSetVariableValue(e.getKey()
					.toString(), e.getValue(), false);
			variables += line;
		}

		browser.addScript(variables, "updateVariables");
	}

	// ------------------fin sistema de script----------------------

	public void registerFormLocators(Form form) {
		String ctx = "var reaperLocators=new Object();";
		for (Field f : form.getFields()) {
			ctx += auxiliarSetVariableValue("reaperLocators." + f.getFieldId(),
					f.getLocator().getExpression(), false);
		}
		browser.addScript(ctx, "reaperContext");

	}

}
