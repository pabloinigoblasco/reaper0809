package org.lsi.us.es.reaper.FormLanguage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.lsi.us.es.reaper.Core.Configurations;
import org.lsi.us.es.reaper.Core.IFormFiller;
import org.lsi.us.es.reaper.Core.ReapingProcess;
import org.lsi.us.es.reaper.Core.Exceptions.ReapingProccessException;
import org.lsi.us.es.reaper.FormLanguage.Locators.Xpath;
import org.lsi.us.es.reaper.QueryLanguage.EventEnumeration;
import org.lsi.us.es.reaper.QueryLanguage.Query;
import org.lsi.us.es.reaper.QueryLanguage.ScriptVariable;

public class IterateHub implements Action {
	
	Locator product;
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
	public void setProduct(Locator product) {
		this.product = product;
	}

	public boolean apply(IFormFiller formFiller,Query q) throws ReapingProccessException 
	{
		boolean end = false;
		int elementCount=0;
		q.launcEvent(EventEnumeration.actionBegin);
		q.launcEvent(EventEnumeration.iterateHubNavigateInBegin);
		while (!end) {
			int numberOfMoviesInThisPage = this.getXpathCount(formFiller);

			for (int i = 1; i <= numberOfMoviesInThisPage; i++) {
				
				Locator anchorLocator = getElementLocatorByIndex(i);
				if (formFiller.isElementPresent(anchorLocator)) {
					formFiller.click(anchorLocator);
					formFiller.waitForPageToLoad(Configurations.submitWaitMilliseconds);
					elementCount++;
					try 
					{
						PrintWriter fw = 
							new PrintWriter(
							new FileOutputStream(Configurations.OutputDirectory + ReapingProcess.getAttemptId() +"-"+elementCount+ ".html"));
						
						fw.write(formFiller.getCurrentHtmlContent());
						fw.close();
					} catch (FileNotFoundException ex) {
						throw new ReapingProccessException(ex);
					}

					formFiller.goBack();
					formFiller.waitForPageToLoad(Configurations.submitWaitMilliseconds);
				}
			}
			q.launcEvent(EventEnumeration.iterateHubPageFinished);

			if (formFiller.isElementPresent(this.getNextPage())) 
			{
				formFiller.click(this.getNextPage());
				formFiller.waitForPageToLoad(Configurations.submitWaitMilliseconds);
			} 
			else
				end = true;
		}
		q.launcEvent(EventEnumeration.actionFinished);
		return true;
	}
	
	private Locator getElementLocatorByIndex(int i) 
	{
		Xpath locator=new Xpath();
		String productExpression=getProduct().getValue();
		Pattern p = Pattern.compile("(.*)child\\:\\:([^/\\[]*)((/|\\[).*)");
		Matcher m = p.matcher(productExpression);
		m.find();
		productExpression=m.group(1)+m.group(2)+"["+i+"]"+m.group(3);
		locator.setValue(productExpression);
		return locator;
		
	}
	
	private int getXpathCount(IFormFiller formFiller) 
	{
		String productExpression=getProduct().getValue();
		Pattern p = Pattern.compile("(.*)child\\:\\:([^/\\[]*)(/|\\[.*)");
		Matcher m = p.matcher(productExpression);
		
		m.find();
		productExpression=m.group(1)+m.group(2);
		
		Xpath x=new Xpath();
		x.setValue(productExpression);
		return formFiller.getXpathCount(x);
	}

}
