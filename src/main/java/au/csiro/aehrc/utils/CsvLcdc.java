/**
 * 
 */
package au.csiro.aehrc.utils;

import javax.management.RuntimeErrorException;

/**
 * This assumes that we provide the user a template containing:<br>
 * ItemOID <br>
 * ItemLabel <br>
 * Theme <br>
 * Domain <br>
 * ExternalId <br>
 * ExternalLabel <br>
 * 
 * @author ler017
 *
 */
public class CsvLcdc {
	
	private String itemOid;
	private String itemLabel;
	private String theme;
	private String domain;
	private String externalLink;
	private String externalLabel;
	
	/**
	 * @return the itemOid
	 */
	public String getItemOid() {
		return itemOid;
	}
	/**
	 * @param itemOid the itemOid to set
	 */
	public void setItemOid(String itemOid) {
		this.itemOid = itemOid;
	}
	/**
	 * @return the itemLabel
	 */
	public String getItemLabel() {
		return itemLabel;
	}
	/**
	 * @param itemLabel the itemLabel to set
	 */
	public void setItemLabel(String itemLabel) {
		this.itemLabel = itemLabel;
	}
	/**
	 * @return the theme
	 */
	public String getTheme() {
		return theme;
	}
	/**
	 * @param theme the theme to set
	 */
	public void setTheme(String theme) {
		this.theme = theme;
	}
	/**
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}
	/**
	 * @param domain the domain to set
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}
	/**
	 * @return the externalLink
	 */
	public String getExternalLink() {
		return externalLink;
	}
	/**
	 * @param externalLink the externalLink to set
	 */
	public void setExternalLink(String externalLink) {
		this.externalLink = externalLink;
	}
	/**
	 * @return the externalLabel
	 */
	public String getExternalLabel() {
		return externalLabel;
	}
	/**
	 * @param externalLabel the externalLabel to set
	 */
	public void setExternalLabel(String externalLabel) {
		this.externalLabel = externalLabel;
	}

}
