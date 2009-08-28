package org.lsi.us.es.ReaperTests;

import org.junit.Assert;
import org.junit.Test;
import org.lsi.us.es.reaper.Core.ReapingProcess;
import org.lsi.us.es.reaper.Core.Exceptions.JavaScriptException;
import org.lsi.us.es.reaper.Core.Exceptions.LoadingModelException;
import org.lsi.us.es.reaper.Core.Exceptions.ReapingProccessException;

public class amazonTest {
	@Test
	public void amazonTest() throws LoadingModelException, JavaScriptException, ReapingProccessException {
		
		ReapingProcess p = new ReapingProcess();
		Assert.assertFalse(p.start("amazon-formModel.xml", "amazon-queryModel.xml"));
		
	}
}
