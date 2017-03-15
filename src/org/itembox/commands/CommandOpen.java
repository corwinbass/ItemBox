package org.itembox.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.itembox.main.ItemBoxGUIManager;

public class CommandOpen {
	
	public static void runCommand(CommandSender sender, String[] args){

		if(sender instanceof Player){
			ItemBoxGUIManager.getInstance().openItemBox((Player) sender);
		}else{
			sender.sendMessage(ChatColor.RED + "Only players may use this command");
		}
	
	}

}
