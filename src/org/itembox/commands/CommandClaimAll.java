package org.itembox.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.itembox.database.PlayerInfo;
import org.itembox.main.ItemBox;
import org.itembox.main.ItemBoxGUIManager;

public class CommandClaimAll {
	
	public static void runCommand(CommandSender sender, String[] args){

		if(sender instanceof Player){
			Player p = (Player) sender;
			PlayerInfo info = ItemBox.getInstance().getPlayerDataManager().getOrLoadPlayerInfo(p);
			int amt = 0;
			if(info.getItems().size() > 0){
				for(ItemStack item:info.getItems()){
					amt++;
					if(p.getInventory().firstEmpty() != -1){
						p.getInventory().addItem(item);
					}else p.getWorld().dropItem(p.getLocation(), item);
				}
				p.updateInventory();
			}
			info.clearItems();
			p.sendMessage(ChatColor.GREEN + "[" + amt + "] box items claimed!");
			
		}else{
			sender.sendMessage(ChatColor.RED + "Only players may use this command");
		}
	
	}

}
