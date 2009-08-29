/* 
 * Authors:  
 * 	Pablo Iñigo Blasco
 * 	Rosa María Burrueco
 *  
 * Directed by:
 *  	Rafael Corchuelo Gil
 *  	Inmaculada Hernández Salmerón
 *  
 * Universidad de Sevilla 2009
 *  
 * */
package org.lsi.us.es.reaper.QueryLanguage;

import java.net.URI;
import java.net.URISyntaxException;

import org.lsi.us.es.reaper.Core.Exceptions.LoadingModelException;

public class JavaScript 
{
	URI url;
	public URI toUrl() {
		return url;
	}
	public String getUrl() {
		return url.toString();
	}

	public void setUrl(String url) throws LoadingModelException {
		try {
			this.url = new URI(url);
		} catch (URISyntaxException e) {
			throw new LoadingModelException(e);
		}
	}

}
