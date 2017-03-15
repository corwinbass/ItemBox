package org.itembox.scroller;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.Plugin;

public class ScrollerInventoryManager implements Listener{

	//Scroller Inventory by Hex_27
	@EventHandler(ignoreCancelled = true)
	public void onClick(InventoryClickEvent event){
		Player p = (Player) event.getWhoClicked();
		if(ScrollerInventory.users.containsKey(p.getUniqueId())){
			ScrollerInventory inv = ScrollerInventory.users.get(p.getUniqueId());
			if(event.getCurrentItem() != null){
				if(event.getCurrentItem().getItemMeta() != null){
					if(event.getCurrentItem().getItemMeta().getDisplayName() == null) return;
					if(event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + ">>")){
						event.setCancelled(true);
						if(inv.currpage >= inv.pages.size()-1){
							return;
						}else{
							inv.currpage += 1;
							p.openInventory(inv.pages.get(inv.currpage));
						}
					}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "<<")){
						event.setCancelled(true);
						if(inv.currpage > 0){
							inv.currpage -= 1;
							p.openInventory(inv.pages.get(inv.currpage));
						}else{
						}
					}
				}
			}
		}
	}
	

}
