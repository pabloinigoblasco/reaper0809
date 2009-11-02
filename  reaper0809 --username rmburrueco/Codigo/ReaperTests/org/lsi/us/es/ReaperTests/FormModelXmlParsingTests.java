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

package org.lsi.us.es.ReaperTests;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.*;
import org.lsi.us.es.reaper.Core.ReapingProcess;
import org.lsi.us.es.reaper.Core.Exceptions.LoadingModelException;
import org.lsi.us.es.reaper.FormLanguage.*;
import org.lsi.us.es.reaper.FormLanguage.Locators.*;
import org.lsi.us.es.reaper.FormLanguage.Fields.*;

public class FormModelXmlParsingTests {
	@Test
	public void ParseXMLFields() {
		Form f = loadForm();

		Assert.assertTrue(f.getIdentificationUrl().equals("testFormModelName"));
		Assert.assertSame(f.getFields().size(), 6);

		Assert.assertTrue(f.getFields().get(0) instanceof Textbox);
		Textbox t = (Textbox) f.getFields().get(0);
		Assert.assertEquals(t.getFieldId(), "testIdTextBox");
		Assert.assertFalse(t.isRequired());
		Assert.assertNotNull(t.getLocator());
		Assert.assertTrue(t.getLocator() instanceof Xpath);
		Assert.assertEquals(t.getLocator().getValue(), "abc/cde/fgh");

		Assert.assertTrue(f.getFields().get(2) instanceof Select);
		Select s = (Select) f.getFields().get(2);
		Assert.assertEquals(s.getFieldId(), "testIdSelect");

		Assert.assertNotNull(s.getLocator());
		Assert.assertTrue(s.getLocator() instanceof Id);
		Assert.assertEquals(s.getLocator().getValue(), "testIdLocator");

		Assert.assertTrue(f.getFields().get(4) instanceof Checkbox);
		Checkbox cb = (Checkbox) f.getFields().get(4);
		Assert.assertEquals(cb.getFieldId(), "testIdCheckBox");
		Assert.assertTrue(cb.isRequired());
		Assert.assertTrue(cb.getLocator() instanceof Text);
		Assert.assertEquals(cb.getLocator().getValue(), "testTextLocator");

		Assert.assertTrue(f.getFields().get(5) instanceof List);
		List l = (List) f.getFields().get(5);
		Assert.assertEquals(l.getFieldId(), "testIdList");
		Assert.assertTrue(l.getLocator() instanceof Name);
		Assert.assertEquals(l.getLocator().getValue(), "testNameLocator");
	}

	@Test
	public void ParseXMLResults() {
		Form f = loadForm();

		Assert.assertNotNull(f.getResults());
		Assert.assertSame(f.getResults().size(), 4);

		Assert
				.assertTrue(f.getResults().get(0).getActions().get(0) instanceof Discard);

		Assert
				.assertTrue(f.getResults().get(1).getActions().get(0) instanceof Classify);
		Classify c = (Classify) f.getResults().get(1).getActions().get(0);
		Assert.assertEquals(c.getTag(), "testTagLabel");

		Assert
				.assertTrue(f.getResults().get(2).getActions().get(0) instanceof Store);
	}

	@Test
	public void ParseXMLReachFormMethod() {
		Form f = loadForm();
		Assert.assertNotNull(f.getReachFormMethod());
		Assert.assertTrue(f.getReachFormMethod() instanceof HttpGetRequest);
		Assert.assertEquals(((HttpGetRequest) f.getReachFormMethod())
				.getRequestUrl(), "http://google.com");
	}

	private Form loadForm() {
		ReapingProcess process = new ReapingProcess();
		Form f = null;
		try {
			f = process.loadFormModel("xmlParsingTests-formModel.xml");
		} catch (LoadingModelException e) {

			Assert.fail(e.getMessage());
		}
		return f;
	}

	@Test
	public void TestRegexs() {
		String s = "//table/child::tr[@x='d']/a";
		Pattern p = Pattern.compile("(.*)child\\:\\:([^/\\[]*)(/|\\[.*)");

		Matcher m = p.matcher(s);

		m.find();
		s = m.group(0);
		s = m.group(1);
		s = m.group(2);
		s = m.group(3);

	}
}
