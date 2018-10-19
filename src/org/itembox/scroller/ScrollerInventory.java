package org.itembox.scroller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.itembox.utils.XMaterial;

public class ScrollerInventory{
	
	//Scroller Inventory by Hex_27
	
	
	public ArrayList<Inventory> pages = new ArrayList<Inventory>();
	public UUID id;
	public int currpage = 0;
	public static HashMap<UUID, ScrollerInventory> users = new HashMap<UUID, ScrollerInventory>();
	
	public ScrollerInventory(Collection<ItemStack> itemCollection, String name, Player p){
		this.id = UUID.randomUUID();
		Inventory page = getBlankPage(name);
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		for(ItemStack item:itemCollection) items.add(item);
		int index = 0;
		for(int i = 0;i < items.size(); i++){
			if(page.firstEmpty() == 46){
				pages.add(page);
				index = 0;
				page = getBlankPage(name);
				page.setItem(index, items.get(i));
			}else{
				page.setItem(index, items.get(i));
			}
			index++;
		}
		pages.add(page);
		p.openInventory(pages.get(currpage));
		users.put(p.getUniqueId(), this);
	}
	

	
	
	
	private Inventory getBlankPage(String name){
		Inventory page = Bukkit.createInventory(null, 54, name);
		
		ItemStack nextpage =  XMaterial.LIME_STAINED_GLASS_PANE.parseItem();
		ItemMeta meta = nextpage.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD + ">>");
		nextpage.setItemMeta(meta);
		
		ItemStack prevpage = XMaterial.MAGENTA_STAINED_GLASS_PANE.parseItem();
		meta = prevpage.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD + "<<");
		prevpage.setItemMeta(meta);
		
		
		page.setItem(53, nextpage);
		page.setItem(45, prevpage);
		return page;
	}
}
