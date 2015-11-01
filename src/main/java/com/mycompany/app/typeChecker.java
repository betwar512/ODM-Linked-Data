package com.mycompany.app;

import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.XSD;

public class typeChecker {

	
	
	/*
	 * Warning Function not tested
	 * 
	 * get same xsd type from range
	 * Input : range String
	 * output: Resource : xsd.dataType 
	 * */
	public static Resource rangeType(String range) throws Exception{
		
		
		if(range.isEmpty()){
			throw new Exception("range is empty ,Exception rangeType method");
		}
		
		Resource r=null;

		switch (range) {
		case  "INTEGER":r=XSD.integer;
			break;
		case  "FLOAT":r=XSD.xfloat;
			break;
		case "DATETIME":r=XSD.dateTime;
			break;		
		default: r= XSD.xstring;
			break;
		 }
		
		return r;

	}

}
