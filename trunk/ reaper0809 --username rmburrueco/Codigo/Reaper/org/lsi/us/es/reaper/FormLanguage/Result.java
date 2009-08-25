package org.lsi.us.es.reaper.FormLanguage;

import java.util.List;
import java.util.regex.Pattern;

import java.util.regex.Matcher;



public class Result 
{
	List<Action> actions;
	String contentRegExp;
	String urlRegexp;
	
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
	
	public boolean applicable(String currentHtmlContent, String currentUrl)
	{
		boolean urlOK=true;
		boolean contentOK=true;
		
		if(getUrlRegExp()!=null)
		{
			Pattern purl=Pattern.compile(getUrlRegExp());
			Matcher murl=purl.matcher(currentUrl);
			urlOK=murl.find();
		}
		
		if(getContentRegExp()!=null)
		{
			Pattern pcontent=Pattern.compile(getContentRegExp(),Pattern.MULTILINE|Pattern.CASE_INSENSITIVE|Pattern.CANON_EQ);
			Matcher mcontent=pcontent.matcher(currentHtmlContent);
			contentOK=mcontent.find();
		}
		
		return urlOK && contentOK;
	}

}
