/* Authors:
 *  
 * Pablo Iñigo Blasco
 * Rosa María Burrueco
 *  
 * */

package org.lsi.us.es.reaper.Core;

import java.util.Comparator;

import org.lsi.us.es.reaper.QueryLanguage.Simple;

public class simpleAssignmentOrderComparator implements Comparator<Simple>
{

	public int compare(Simple arg0, Simple arg1) {
		return arg0.getPositionOrder()-arg1.getPositionOrder();
	}

}
