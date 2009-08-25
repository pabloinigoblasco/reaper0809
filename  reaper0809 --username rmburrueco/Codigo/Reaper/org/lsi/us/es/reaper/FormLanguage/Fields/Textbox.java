package org.lsi.us.es.reaper.FormLanguage.Fields;

import org.lsi.us.es.reaper.Core.IFormFiller;
import org.lsi.us.es.reaper.Core.Exceptions.JavaScriptException;
import org.lsi.us.es.reaper.Core.Exceptions.ReapingProccessException;
import org.lsi.us.es.reaper.FormLanguage.Field;
import org.lsi.us.es.reaper.QueryLanguage.Value;

public class Textbox extends Field {

	@Override
	public void setValue(Value val, String v, IFormFiller filler)
			throws JavaScriptException, ReapingProccessException {
		try {
			filler.typeText(this.getLocator(), v);
		} catch (Exception ex) {
			throw new ReapingProccessException(ex);
		}
	}
}
