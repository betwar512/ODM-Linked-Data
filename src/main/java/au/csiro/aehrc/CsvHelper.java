/**
 * 
 */
package au.csiro.aehrc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import au.csiro.aehrc.utils.CsvLcdc;

/**
 * @author Abbas H Safaie
 * 
 */
public class CsvHelper {

	// CSV resource file lcdcMAp
	private static final String filePath = "src/main/java/resources/lcdcMapping-v1.0.csv";

	// TODO: change this helper class so that it reads the CSV file once and
	// stores all the values in a list
	
	public List<CsvLcdc> extractCsv(String filename) {
		
		CsvToBean<CsvLcdc> csvToBean = new CsvToBean<CsvLcdc>();
		
		Map<String, String> columnMapping = new HashMap<String, String>();
		columnMapping.put("ItemOID", "itemOid");
		columnMapping.put("ItemLabel", "itemLabel");
		columnMapping.put("Theme", "theme");
		columnMapping.put("Domain", "domain");
		columnMapping.put("ExternalId", "externalLink");
		columnMapping.put("ExternalLabel", "externalLabel");

		HeaderColumnNameTranslateMappingStrategy<CsvLcdc> strategy = new HeaderColumnNameTranslateMappingStrategy<CsvLcdc>();
		strategy.setType(CsvLcdc.class);
		strategy.setColumnMapping(columnMapping);

		List<CsvLcdc> results = null;

		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(filename));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		results = csvToBean.parse(strategy, reader);
		
		return results;
		
	}

	/*
	 * return snomad uri for passed label
	 */
	public static String getSnomedUri(String itemOid)
			throws FileNotFoundException {
		String uri = "";

		List<String[]> myEntries;
		try {

			@SuppressWarnings("resource")
			CSVReader reader = new CSVReader(new FileReader(filePath));
			myEntries = reader.readAll();

			Iterator<String[]> it = myEntries.iterator();

			Boolean exit = false; // find uri exit

			while (!exit && it.hasNext()) {

				String[] str = it.next();
				String s = str[0];
				if (s.equals(itemOid)) {
					uri = str[3];
					exit = true;
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return uri;
	}

	/*
	 * return label Input ItemOid
	 */
	public static String getLabel(String itemOid) throws FileNotFoundException {
		String label = "";

		List<String[]> myEntries;
		try {

			@SuppressWarnings("resource")
			CSVReader reader = new CSVReader(new FileReader(filePath));
			myEntries = reader.readAll();

			Iterator<String[]> it = myEntries.iterator();

			Boolean exit = false; // find uri exit

			while (!exit && it.hasNext()) {

				String[] str = it.next();
				String s = str[0];
				if (s.equals(itemOid)) {
					label = str[4];
					exit = true;
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return label;
	}

	/*
	 * return Domain input ItemOid
	 */
	public static String getDomain(String itemOid) throws FileNotFoundException {
		String uri = "";

		List<String[]> myEntries;
		try {

			@SuppressWarnings("resource")
			CSVReader reader = new CSVReader(new FileReader(filePath));
			myEntries = reader.readAll();
			Iterator<String[]> it = myEntries.iterator();

			Boolean exit = false; // find uri exit

			while (!exit && it.hasNext()) {
				String[] str = it.next();
				String s = str[0];
				if (s.equals(itemOid)) {
					uri = str[3];
					exit = true;
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return uri;
	}

	/*
	 * return Domain input ItemOid
	 */
	public static String getTheme(String itemOid) throws FileNotFoundException {
		String uri = "";

		List<String[]> myEntries;
		try {

			@SuppressWarnings("resource")
			CSVReader reader = new CSVReader(new FileReader(filePath));
			myEntries = reader.readAll();
			Iterator<String[]> it = myEntries.iterator();

			Boolean exit = false; // find uri exit

			while (!exit && it.hasNext()) {
				String[] str = it.next();
				// TODO : change it to proper column
				String s = str[0];
				if (s.equals(itemOid)) {
					uri = str[2];
					exit = true;
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return uri;
	}

}
