package com.mycompany.app;


public class UriCustomHelper {

	
	private static final String cardioBase="http://aehrc-ci.it.csiro.au/cardio/lcdc/clinical/";	
	
	private static final String obsBase="http://aehrc-ci.it.csiro.au/dataset/cardio/lcdc/20150713/theme/";
	
	public static final String metaBase="http://aehrc-ci.it.csiro.au/cardio/lcdc/id/variable-def";
	
	public static final String sliceBase="http://aehrc-ci.it.csiro.au/dataset/cardio/lcdc/20150713/";
	
	public static final String themeBase="http://purl.org/sstats/lcdc/id/theme/";
	
	public static final String purl="http://purl.org/sstats/lcdc/id/";
	
	
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
	

}
