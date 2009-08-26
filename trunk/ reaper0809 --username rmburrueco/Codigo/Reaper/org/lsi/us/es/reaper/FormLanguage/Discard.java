/* Authors:
 *  
 * Pablo Iñigo Blasco
 * Rosa María Burrueco
 *  
 * */

package org.lsi.us.es.reaper.FormLanguage;

import java.util.List;

import org.lsi.us.es.reaper.Core.IFormFiller;
import org.lsi.us.es.reaper.QueryLanguage.Query;

public class Discard implements Action{

	public boolean apply(IFormFiller formFiller,Query q)  {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean validate(List<String> errorDescriptions) {
		// TODO Auto-generated method stub
		return true;
	}

}
