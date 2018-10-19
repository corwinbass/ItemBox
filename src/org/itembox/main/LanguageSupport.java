/*
 * Copyright (C) 2015 wysohn.  All rights reserved.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation,  version 3.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.itembox.main;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.itembox.database.FileSerialize;
import org.itembox.database.FileSerializeException;

public class LanguageSupport extends FileSerialize{
	private ItemBox plugin;
	private String lang;
	
	private Queue<Double> doub = new LinkedList<Double>();
	private Queue<Integer> integer = new LinkedList<Integer>();
	private Queue<String> string = new LinkedList<String>();
	private Queue<Boolean> bool = new LinkedList<Boolean>();
	
	private LanguageSupport(File dbPath, String name) throws FileSerializeException{
		super(dbPath, name);
	}
	
	LanguageSupport(Plugin plugin, String lang) throws FileSerializeException{
		this(new File(plugin.getDataFolder(), "lang"), lang+".yml");
		
		Validate.notNull(lang);
		
		this.plugin = (ItemBox) plugin;
		this.lang = lang;
		
		fillIfEmpty();
	}
	
	private void fillIfEmpty(){
		int i = 0;
		for(final Languages lang : Languages.values()){
			String str = lang.toString();
			
			
			if(this.load(str) == null){
				try {
					this.save(str, new ArrayList<String>(){{
						add(lang.getDefault());}});
					Bukkit.getLogger().info("[ItemBox] Added language setting "+str);
				} catch (FileSerializeException e) {
					Bukkit.getLogger().info("[ItemBox] Could not fill empty string "+str);
					e.printStackTrace();
				}
			}
		}
		Bukkit.getLogger().info("[ItemBox]: Loaded " + i + " language statements");
	}
	
	@SuppressWarnings("unchecked")
	public String[] parseStrings(Languages lang){
		List<String> str = new ArrayList<String>();
		str.addAll((List<String>) this.load(lang.toString()));
		
		Validate.notNull(str);
		
		replaceVariables(str);
		
		for(int i=0;i<str.size();i++){
			str.set(i , lang + " is null");
		}
		
		return str.toArray(new String[str.size()]);
	}
	
	@SuppressWarnings("unchecked")
	public String parseFirstString(Languages lang){
		List<String> str = new ArrayList<String>();
		str.addAll((List<String>) this.load(lang.toString()));
		
		Validate.notNull(str);
		if(str.size() == 0){
			return null;
		}
		
		if(str.size() > 1){
			for(int i=1;i<str.size();i++){
				str.remove(i);
			}
		}

		replaceVariables(str);
		
		return str.get(0) == null ? lang + " is null" : str.get(0);
	}
	
	private void replaceVariables(List<String> strings){
		for(int i = 0;i < strings.size(); i++){
			String str = strings.get(i);
			if(str == null) continue;

			str = ChatColor.translateAlternateColorCodes('&', str);

//			if(str.contains("${")){
//				int start = -1;
//				int end = -1;
//				
//				while(!((start = str.indexOf("${")) == -1 || 
//						(end = str.indexOf("}")) == -1)){
//					
//					String leftStr = str.substring(0, start);
//					String rightStr = str.substring(end + 1, str.length());
//					
//					String varName = str.substring(start + 2, end);
//					
//					switch(varName){
//					case "double":
//						str = leftStr+String.valueOf(this.doub.poll())+rightStr;
//						break;
//					case "integer":
//						str = leftStr+String.valueOf(this.integer.poll())+rightStr;
//						break;
//					case "string":
//						str = leftStr+String.valueOf(this.string.poll())+rightStr;
//						break;
//					case "bool":
//						str = leftStr+String.valueOf(this.bool.poll())+rightStr;
//						break;
//					default:
//							str = leftStr+String.valueOf("[UnkownVar]")+rightStr;
//							break;
//					}
//				}	
//			}
			
			strings.set(i, str);
		}
		
//		this.doub.clear();
//		this.integer.clear();
//		this.string.clear();
//		this.bool.clear();
	}
	
	
	public static enum Languages{
		Command_Only_Players("&cOnly players can use this command!"),
		Command_ClaimAll_Success("&c[%amt%] box items claimed!"),
		Command_Send_Success("&aItem sent to %player%!"),
		Command_Send_Receive("&aYou have recieved an item from %player%! Use /itembox open to claim it"),
		Command_Send_Cannot_Send_Yourself("&cYou cannot send items to yourself!"),
		Command_Send_Player_Doesnt_Exist("&cThe specified player doesn't exist!"),
		Command_Send_Hand_Empty("&cYou need to be holding an item in your hand!"),
		Command_Send_Usage("/itembox send [player]"),
		Command_Send_Description("Sends the item in your hand to the specified player. Player name is case sensitive"),
		Command_SendAll_Usage("/itembox sendall"),
		Command_SendAll_Description("Sends the item in your hand to all players"),
		Command_GiveDynamicBox_Unknown_Box("&cThat dynamic box does not exist. Existing boxes:"),
		Command_ClaimAll_Usage("/itembox claimall"),
		Command_ClaimAll_Description("Claims all items from your itembox. If your inventory is full, they will drop on the floor"),
		Command_Open_Usage("/itembox open"),
		Command_Open_Description("Opens your Itembox"),
		Command_GiveDynamicBox_Usage("/itembox givedynamicbox [player] [boxname]"),
		Command_GiveDynamicBox_Description("Gives the specified player the specified dynamic box in config."),
		Command_GiveAllDynamicBox_Usage("/itembox givealldynamicbox [boxname]"),
		Command_GiveAllDynamicBox_Description("Gives the specified dynamic box to every player that joined the server before"),
		Command_AddItemToDynamicBox_Usage("/itembox additemtodynamicbox [boxname] [chance out of 100]"),
		Command_AddItemToDynamicBox_Description("Adds the item in your hand to the dynamic boxc with the specified name with the specified chance of appearing. Amount and attributes not taken into account"),
		Command_AddItemToDynamicBox_Success("&aSuccessfully added item to dynamic box!"),
		Command_Reload_Usage("/itembox reload"),
		Command_Reload_Description("Use to load dynamicboxes from config again"),
		Command_No_Permissions("&cYou don't have the permission to use this command!"),
		GUI_ItemBox_Title("&6ItemBox"),
		GUI_ItemBox_Others("&b%player%'s &6ItemBox"),
		GUI_ItemBox_Durability("&6[durability: %durabiliy%]"),
		GUI_ItemBox_ClickToClaim("&aClick to claim the item"),
		GUI_ItemBox_ClickToDelete("&cClick to delete the item"),
		GUI_ItemBox_EmptySpaceNeeded("&aSpace in your inventory is needed"),
		GUI_ItemBox_InventoryFull("&cInventory is full! Cannot claim!"),
		DynamicBox_Contains("&aThis box contains up to:"),
		DynamicBox_Space_Warning("&cIf your inventory is full, items will be dropped on the floor."),
		Misc_Join_Notif("&aYou have %amt% items in your item box. Use /itembox open to claim them"),
		;
		private String defaultSring;
		
		private Languages(String defaultString){
			this.defaultSring = defaultString;
		}
		
		public String getDefault(){
			return this.defaultSring;
		}
	}
}
