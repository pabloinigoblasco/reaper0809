package org.lsi.us.es.reaper.FormLanguage;

import java.net.MalformedURLException;
import java.net.URL;

import org.lsi.us.es.reaper.Core.ReapingProcess;
import org.lsi.us.es.reaper.Core.Exceptions.LoadingModelException;


public class HttpGetRequest implements ReachFormMethod 
{

	URL requestUrl;
	
	public String getRequestUrl() {
		return requestUrl.toString();
	}

	public void setRequestUrl(String requestUrl) throws LoadingModelException {
		
		
		try {
			this.requestUrl = new URL(requestUrl);
		} catch (MalformedURLException e) {
			throw new LoadingModelException(e);
		}
	}

	public void navigateToForm() {
		ReapingProcess.getFormFiller().navigate(getRequestUrl());
		
	}

}
