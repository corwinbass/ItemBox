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

public class CommandGiveDynamicBox {
	
	public static void runCommand(CommandSender sender, String[] args){

		if(!sender.hasPermission("itembox.givedynamicbox")&&!sender.hasPermission("itembox.*")){
			sender.sendMessage(ItemBox.getLang().parseFirstString(Languages.Command_No_Permissions));
			return;
		}
			if(args.length != 3){
				sender.sendMessage(ItemBox.getLang().parseFirstString(Languages.Command_GiveDynamicBox_Usage));
				return;
			}
			OfflinePlayer op = Bukkit.getOfflinePlayer(args[1]);
			if(op == null){
				sender.sendMessage(ItemBox.getLang().parseFirstString(Languages.Command_Send_Player_Doesnt_Exist));
				return;
			}
			if(!ItemBox.getDynamicBoxManager().dynamicBoxes.containsKey(args[2].toLowerCase())){
				sender.sendMessage(ItemBox.getLang().parseFirstString(Languages.Command_GiveDynamicBox_Unknown_Box));
				for(String box:ItemBox.getDynamicBoxManager().dynamicBoxes.keySet()){
					sender.sendMessage(ChatColor.RED + " - " + box);
				}
				return;
			}
			ItemStack item = ItemBox.getDynamicBoxManager().getDynamicBoxItem(args[2].toLowerCase());
			PlayerInfo info = ItemBox.getInstance().getPlayerDataManager().getOrLoadPlayerInfo(op);
			info.addItem(item);
			sender.sendMessage(ItemBox.getLang().parseFirstString(Languages.Command_Send_Success).replaceAll("%player%", op.getName()));
			if(op.isOnline()){
				Player target = Bukkit.getPlayer(op.getUniqueId());
				target.sendMessage(ItemBox.getLang().parseFirstString(Languages.Command_Send_Receive).replaceAll("%player%", sender.getName()));
			}
	
	}

}
