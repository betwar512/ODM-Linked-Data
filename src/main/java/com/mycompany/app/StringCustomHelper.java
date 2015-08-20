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

	
	public StringCustomHelper() {
		// TODO Auto-generated constructor stub
	}

	
	  //================================================================================
    // Comment
    //================================================================================
	
	
	/**
	 * Comment Generator
	 * INput CommentModel CLass
	 * Output String Comment
	 */
	public static String Comment(CommentModel commentDto){
		
		//Final Purpose
		String purpose="";
		String comment="";
		String subjectKey="";
		String event="";
		String[] subject=commentDto.subjectKey.split("_"); 
			subjectKey=subject[subject.length-1]; //subjectNumber
		//Observation purpose 
		//check if number or not 
		String[] purposePeaces=commentDto.itemOid.split("_");
		//check if number or not 
		boolean number=isNumeric(purposePeaces[purposePeaces.length-1]);
		if(number){//is number then keep last 2 part 	
			//Final purpose
			purpose = purposePeaces[purposePeaces.length-2].toLowerCase() +" " + purposePeaces[purposePeaces.length-1];
		}else{//if not number keep the last part only 	
			//Final Purpose
			purpose=purposePeaces[purposePeaces.length-1].toLowerCase();	
		}
	
			//Event 
			String[] eventParts=commentDto.eventOid.split("_");
		event=eventParts[eventParts.length-1].toLowerCase();
		
		//Final Comment
		comment="This observation is about '" + commentDto.dataSet+" "+ purpose+ "', from Subject '"+subjectKey + "', from Event '"+event+"'.";
		
		return comment;		
	}
	
	  //================================================================================
    // Label
    //================================================================================
	//Create Label for RDF
	public static String label(String str){
		
		String label="";
		String[] purposePeaces=str.split("_");
		//check if number or not 
		boolean number=isNumeric(purposePeaces[purposePeaces.length-1]);
		if(number){//is number then keep last 2 part 	
			//Final purpose
			label = purposePeaces[purposePeaces.length-2].toLowerCase() +" " + purposePeaces[purposePeaces.length-1];
		}else{//if not number keep the last part only 	
			//Final Purpose
			label=purposePeaces[purposePeaces.length-1].toLowerCase();	
		}
		return label;	
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
	
/*	
	//Check string in Numeric or not, Only works for Latin words 
	public static boolean numericChecker(String str)  
	{  
	  try  
	  {  
	    double d = Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
	
	
	*/
	
}
