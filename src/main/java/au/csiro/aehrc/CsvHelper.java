/**
 * 
 */
package au.csiro.aehrc;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import au.com.bytecode.opencsv.CSVReader;
import au.csiro.aehrc.utils.CsvLcdc;

/**
 * @author Abbas H Safaie
 * 
 */
public class CsvHelper {

	// CSV resource file lcdcMAp
	private static final String filePath = "src/main/java/resources/lcdcMapping-v1.0.csv";
	// stores all the values in a list
	
	//HashMap to capture CSV file data into Map with value: <itemOid,rest of columns as CsvLcdc> 
	private Map<String, CsvLcdc> hashMap;
	
	
	public CsvHelper() throws IOException{	
		
		hashMap=new HashMap<String,CsvLcdc>();
			extractCsv();
	}
	
	
	private void extractCsv( ) throws IOException {
		
		@SuppressWarnings("resource")
		CSVReader reader = new CSVReader(new FileReader(filePath));
		
			List<String[]> myEntries = reader.readAll();
			Iterator<String[]> it = myEntries.iterator();
			while ( it.hasNext()) {
			    CsvLcdc csv=new CsvLcdc();					
				String[] str = it.next();
				String key = str[0];			
				csv.setItemOid(key);
				csv.setItemLabel(str[1]);
				csv.setTheme(str[2]);
				csv.setDomain(str[3]);
				csv.setExternalLink(str[4]);
				csv.setExternalLabel(str[5]);		
		     	hashMap.put(key, csv);									
	            }
			}

	/*
	 * return snomad uri for passed itemOid
	 */
	public  String getSnomedUri(String itemOid)
			 {
		String uri = "";
		
		  CsvLcdc csv=hashMap.get(itemOid);
		  uri=csv.getExternalLink();
	

		return uri;
	}

	/*
	 * retunr external label 
	 * */
	public String getExternalLable(String itemOid){
		String externalLabel="";
		externalLabel=hashMap.get(itemOid).getExternalLabel();
		
		return externalLabel;
	}
	
	
	/*
	 * return label Input ItemOid
	 */
	public String getLabel(String itemOid) {
		  
		String label = "";
		  CsvLcdc csv=hashMap.get(itemOid);
		  label=csv.getItemLabel();

		return label;
	}

	/*
	 * return Domain input ItemOid
	 */
	public String getDomain(String itemOid) {
		String domain = "";
		  CsvLcdc csv=hashMap.get(itemOid);
		  domain=csv.getDomain();
	
		return domain;
	}

	/*
	 * return Domain input ItemOid
	 */
	public  String getTheme(String itemOid) {
		String theme = "";
	    CsvLcdc csv=hashMap.get(itemOid);
	    theme=csv.getTheme();
		return theme;
	}

}
