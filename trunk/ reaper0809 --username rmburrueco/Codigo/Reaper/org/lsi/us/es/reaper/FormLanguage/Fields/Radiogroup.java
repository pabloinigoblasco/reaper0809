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

package org.lsi.us.es.reaper.FormLanguage.Fields;

import org.lsi.us.es.reaper.Core.IFormFiller;
import org.lsi.us.es.reaper.Core.Exceptions.JavaScriptException;
import org.lsi.us.es.reaper.Core.Exceptions.ReapingProccessException;
import org.lsi.us.es.reaper.FormLanguage.Field;
import org.lsi.us.es.reaper.QueryLanguage.Value;

public class Radiogroup extends Field {
	@Override
	public void setValue(Value val, String v, IFormFiller filler)
			throws JavaScriptException, ReapingProccessException {
		try {

			filler.setRadioButtonElement(this.getLocator(), v, val
					.getParentAssignment().getUseValueAs());
		} catch (Exception ex) {
			throw new ReapingProccessException(ex);
		}
	}

}
