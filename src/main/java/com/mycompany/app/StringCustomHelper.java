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

	/**
	 * 
	 */
	public StringCustomHelper() {
		// TODO Auto-generated constructor stub
	}

	
	
	//Create Generic comment with content of Key (Map key URI)
	public String Comment(String key){
		
		//Final Purpose
		String purpose="";
		String comment="";
		
		
		String[] keys =key.split("/"); //split the key 
		String[] subject=keys[0].split("_"); 
		String subjectKey=subject[subject.length-1]; //subjectNumber
		
		//Observation purpose 
		String purposes=keys[keys.length-1];
		String[] purposeParts=purposes.split("_");
		String purposeLast=purposeParts[purposeParts.length -1];
		//check if number or not 
		String[] purposePeaces=purposeLast.split("_");
		//check if number or not 
		boolean number=isNumeric(purposePeaces[purposePeaces.length-1]);
		if(number){//is number then keep last 2 part 	
			//Final purpose
			purpose=purposeParts[purposeParts.length-2].toLowerCase() +" " + purposeParts[purposeParts.length-1];
		}else{//if not number keep the last part only 	
			//Final Purpose
			purpose=purposePeaces[purposePeaces.length-1].toLowerCase();	
		}
		
		//Event 
		String[] eventParts=keys[1].split("_");
		String event=eventParts[eventParts.length-1].toLowerCase();
		
		//Final Comment
		comment="This observation is about '"+purpose +"', from Subject '"+subjectKey + "', from Event '"+event+"'.";
		
		return comment;		
	}
	
	
	//Create Label for RDF
	public String lable(String key){
		
		String label="";
		String[] keys =key.split("/"); //split the key 
		
		//Observation purpose 
		String purposes=keys[keys.length-1];
		String[] purposeParts=purposes.split("_");
		String purposeLast=purposeParts[purposeParts.length -1];
		//check if number or not 
		String[] purposePeaces=purposeLast.split("_");
		//check if number or not 
		boolean number=isNumeric(purposePeaces[purposePeaces.length-1]);
		if(number){//is number then keep last 2 part 	
			//Final purpose
			label=purposePeaces[purposePeaces.length-2].toLowerCase() +" " + purposePeaces[purposePeaces.length-1];
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
	//Check string in Numeric or not Only for Latin words 
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
