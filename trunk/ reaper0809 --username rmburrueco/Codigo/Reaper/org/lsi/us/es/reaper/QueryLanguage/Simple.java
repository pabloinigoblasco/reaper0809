package org.lsi.us.es.reaper.QueryLanguage;

import java.util.List;

import org.lsi.us.es.reaper.FormLanguage.Field;

public class Simple extends Assignment{

		public Simple()
		{
			useValueAs=ValueEntryType.display;
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
		public void setField(Field value)
		{
			f=value;
		}
}
