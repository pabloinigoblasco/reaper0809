/* Authors:
 *  
 * Pablo Iñigo Blasco
 * Rosa María Burrueco
 *  
 * */

package org.lsi.us.es.reaper.QueryLanguage;

import java.util.List;

import org.lsi.us.es.reaper.FormLanguage.Form;

public abstract class Assignment {

	public abstract boolean validate(List<String> errors,Form f) ;

}
