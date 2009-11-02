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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.lsi.us.es.reaper.Core.Configurations;
import org.lsi.us.es.reaper.Core.IFormFiller;
import org.lsi.us.es.reaper.Core.LogSystem;
import org.lsi.us.es.reaper.Core.ReapingProcess;
import org.lsi.us.es.reaper.Core.Exceptions.ReapingProccessException;
import org.lsi.us.es.reaper.FormLanguage.Locators.Xpath;
import org.lsi.us.es.reaper.QueryLanguage.EventEnumeration;
import org.lsi.us.es.reaper.QueryLanguage.Query;
import org.lsi.us.es.reaper.QueryLanguage.ScriptVariable;

public class IterateHub implements Action {
	Xpath product;
	Locator nextPage;

	public Locator getNextPage() {
		return nextPage;
	}

	public void setNextPage(Locator nextPage) {
		this.nextPage = nextPage;
	}

	public Locator getProduct() {
		return product;
	}

	public void setProduct(Xpath product) {
		this.product = product;
	}

	public boolean apply(IFormFiller formFiller, Query q)
			throws ReapingProccessException {
		boolean end = false;
		int elementCount = 0;
		q.launchEvent(EventEnumeration.actionBegin);
		int page=0;
		
		
		while (!end) 
		{
			q.launchEvent(EventEnumeration.iterateHubPageBegin);
			int numberOfMoviesInThisPage = this.getXpathCount(formFiller);
			LogSystem.LogInResults(numberOfMoviesInThisPage+" products in this page("+page+")");

			for (int i = 1; i <= numberOfMoviesInThisPage; i++,elementCount++) {

				Locator anchorLocator = getElementLocatorByIndex(i);
				if (formFiller.isElementPresent(anchorLocator)) {
					formFiller.setVariableValue(ScriptVariable.currentProductLocator, anchorLocator.getExpression());
					q.launchEvent(EventEnumeration.iterateHubProductDetailBegin);
					
					try 
					{
						formFiller.click(anchorLocator);
						formFiller.waitForPageToLoad(Configurations.submitWaitMilliseconds);
					} 
					catch (Exception ex)// error en la respuesta del servidor, o en el script
					{
						String msg="Product: "+elementCount+" ["+ i+ " of page "+page+"] (Locator:"+anchorLocator.getExpression()+")\n ERROR"+ ex.toString();
						LogSystem.LogInResults(msg);
						continue;
					}
					formFiller.setVariableValue(ScriptVariable.currentProductLocator, null);
					
					
			
					//guardando en disco
					try {
						PrintWriter fw = new PrintWriter(new FileOutputStream(
								ReapingProcess.getCurrentDirectoryName()+"/"+
										+ ReapingProcess.getAttemptId() + "-"
										+ elementCount + ".html"));

						fw.write(formFiller.getCurrentHtmlContent());
						fw.close();
					} catch (FileNotFoundException ex) 
					{
						throw new ReapingProccessException(ex);
					}

					formFiller.goBack();
					formFiller.waitForPageToLoad(Configurations.submitWaitMilliseconds);
					
					formFiller.setVariableValue(ScriptVariable.currentProductLocator, anchorLocator.getExpression());
					q.launchEvent(EventEnumeration.iterateHubProductDetailFinished);
					formFiller.setVariableValue(ScriptVariable.currentProductLocator, null);
				}
				else
				{
					String msg="Product: "+elementCount+" ["+ i+ " of page "+page+"] NOT FOUND (Locator:"+anchorLocator.getExpression()+")";
					LogSystem.LogInResults(msg);
				}
				
			}
			q.launchEvent(EventEnumeration.iterateHubPageFinished);
			
			if (this.getNextPage()!=null && formFiller.isElementPresent(this.getNextPage())) {
				formFiller.click(this.getNextPage());
				try
				{
					formFiller.waitForPageToLoad(Configurations.submitWaitMilliseconds);
					page++;
				}catch(Exception ex){}
			} else
				end = true;
		}
		q.launchEvent(EventEnumeration.actionFinished);
		return true;
	}

	private Locator getElementLocatorByIndex(int i) {
		Xpath locator = new Xpath();
		String productExpression = getProduct().getValue();
		Pattern p = Pattern.compile("(.*)child\\:\\:([^/]*)((/|\\[).*)");
		Matcher m = p.matcher(productExpression);
		m.find();
		productExpression = m.group(1) + m.group(2) + "[" + i + "]"
				+ m.group(3);
		locator.setValue(productExpression);
		return locator;

	}

	private int getXpathCount(IFormFiller formFiller) {
		String productExpression = getProduct().getValue();
		Pattern p = Pattern.compile("(.*)child\\:\\:([^/]*)(/|\\[.*)");
		Matcher m = p.matcher(productExpression);

		m.find();
		productExpression = m.group(1) + m.group(2);

		Xpath x = new Xpath();
		x.setValue(productExpression);
		return formFiller.getXpathCount(x);
	}

	public boolean validate(List<String> errorDescriptions) {
		// si existe una iterate hub action comprobar
		// que existe la cadena ::child una y solo una vez product
		boolean errors = false;
		Pattern p = Pattern.compile("child\\:\\:([^/\\[]*)");
		Matcher m = p.matcher(product.getValue());
		errors = !m.find();
		if (errors)
			errorDescriptions
					.add("iterateHub product xpath expression incorrect");

		return errors;
	}

}
