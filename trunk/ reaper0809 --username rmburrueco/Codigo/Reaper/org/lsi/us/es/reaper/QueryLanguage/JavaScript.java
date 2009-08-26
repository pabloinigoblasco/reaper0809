/* Authors:
 *  
 * Pablo Iñigo Blasco
 * Rosa María Burrueco
 *  
 * */

package org.lsi.us.es.reaper.QueryLanguage;

import java.net.MalformedURLException;
import java.net.URL;

import org.lsi.us.es.reaper.Core.Exceptions.LoadingModelException;

public class JavaScript 
{
	URL url;

	public URL toUrl() {
		return url;
	}
	public String getUrl() {
		return url.toString();
	}

	public void setUrl(String url) throws LoadingModelException {
		try {
			this.url = new URL(url);
		} catch (MalformedURLException e) {
			throw new LoadingModelException(e);
		}
	}

}
