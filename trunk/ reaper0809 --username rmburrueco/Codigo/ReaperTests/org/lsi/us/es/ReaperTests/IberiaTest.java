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

import org.junit.Assert;
import org.junit.Test;
import org.lsi.us.es.reaper.Core.ReapingProcess;
import org.lsi.us.es.reaper.Core.Exceptions.JavaScriptException;
import org.lsi.us.es.reaper.Core.Exceptions.LoadingModelException;
import org.lsi.us.es.reaper.Core.Exceptions.ReapingProccessException;

public class IberiaTest {

	@Test
	public void iberiaFormFillingTest() throws LoadingModelException,
			JavaScriptException, ReapingProccessException {
		ReapingProcess p = new ReapingProcess();
		Assert.assertFalse(p.start("Examples/iberia/Iberia-formModel.xml",
				"Examples/iberia/Iberia-queryModel.xml"));
	}
}
