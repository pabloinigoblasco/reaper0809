/* 
 * Authors:  
 * 	Pablo Iñigo Blasco
 * 	Rosa María Burrueco
 *  
 * Directed by:
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

public class Checkbox extends Field {

	@Override
	public void setValue(Value val, String v, IFormFiller filler)
			throws JavaScriptException, ReapingProccessException {
		try {
			Boolean check = Boolean.valueOf(v);
			if (check == null) {
				Integer intval = Integer.valueOf(v);
				if (intval == null)
					throw new RuntimeException(
							"Invalid value for a checkbox- Field:"
									+ this.getFieldId() + "=" + v);
				else
					check = (intval == 1);
			}
			filler.setCheckBoxValue(this.getLocator(), check);
		} catch (Exception ex) {
			throw new ReapingProccessException(ex);
		}
	}

}
