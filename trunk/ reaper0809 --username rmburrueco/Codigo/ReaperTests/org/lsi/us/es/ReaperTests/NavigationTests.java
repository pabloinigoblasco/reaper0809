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
import org.lsi.us.es.reaper.FormLanguage.Form;

public class NavigationTests {

	@Test
	public void navigateToFormTest() {
		ReapingProcess p = new ReapingProcess();
		try {
			Form f = p.loadFormModel("Iberia-formModel.xml");
			f.getReachFormMethod().navigateToForm();

		} catch (LoadingModelException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}

	}

}
