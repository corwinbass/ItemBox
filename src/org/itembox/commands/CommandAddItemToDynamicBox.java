package org.itembox.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.itembox.database.PlayerInfo;
import org.itembox.main.ItemBox;
import org.itembox.main.LanguageSupport.Languages;

public class CommandAddItemToDynamicBox {
	
	public static void runCommand(CommandSender sender, String[] args){
		if(sender instanceof Player){
			Player p = (Player) sender;
			if(!sender.hasPermission("itembox.additemtodynamicbox")&&!sender.hasPermission("itembox.*")){
				sender.sendMessage(ItemBox.getLang().parseFirstString(Languages.Command_No_Permissions));
				return;
			}
				if(args.length != 3){
					sender.sendMessage(ItemBox.getLang().parseFirstString(Languages.Command_AddItemToDynamicBox_Usage));
					return;
				}
				
				if(!ItemBox.getDynamicBoxManager().dynamicBoxes.containsKey(args[1].toLowerCase())){
					sender.sendMessage(ItemBox.getLang().parseFirstString(Languages.Command_GiveDynamicBox_Unknown_Box));
					for(String box:ItemBox.getDynamicBoxManager().dynamicBoxes.keySet()){
						sender.sendMessage(ChatColor.RED + " - " + box);
					}
					return;
				}
				if(p.getItemInHand() == null || p.getItemInHand().getType() == Material.AIR){
					p.sendMessage(ItemBox.getLang().parseFirstString(Languages.Command_Send_Hand_Empty));
					return;
				}
				try{
					int chance = Integer.parseInt(args[2]);
					List<String> items = ItemBox.getInstance().getConfig().getStringList("dynamic-boxes." + args[1].toLowerCase());
					items.add(ItemBox.getDynamicBoxManager().stringFromItem(p.getItemInHand(), chance));
					ItemBox.getInstance().getConfig().set("dynamic-boxes." + args[1].toLowerCase(), items);
					ItemBox.getInstance().saveConfig();
					p.sendMessage(ItemBox.getLang().parseFirstString(Languages.Command_AddItemToDynamicBox_Success));
				}catch(NumberFormatException e){
					sender.sendMessage(ItemBox.getLang().parseFirstString(Languages.Command_AddItemToDynamicBox_Usage));
					return;
				}
				
				
				ItemBox.getInstance().reloadConfig();
				ItemBox.getDynamicBoxManager().loadDynamicBoxes();
		}else{
			sender.sendMessage(ItemBox.getLang().parseFirstString(Languages.Command_Only_Players));
		}
	}

}
