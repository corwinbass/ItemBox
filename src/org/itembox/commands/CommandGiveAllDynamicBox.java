package org.itembox.commands;

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

public class CommandGiveAllDynamicBox {
	
	public static void runCommand(CommandSender sender, String[] args){

		if(!sender.hasPermission("itembox.givealldynamicbox")&&!sender.hasPermission("itembox.*")){
			sender.sendMessage(ItemBox.getLang().parseFirstString(Languages.Command_No_Permissions));
			return;
		}
			if(args.length != 2){
				sender.sendMessage(ItemBox.getLang().parseFirstString(Languages.Command_GiveAllDynamicBox_Usage));
				return;
			}
			if(!ItemBox.getDynamicBoxManager().dynamicBoxes.containsKey(args[1].toLowerCase())){
				sender.sendMessage(ItemBox.getLang().parseFirstString(Languages.Command_GiveDynamicBox_Unknown_Box));
				for(String box:ItemBox.getDynamicBoxManager().dynamicBoxes.keySet()){
					sender.sendMessage(ChatColor.RED + " - " + box);
				}
				return;
			}
			ItemStack item = ItemBox.getDynamicBoxManager().getDynamicBoxItem(args[1].toLowerCase());
			int i = 0;
			for(OfflinePlayer op:Bukkit.getOfflinePlayers()){
				i++;
				PlayerInfo info = ItemBox.getInstance().getPlayerDataManager().getOrLoadPlayerInfo(op);
				info.addItem(item);
				if(op.isOnline()){
					Player target = Bukkit.getPlayer(op.getUniqueId());
					target.sendMessage(ItemBox.getLang().parseFirstString(Languages.Command_Send_Receive).replaceAll("%player%", sender.getName()));
				}
			}
			sender.sendMessage(ItemBox.getLang().parseFirstString(Languages.Command_Send_Success).replaceAll("%player%", "" + i));
	}

}
