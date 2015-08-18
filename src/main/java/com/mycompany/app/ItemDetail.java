package com.mycompany.app;

import java.util.List;

import org.cdisc.ns.odm.v1.ODMcomplexTypeDefinitionItemData;

public class ItemDetail {

	
	//ItemGroup key 
	public String itemGroupOid;
	
	//repeatKey in ItemGroup
	public String itemRepeatKey;
	
	//list of Items 
	public List<ODMcomplexTypeDefinitionItemData> items;  

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
