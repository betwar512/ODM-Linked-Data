package au.csiro.aehrc;

import java.util.List;

import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionItemData;

public class ItemDetail {

	
	public String subjectKey;
	public String eventOid;
	public String formOid;
	//ItemGroup key 
	public String itemGroupOid;
	//repeatKey in ItemGroup
	public String itemRepeatKey;
	public Boolean repeating;
	public List<ODMcomplexTypeDefinitionItemData> items; 
	//list of Items 
	
	
	/*
	* check if item belong to vital ItemGroup 
	*return type : Boolean 
	*/
	public  Boolean isVital( ){
	final String vital="VITAL";
	String[] comparStrs=itemGroupOid.split("_");
	String comparStr=comparStrs[1];
	//Compare String 
		 if(comparStr.equals(vital)){
			return true;
		}else{
		return false;
		}
	}
	
	 //================================================================================
    // Static Boolean function to categories return information in groups 
   //================================================================================
	
	/*
	* check if item belong to blood ItemGroup 
	*return type : Boolean 
	*/
	public  Boolean isBlood(){		
	final String blood="BLOOD";
	String[] comparStrs=itemGroupOid.split("_");
	String comparStr=comparStrs[1];
	//Compare String 
		if(comparStr.equals(blood)){
			return true;
		}
		return false;
	}
	
	/*
	* check if item belong to Medication  ItemGroup 
	*return type : Boolean 
	*/
	public  Boolean isMedic(){		
	final String medicin="MEDIC";
	String[] comparStrs=itemGroupOid.split("_");
	String comparStr=comparStrs[1];
	//Compare String 
		if(comparStr.equals(medicin)){
			return true;
		}
		return false;
	}
	
	
	//getter and setter 

	public void setFormOid(String formOid) {
		this.formOid = formOid;
	}
	
	
	public Boolean getRepeating() {
		return repeating;
	}


	public void setRepeating(Boolean repeating) {
		this.repeating = repeating;
	}


	public String getSubjectKey() {
		return subjectKey;
	}
	public void setSubjectKey(String subjectKey) {
		this.subjectKey = subjectKey;
	}
	public String getEventOid() {
		return eventOid;
	}
	public void setEventOid(String eventOid) {
		this.eventOid = eventOid;
	}
	
	public String getFormOid(){
		
		return formOid;
	}
	public void setFomrOid(String formOid){
		
		this.formOid=formOid;
	}
	
	public String getItemGroupOid() {
		return itemGroupOid;
	}

	public void setItemGroupOid(String itemGroupOid) {
		this.itemGroupOid = itemGroupOid;
	}

	public String getItemRepeatKey() {
		return itemRepeatKey;
	}

	public void setItemRepeatKey(String itemRepeatKey) {
		this.itemRepeatKey = itemRepeatKey;
	}

	public List<ODMcomplexTypeDefinitionItemData> getItems() {
		return items;
	}

	public void setItems(List<ODMcomplexTypeDefinitionItemData> items) {
		this.items = items;
	}
	
	
}
