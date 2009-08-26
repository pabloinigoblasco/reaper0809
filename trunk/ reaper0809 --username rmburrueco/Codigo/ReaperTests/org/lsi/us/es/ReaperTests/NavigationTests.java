package org.lsi.us.es.ReaperTests;

import org.junit.*;
import org.lsi.us.es.reaper.Core.ReapingProcess;
import org.lsi.us.es.reaper.Core.Exceptions.JavaScriptException;
import org.lsi.us.es.reaper.Core.Exceptions.LoadingModelException;
import org.lsi.us.es.reaper.Core.Exceptions.ReapingProccessException;
import org.lsi.us.es.reaper.FormLanguage.Form;


public class NavigationTests{


	@Test
	public void navigateToFormTest()
	{
		ReapingProcess p=new ReapingProcess();
		try {
			Form f=p.loadFormModel("Iberia-formModel.xml");
			f.getReachFormMethod().navigateToForm();
			
		} catch (LoadingModelException e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		
	}
	
	@Test
	public void iberiaFormFillingTest() throws LoadingModelException, JavaScriptException, ReapingProccessException
	{
		ReapingProcess p=new ReapingProcess();
		Assert.assertFalse(p.start("Iberia-formModel.xml", "Iberia-queryModel.xml"));
	}
}
