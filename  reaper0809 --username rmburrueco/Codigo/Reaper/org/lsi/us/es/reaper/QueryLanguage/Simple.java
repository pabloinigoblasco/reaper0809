/* 
 * Authors:  
 * 	Pablo Iñigo Blasco
 * 	Rosa María Burrueco
 *  
 * Advisors:
 *  	Rafael Corchuelo Gil
 *  	Inmaculada Hernández Salmerón
 *  
 * Universidad de Sevilla 2009
 *  
 * */

package org.lsi.us.es.reaper.QueryLanguage;

import java.util.List;

import org.lsi.us.es.reaper.FormLanguage.Field;
import org.lsi.us.es.reaper.FormLanguage.Form;

public class Simple extends Assignment {

	public Simple() {
		useValueAs = ValueEntryType.display;
	}

	ValueEntryType useValueAs;
	List<Value> values;
	String fieldId;
	Field f;
	int positionOrder;

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public ValueEntryType getUseValueAs() {
		return useValueAs;
	}

	public void setUseValueAs(ValueEntryType useValueAs) {
		this.useValueAs = useValueAs;
	}

	public List<Value> getValues() {
		return values;
	}

	public void setValues(List<Value> values) {
		this.values = values;
	}

	public int getPositionOrder() {
		return positionOrder;
	}

	public void setPositionOrder(int positionOrder) {
		this.positionOrder = positionOrder;
	}

	public Field getField() {

		return f;
	}

	public void setField(Field value) {
		f = value;
	}

	@Override
	public boolean validate(List<String> errors, Form f) {
		boolean error = false;
		if (f.getFieldByFieldId(getFieldId()) == null) {
			error = true;
			errors
					.add("Query assignment have a fieldid that does not exist at formModel: "
							+ getFieldId());
		}

		if (getValues() == null || getValues().size() == 0) {
			error = true;
			errors
					.add("Simple assignments must have at least one value element");
		} else {
			// Comprobar si dos valores de un mismo simple assignment están
			// repetidos
			for (Value v : getValues()) {
				for (Value v2 : getValues()) {
					if (v != v2 && v.getVal().equals(v2.getVal()))
						errors
								.add("Simple assignments cannot have two equals value elements");
				}
			}
		}
		return error;

	}
}
