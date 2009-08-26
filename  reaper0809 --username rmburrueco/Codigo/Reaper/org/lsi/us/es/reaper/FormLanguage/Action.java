/* Authors:
 *  
 * Pablo Iñigo Blasco
 * Rosa María Burrueco
 *  
 * */

package org.lsi.us.es.reaper.FormLanguage;

import java.util.List;

import org.lsi.us.es.reaper.Core.IFormFiller;
import org.lsi.us.es.reaper.Core.Exceptions.ReapingProccessException;
import org.lsi.us.es.reaper.QueryLanguage.Query;

public interface Action 
{
	boolean apply(IFormFiller formFiller,Query query) throws ReapingProccessException;

	boolean validate(List<String> errorDescriptions);
}
