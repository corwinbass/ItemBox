package org.itembox.main;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.itembox.database.PlayerInfo;
import org.itembox.main.LanguageSupport.Languages;
import org.itembox.scroller.ScrollerInventory;

public class ItemBoxGUIManager implements Listener {
	
	public static ItemBoxGUIManager instance;
	
	public ItemBoxGUIManager(){
		instance = this;
	}
	
	public static ItemBoxGUIManager getInstance(){
		return instance;
	}
	
	@EventHandler
	public void onBoxClick(InventoryClickEvent event){
		if(event.getInventory().getName() == null) return;
		if(!event.getInventory().getName().equals(ItemBox.getLang().parseFirstString(Languages.GUI_ItemBox_Title))) return;
		event.setCancelled(true);
		if(event.getCurrentItem() == null) return;
		if(event.getCurrentItem().getItemMeta() == null) return;
		if(event.getCurrentItem().getItemMeta().getLore() == null) return;
		if(!event.getCurrentItem().getItemMeta().getLore().contains(ItemBox.getLang().parseFirstString(Languages.GUI_ItemBox_ClickToClaim))) return;
		ItemStack clicked = event.getCurrentItem();
		Player p = (Player) event.getWhoClicked();
		ArrayList<ItemStack> buttons = getItemButtons(p);
		PlayerInfo info = ItemBox.getInstance().getPlayerDataManager().getOrLoadPlayerInfo(p);
		ArrayList<ItemStack> items = new ArrayList<ItemStack>(info.getItems());
		if(!buttons.contains(clicked)){
			p.sendMessage("" + buttons.size());
			return;
		}
		int index = buttons.indexOf(clicked);
		if(p.getInventory().firstEmpty() == -1){
			p.sendMessage(ItemBox.getLang().parseFirstString(Languages.GUI_ItemBox_InventoryFull));
			p.closeInventory();
			return;
		}
		ItemStack item = items.get(index);
		p.getInventory().addItem(item);
		info.removeItem(item);
		openItemBox(p);
	}
	
	public void openItemBox(Player p){
		
		ScrollerInventory inv = new ScrollerInventory(getItemButtons(p), ItemBox.getLang().parseFirstString(Languages.GUI_ItemBox_Title) ,p);
	}
	
	public ArrayList<ItemStack> getItemButtons(Player p){
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		PlayerInfo info = ItemBox.getInstance().getPlayerDataManager().getOrLoadPlayerInfo(p);
		for(ItemStack item:info.getItems()){
			ItemStack button = new ItemStack(Material.CHEST, item.getAmount());
			if(item.getEnchantments().size() > 0){
				button.addUnsafeEnchantments(item.getEnchantments());
			}
			ItemMeta meta = button.getItemMeta();
			List<String> lore = new ArrayList<String>();
			if(item.hasItemMeta() && item.getItemMeta().getDisplayName() != null &&
					!item.getItemMeta().getDisplayName().equals(item.getType().toString())){
				meta.setDisplayName(item.getItemMeta().getDisplayName());
			}else{
				String disp = ChatColor.RESET + "" + ChatColor.AQUA + item.getType().toString().substring(0, 1).toUpperCase() + item.getType().toString().substring(1).toLowerCase();
				if(item.getDurability() != 0){
					disp += ItemBox.getLang().parseFirstString(Languages.GUI_ItemBox_Durability).replaceAll("%durability%", "" + item.getDurability());
				}
				meta.setDisplayName(disp.replaceAll("_", " "));
			}
			
			if(item.hasItemMeta() && item.getItemMeta().getLore() != null){
				lore.addAll(item.getItemMeta().getLore());
			}
			
			lore.add(ItemBox.getLang().parseFirstString(Languages.GUI_ItemBox_ClickToClaim));
			lore.add(ItemBox.getLang().parseFirstString(Languages.GUI_ItemBox_EmptySpaceNeeded));
			meta.setLore(lore);
			
			button.setItemMeta(meta);
			items.add(button);
		}
		
		return items;
	}
	

}
