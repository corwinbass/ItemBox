package org.itembox.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.itembox.commands.CommandClaimAll;
import org.itembox.commands.CommandOpen;
import org.itembox.commands.CommandSend;
import org.itembox.database.PlayerInfo;
import org.itembox.main.LanguageSupport.Languages;

public class CommandManager {

	
	ItemBox plugin;
	public CommandManager(ItemBox plugin){
		this.plugin = plugin;
	}
	public void onCommand(CommandSender sender, Command command, String label,
			String[] args) {
		
		if(command.getName().equalsIgnoreCase("itembox")){
			if(args.length == 0){
				sender.sendMessage(ChatColor.GOLD + "====ItemBox====");
				if(sender.hasPermission("itembox.open")&&sender.hasPermission("itembox.*"))
					sender.sendMessage(ChatColor.YELLOW + ItemBox.getLang().parseFirstString(Languages.Command_Open_Usage) + ChatColor.GOLD + " - " + ItemBox.getLang().parseFirstString(Languages.Command_Open_Description));
				if(sender.hasPermission("itembox.send")&&sender.hasPermission("itembox.*"))
					sender.sendMessage(ChatColor.YELLOW + ItemBox.getLang().parseFirstString(Languages.Command_Send_Usage) + ChatColor.GOLD + " - " + ItemBox.getLang().parseFirstString(Languages.Command_Send_Description));
				if(sender.hasPermission("itembox.claimall")&&sender.hasPermission("itembox.*"))
					sender.sendMessage(ChatColor.YELLOW + ItemBox.getLang().parseFirstString(Languages.Command_ClaimAll_Usage) + ChatColor.GOLD + " - " + ItemBox.getLang().parseFirstString(Languages.Command_ClaimAll_Description));
			}else if(args[0].equalsIgnoreCase("open")){
				CommandOpen.runCommand(sender, args);
			}else if(args[0].equalsIgnoreCase("send")){
				CommandSend.runCommand(sender, args);
			}else if(args[0].equalsIgnoreCase("claimall")){
				CommandClaimAll.runCommand(sender, args);
			}else{
				sender.sendMessage(ChatColor.GOLD + "====ItemBox====");
				if(sender.hasPermission("itembox.open")&&sender.hasPermission("itembox.*"))
					sender.sendMessage(ChatColor.YELLOW + ItemBox.getLang().parseFirstString(Languages.Command_Open_Usage) + ChatColor.GOLD + " - " + ItemBox.getLang().parseFirstString(Languages.Command_Open_Description));
				if(sender.hasPermission("itembox.send")&&sender.hasPermission("itembox.*"))
					sender.sendMessage(ChatColor.YELLOW + ItemBox.getLang().parseFirstString(Languages.Command_Send_Usage) + ChatColor.GOLD + " - " + ItemBox.getLang().parseFirstString(Languages.Command_Send_Description));
				if(sender.hasPermission("itembox.claimall")&&sender.hasPermission("itembox.*"))
					sender.sendMessage(ChatColor.YELLOW + ItemBox.getLang().parseFirstString(Languages.Command_ClaimAll_Usage) + ChatColor.GOLD + " - " + ItemBox.getLang().parseFirstString(Languages.Command_ClaimAll_Description));
			}
		}
		
	}
	
}
