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

package org.lsi.us.es.reaper.FormLanguage;

import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import java.util.regex.Matcher;

public class Result {
	List<Action> actions;
	String contentRegExp;
	String urlRegexp;

	Pattern purl;
	Pattern pcontent;

	public List<Action> getActions() {
		return actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	public String getContentRegExp() {
		return contentRegExp;
	}

	public void setContentRegExp(String contentRegExp) {
		this.contentRegExp = contentRegExp;
	}

	public String getUrlRegExp() {
		return urlRegexp;
	}

	public void setUrlRegExp(String urlRegexp) {
		this.urlRegexp = urlRegexp;
	}

	public boolean applicable(String currentHtmlContent, String currentUrl) {
		boolean urlOK = true;
		boolean contentOK = true;

		if (getUrlRegExp() != null) {
			Matcher murl = purl.matcher(currentUrl);
			urlOK = murl.find();
		}

		if (getContentRegExp() != null) {
			Matcher mcontent = pcontent.matcher(currentHtmlContent);
			contentOK = mcontent.find();
		}

		return urlOK && contentOK;
	}

	public boolean validate(List<String> errorDescriptions) {

		boolean errors = false;
		// TODO:comprobar que al menos de cada resultado
		// está relleno o el regExp o el urlregexp
		
		if (getActions() == null || getActions().size() == 0) {
			errors = true;
			errorDescriptions
					.add("every FormModel.Result must have at least one asociated Action: store, discard, clasiffy, IterateHub...");
		} else {
			int noDiscardActions = 0;
			for (Action a : getActions()) {
				if (!(a instanceof Discard))
					noDiscardActions++;
				errors |= a.validate(errorDescriptions);
			}
			if (noDiscardActions == 0) {
				errors = true;
				errorDescriptions
						.add("A result element must have at least one action with type different to Discard");
			}
		}
		try {
			if (getUrlRegExp() != null)
				purl = Pattern.compile(getUrlRegExp());

			if (getContentRegExp() != null)
				pcontent = Pattern.compile(getContentRegExp(),
						Pattern.MULTILINE | Pattern.CANON_EQ);
		} catch (PatternSyntaxException ex) {
			errors = true;
			errorDescriptions
					.add("Result with incorrect regular exressions:\npattern url: "
							+ purl + "content url:" + contentRegExp);
		}

		return errors;
	}

}
