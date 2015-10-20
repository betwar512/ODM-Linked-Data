/**
 * Helper Class to Create Comment and Other specific Custom Strings
 * 
 */
package com.mycompany.app;

/**
 * @author Abbas H Safaie
 *	 
 */
public class StringCustomHelper {

	

	//GroupType of the item for theme 
	public static String groupType(String itemOid){		
		String[] comparStrs=itemOid.split("_");
		String group=comparStrs[1];
			return group;
		}
	

	
	
	//check is Numeric or not with low cost WARNING only for positive numbers 
	public static boolean isNumeric(String str)
	{
	    for (char c : str.toCharArray())
	    {
	        if (!Character.isDigit(c)) return false;
	    }
	    return true;
	}
	

	
}
