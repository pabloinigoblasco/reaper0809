package org.lsi.us.es.reaper.SeleniumFormFillerStrategy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.lsi.us.es.reaper.Core.Configurations;
import org.lsi.us.es.reaper.Core.IFormFiller;
import org.lsi.us.es.reaper.Core.ReapingProcess;
import org.lsi.us.es.reaper.Core.Exceptions.LoadingModelException;
import org.lsi.us.es.reaper.Core.Exceptions.ReapingProccessException;
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
			browser.setBrowserLogLevel("warn");
		}

		browser.open(url);
		browser.waitForPageToLoad(Configurations.submitWaitMilliseconds);
	}

	public void releaseResources() {

		if (browser != null) {
			browser.close();
			browser = null;
		}
		if (server != null) {
			server.stop();
			server = null;
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

	public String evalScript(String script) {

		String ret = browser.getEval(script);
		return ret;
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

		for (JavaScript j : scripts) {
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(j.toUrl().openStream()));

				String line = null;
				StringBuilder stringBuilder = new StringBuilder();
				String ls = System.getProperty("line.separator");

				while ((line = reader.readLine()) != null) {
					stringBuilder.append(line);
					stringBuilder.append(ls);
				}
				browser.addScript(stringBuilder.toString(), j.hashCode() + "");

			} catch (IOException e) {
				throw new LoadingModelException(e);
			}
		}

	}

	public void runScript(String script) {
		browser.runScript(script);

	}

	public void addScript(String src, String tag) {
		browser.addScript(src, tag);

	}

	public void unregisterScript(String tag) {
		browser.removeScript(tag);

	}


	public void registerVariable(ScriptVariable key, String value) {
		ReapingProcess.getFormFiller().unregisterScript(key.toString());
		String setValue="null";
		if(value!=null)
			setValue="'"+value+"'";
			
		ReapingProcess.getFormFiller().addScript("var "+key.toString()+"="+setValue+";",key.toString());
		
	}
}
