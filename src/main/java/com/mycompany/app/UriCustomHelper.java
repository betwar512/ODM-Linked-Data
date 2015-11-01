package com.mycompany.app;


public class UriCustomHelper {

	
	private static final String cardioBase="http://aehrc-ci.it.csiro.au/cardio/lcdc/clinical/";	
	
	private static final String obsBase="http://aehrc-ci.it.csiro.au/dataset/cardio/lcdc/20150713/theme/";
	
	private static final String metaBase="http://aehrc-ci.it.csiro.au/cardio/lcdc/id/variable-def";
	

	private static final String sliceBase="http://aehrc-ci.it.csiro.au/dataset/cardio/lcdc/20150713/";
	
	private static final String themeBase="http://purl.org/sstats/lcdc/id/theme/";
	
	public static String getThemebase() {
		return themeBase;
	}

	private static final String purl="http://purl.org/sstats/lcdc/id/";
	
	
	
	
	public static String getMetabase() {
		return metaBase;
	}
	
	
	/*
	 * input itemOid 
	 * output: variable uri for passed theme 
	 * 
	 * */
	public static final String generateVariableDef(String itemOid){
		
		String uri=metaBase+"#"+ itemOid;
		
		return uri;
	}
	
	
	/*
	 * input Theme 
	 * output: cardio uri for passed theme 
	 * 
	 * */
	public static String generateCardioUri(String theme){
		
		String uri=UriCustomHelper.cardioBase+theme+"/def/cardio-"+theme+"#";

		return uri;
		
	}
	
	
	/*
	 * Generate observation Uri
	*input: theme,phase and subject Key
	*output: String ObservationUri 
	*/
	public static final String generateObservationUri(String theme,String phase,String subject){	
		final String uri=obsBase+theme+"/phase/"+phase+"/subject/"+subject; 
	return uri;
	}
	
	
	//generate phase Purl Uri
	public static final String generatePhase(String eventOid){
		
		String phase=purl+"phase/"+eventOid;
 	
		return phase;
		
	}
	
	/*
	 * Slice dc uri 
	 * */
	public static String csSliceUri(String subject){
		
		
	return	sliceBase+"cs-slice"+"/subject/"+subject;
		
		
	}
	/*
	 * slice : domain slice 
	 * */
	public static String dsSliceUri(String theme){
		return sliceBase+"ds-slice"+"/theme/"+theme;
	}
	
	/*
	 * 
	 * */
	public static String tcSliceUri(String phase){
		return sliceBase+"tc-slice"+"/phase/"+phase;
	}
	
	//generate Subject Purl Uri
	public static final String generateSubject(String subjectKey){
			
    String	subjectUri=purl+"subject/"+subjectKey;	
		return subjectUri;
		
	}
	
	//generate Theme Purl Uri 
	public static final String generateTheme(String theme){
			
    String	themeUri=purl+"theme/"+theme;	
		return themeUri;
		
	}
	
	//add key to uri 
	public static final String addKey(String uri,String key){
		
		return uri+"/key/"+key;
		
	}
	

}
