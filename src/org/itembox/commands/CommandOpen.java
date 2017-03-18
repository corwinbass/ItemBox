package org.itembox.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.itembox.main.ItemBox;
import org.itembox.main.ItemBoxGUIManager;
import org.itembox.main.LanguageSupport.Languages;

public class CommandOpen {
	
	public static void runCommand(CommandSender sender, String[] args){
		if(!sender.hasPermission("itembox.open")&&!sender.hasPermission("itembox.*")){
			sender.sendMessage(ItemBox.getLang().parseFirstString(Languages.Command_No_Permissions));
			return;
		}
		if(sender instanceof Player){
			ItemBoxGUIManager.getInstance().openItemBox((Player) sender);
		}else{
			sender.sendMessage(ItemBox.getLang().parseFirstString(Languages.Command_Only_Players));
		}
	
	}

}
