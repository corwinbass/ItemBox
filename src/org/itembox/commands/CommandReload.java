package org.itembox.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.itembox.main.ItemBox;
import org.itembox.main.ItemBoxGUIManager;
import org.itembox.main.LanguageSupport.Languages;

public class CommandReload {
	
	public static void runCommand(CommandSender sender, String[] args){
		if(!sender.hasPermission("itembox.reload")&&!sender.hasPermission("itembox.*")){
			sender.sendMessage(ItemBox.getLang().parseFirstString(Languages.Command_No_Permissions));
			return;
		}
		try{
			ItemBox.getInstance().reloadConfig();
			ItemBox.getDynamicBoxManager().loadDynamicBoxes();
			sender.sendMessage(ChatColor.GREEN + "reloaded.");
		}catch(Exception e){
			e.printStackTrace();
			sender.sendMessage(ChatColor.RED + "Config caught an error! Unable to reload.");
		}
	}

}
