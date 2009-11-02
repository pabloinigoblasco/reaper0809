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

package org.lsi.us.es.reaper.QueryLanguage;

import java.util.List;

import org.lsi.us.es.reaper.FormLanguage.Form;

public class Dependant extends Assignment {
	List<Group> groups;

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	@Override
	public boolean validate(List<String> errors, Form f) {
		boolean error = false;
		if (getGroups() != null && getGroups().size() > 1) {
			for (Group g : getGroups()) {
				if (g.getSimpleAssigments() != null
						&& g.getSimpleAssigments().size() > 0) {

					for (Simple s : g.getSimpleAssigments())
						error |= s.validate(errors, f);
				}
				else
				{
					error=true;
					errors.add("a dependant group needs at least one simple assignment");
				}
			}
		} else {
			error = true;
			errors.add("Dependat field needs at least two groups");
		}

		return error;
	}

}
