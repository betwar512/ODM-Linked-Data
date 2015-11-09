package au.csiro.aehrc;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
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
	public static Resource rangeResourcetype(String range) throws Exception{
		
		
		if(range.isEmpty()){
			throw new Exception("range is empty ,Exception rangeType method");
		}
		
		Resource r=null;

		switch (range) {
		case  "INTEGER":r=XSD.integer;
			break;
		case  "FLOAT":r=XSD.xfloat;
			break;
		case "DATE":r=XSD.date;
			break;
		case "TEXT":r=XSD.xstring;
		break;
		default: r= XSD.xstring;
			break;
		 }
		return r;

	}

	
	/*
	 @Warning Function not tested
	 * 
	 * get same xsd type from range
	 * Input : range String
	 * output: XSDDatatype : XSDDatatype.dataType 
	 * */
	public static XSDDatatype rangeDatatype(String range){
		
	
		XSDDatatype data = null;


		switch (range) {
		case  "INTEGER":data=XSDDatatype.XSDinteger;
			break;
		case  "FLOAT":data=XSDDatatype.XSDfloat;
			break;
		case  "DATE":data=XSDDatatype.XSDdate;
			break;
		case  "TEXT":data=XSDDatatype.XSDstring;
		break;
		default:data=XSDDatatype.XSDstring;
			break;
		 }
		
		return data;

	}

	
}
