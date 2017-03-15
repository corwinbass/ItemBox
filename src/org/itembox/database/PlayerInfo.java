package org.itembox.database;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerInfo{
	
	private OfflinePlayer player;
	private Collection<ItemStack> items = new ArrayList<ItemStack>();
	
	public PlayerInfo(OfflinePlayer player){
		this.player = player;
	}
	
	public PlayerInfo(UUID player){
		this.player = Bukkit.getOfflinePlayer(player);
		
	}
	
	public void clearItems(){
		items = new ArrayList<ItemStack>();
	}
	
	public void addItem(ItemStack item){
		items.add(item);
	}
	
	public void removeItem(ItemStack item){
		if(items.contains(item)) items.remove(item);
	}
	
	public String getItemsInString(){
		return serializeItems(items);
	}
	
	public void loadItemsFromString(String s){
		Collection<ItemStack> savedItems = deserializeItems(s);
		items = savedItems;
	}
	
	private static String serializeItems(Collection<ItemStack> collection){
		YamlConfiguration conf = new YamlConfiguration();
		conf.set("inv", collection);
		String ser = conf.saveToString();
		return ser.replaceAll("'", "kv2987shwks").replaceAll("§","hksun2928s7");
	}
	
	private static Collection<ItemStack> deserializeItems(String ser){
		YamlConfiguration conf = new YamlConfiguration();
		Collection<ItemStack> chest = new ArrayList<ItemStack>();
		try {
			conf.loadFromString(ser.replaceAll("kv2987shwks", "'").replaceAll("hksun2928s7", "§"));
			chest = (Collection<ItemStack>) conf.get("inv");
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
			return chest;
		} catch (Exception e){
			e.printStackTrace();
			return chest;
		}
		return chest;
	}

	public Collection<ItemStack> getItems(){
		return items;
	}

	public OfflinePlayer getPlayer() {
		return player;
	}
	
//	public void setPlayer(OfflinePlayer player) {
//		this.player = player;
//	}

}
