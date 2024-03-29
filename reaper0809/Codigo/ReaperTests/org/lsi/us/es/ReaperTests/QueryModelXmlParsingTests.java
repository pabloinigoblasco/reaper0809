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

import org.junit.*;
import org.lsi.us.es.reaper.Core.ReapingProcess;
import org.lsi.us.es.reaper.Core.Exceptions.LoadingModelException;
import org.lsi.us.es.reaper.QueryLanguage.*;

public class QueryModelXmlParsingTests {
	@Test
	public void ParseXML() {
		Query q = loadQuery();

		Assert.assertEquals(q.getFormURL(), "testFormModelName");
	}

	private Query loadQuery() {
		ReapingProcess process = new ReapingProcess();
		Query q = null;
		try {
			q = process.loadQueryModel("xmlParsingTests-queryModel.xml");
		} catch (LoadingModelException e) {

			Assert.fail(e.getMessage());
		}
		return q;
	}
}
