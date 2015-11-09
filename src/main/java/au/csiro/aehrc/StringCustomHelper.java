/**
 * Helper Class to Create Comment and Other specific Custom Strings
 * 
 */
package au.csiro.aehrc;


/**
 * @author Abbas H Safaie
 *	 
 */
public class StringCustomHelper {

	
	private static final String cardioModel="Cardio-";
	private static final String variableModel="Variable-";
	private static final String observationModel="Observation-";
	private static final String linksetModel="Linkset-";
	

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
	
	
	//put model name together from 2 part 
	public static final String modelName(String dataModel,String theme){
		
		return dataModel+theme;
	}
	

	////////////////////////////////////////////////////////////////
	//	model names											     //
   ///////////////////////////////////////////////////////////////
	

	public static String getCardiomodel() {
		return cardioModel;
	}

	public static String getVariablemodel() {
		return variableModel;
	}

	public static String getObservationmodel() {
		return observationModel;
	}

	public static String getLinksetmodel() {
		return linksetModel;
	}
	
	
}
