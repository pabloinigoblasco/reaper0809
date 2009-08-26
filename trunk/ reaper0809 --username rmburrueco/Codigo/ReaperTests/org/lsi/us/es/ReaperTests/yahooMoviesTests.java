package org.lsi.us.es.ReaperTests;

import org.junit.Assert;
import org.junit.Test;
import org.lsi.us.es.reaper.Core.ReapingProcess;
import org.lsi.us.es.reaper.Core.Exceptions.JavaScriptException;
import org.lsi.us.es.reaper.Core.Exceptions.LoadingModelException;
import org.lsi.us.es.reaper.Core.Exceptions.ReapingProccessException;

public class yahooMoviesTests {
	@Test
	public void yahooMoviesTest() throws LoadingModelException, JavaScriptException, ReapingProccessException {
		
		ReapingProcess p = new ReapingProcess();
		Assert.assertFalse(p.start("yahooMovies-formModel.xml", "yahooMovies-queryModel.xml"));
		
	}
}
