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

package org.lsi.us.es.reaper.Core;

public class Configurations {
	public static String FormModelMappingFile ="formmodel-mapping.xml";
	public static String QueryModelMappingFile ="querymodel-mapping.xml";
	
	public static String SeleniumNavigator="*firefox";
	public static String FormFillerStrategy="org.lsi.us.es.reaper.SeleniumFormFillerStrategy.SeleniumFillerApplication";
	public static String submitWaitMilliseconds="20000";
	public static String afterEventsCodeWaitMilliseconds="500";
	public static String OutputDirectory="./Results/";
	public static String BrowserSpeed = "10";
	public static int SeleniumPort=4444;
	
}
