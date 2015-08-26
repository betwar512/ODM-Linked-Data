package com.mycompany.app;

public class UriCustomHelper {

	
	//Check for Uri rdfDefinition type 
		public static String rdfDefinition(String itemGroupOid){
			String vitalUri="http://aehrc-ci.it.csiro.au/cardio/lcdc/vitalsigns/def/cardio-vitalsigns";
			
		final String blood="BLOOD";
		final String vital="VITAL";
		final String medic="MEDIC";
		
		String[] comparStrs=itemGroupOid.split("_");
		
		String comparStr=comparStrs[1];
		
		//Compare String 
			if(comparStr.equals(blood)){
			
			}else if(comparStr.equals(vital)){
				return vitalUri;
			}else if(comparStr.equals(medic)){
		
			}
			return "";
		}
		

	//Check for Uri dataset type 
	public static String datasetType(String str){
		String returnStr="";
		
	final String blood="BLOOD";
	final String vital="VITAL";
	final String medic="MEDIC";
	
	String[] comparStrs=str.split("_");
	
	String comparStr=comparStrs[1];
	
	//Compare String 
		if(comparStr.equals(blood)){
			returnStr=blood;
		}else if(comparStr.equals(vital)){
			returnStr="vitalsigns";
		}else if(comparStr.equals(medic)){
			returnStr=medic;
		}
		return returnStr.toLowerCase();
	}
	
	
	
	//Generate Uri for Dataset
	public static String uriDataset(ItemDetail item){
		
		String uriComplete;
		String baseUri="http://aehrc-ci.it.csiro.au/dataset/";		
		String version="20150713";
		String dataset=datasetType(item.itemGroupOid);
		String uri=baseUri+dataset+"/lcdc/"+version+"/subject/"+ item.subjectKey+"/phase/"+
		item.eventOid+"/form/"+item.formOid+"/itemGroup/"+item.itemGroupOid;
		//Key optional
		if(item.itemGroupOid.length()>0)
		 uriComplete=uri+"/Key/"+item.itemRepeatKey+"/variable/";
		else
			uriComplete=uri+"/variable/";
		return uriComplete;
		
	}
	
	
	//Generate Uri for Slice
	public static String uriSlice(ItemDetail item){
		String uriComplete;
		String baseUri="http://aehrc-ci.it.csiro.au/slice/";		
		String version="20150713";
	
		String uri=baseUri+item.subjectKey+"/lcdc/"+version+"/subject/"+ item.subjectKey+"/phase/"+
		item.eventOid+"/form/"+item.formOid+"/itemGroup/"+item.itemGroupOid+
		"/RepeatKey/"+item.itemRepeatKey+"/variable/";
		//Key optional
				if(item.itemGroupOid.length()>0)
				 uriComplete=uri+"/Key/"+item.itemRepeatKey+"/variable/";
				else
					uriComplete=uri+"/variable/";
				
		return uriComplete;
		
	}
	
	
	//Uri for metadata 
	public static String uriMetaData(String itemGroupOid){
		
		String uriComplete="";
		String dataset=datasetType(itemGroupOid);
		
		if(dataset.equals("vitalSigns"))
		uriComplete="http://aehrc-ci.it.csiro.au/cardio/lcdc/clinical/vitalsigns/def/cardio-vitalsigns#";
		if(dataset.equals("blood"))
			uriComplete="http://aehrc-ci.it.csiro.au/bloodmesurment/lcdc/clinical/bloodmesurment/def/blood-bloodtest#";
		if(dataset.equals("medic"))
			uriComplete="http://aehrc-ci.it.csiro.au/medication/lcdc/clinical/medication/def/mediction-prescription#";

		return uriComplete;

	}
	
}
