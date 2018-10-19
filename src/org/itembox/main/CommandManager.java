package org.itembox.main;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.itembox.commands.CommandAddItemToDynamicBox;
import org.itembox.commands.CommandClaimAll;
import org.itembox.commands.CommandGiveAllDynamicBox;
import org.itembox.commands.CommandGiveDynamicBox;
import org.itembox.commands.CommandOpen;
import org.itembox.commands.CommandReload;
import org.itembox.commands.CommandSend;
import org.itembox.commands.CommandSendAll;
import org.itembox.database.PlayerInfo;
import org.itembox.main.LanguageSupport.Languages;

public class CommandManager implements TabCompleter{

	
	ItemBox plugin;
	public CommandManager(ItemBox plugin){
		this.plugin = plugin;
	}
	public void onCommand(CommandSender sender, Command command, String label,
			String[] args) {
		
		if(command.getName().equalsIgnoreCase("itembox")){
			if(args.length == 0){

				displayHelp(sender);
				
			}else if(args[0].equalsIgnoreCase("open")){
				CommandOpen.runCommand(sender, args);
			}else if(args[0].equalsIgnoreCase("send")){
				CommandSend.runCommand(sender, args);
			}else if(args[0].equalsIgnoreCase("claimall")){
				CommandClaimAll.runCommand(sender, args);
			}else if(args[0].equalsIgnoreCase("givedynamicbox")){
				CommandGiveDynamicBox.runCommand(sender, args);
			}else if(args[0].equalsIgnoreCase("givealldynamicbox")){
				CommandGiveAllDynamicBox.runCommand(sender, args);
			}else if(args[0].equalsIgnoreCase("sendall")){
				CommandSendAll.runCommand(sender, args);
			}else if(args[0].equalsIgnoreCase("additemtodynamicbox")){
				CommandAddItemToDynamicBox.runCommand(sender, args);
			}else if(args[0].equalsIgnoreCase("reload")){
				CommandReload.runCommand(sender, args);
			}else{
				displayHelp(sender);
			}
		}
		
	}
	
	public void displayHelp(CommandSender sender){

		sender.sendMessage(ChatColor.GOLD + "====ItemBox====");
		if(sender.hasPermission("itembox.open")||sender.hasPermission("itembox.*"))
			sender.sendMessage(ChatColor.YELLOW + ItemBox.getLang().parseFirstString(Languages.Command_Open_Usage) + ChatColor.GOLD + " - " + ItemBox.getLang().parseFirstString(Languages.Command_Open_Description));
		if(sender.hasPermission("itembox.send")||sender.hasPermission("itembox.*"))
			sender.sendMessage(ChatColor.YELLOW + ItemBox.getLang().parseFirstString(Languages.Command_Send_Usage) + ChatColor.GOLD + " - " + ItemBox.getLang().parseFirstString(Languages.Command_Send_Description));
		if(sender.hasPermission("itembox.sendall")||sender.hasPermission("itembox.*"))
			sender.sendMessage(ChatColor.YELLOW + ItemBox.getLang().parseFirstString(Languages.Command_SendAll_Usage) + ChatColor.GOLD + " - " + ItemBox.getLang().parseFirstString(Languages.Command_SendAll_Description));
		if(sender.hasPermission("itembox.claimall")||sender.hasPermission("itembox.*"))
			sender.sendMessage(ChatColor.YELLOW + ItemBox.getLang().parseFirstString(Languages.Command_ClaimAll_Usage) + ChatColor.GOLD + " - " + ItemBox.getLang().parseFirstString(Languages.Command_ClaimAll_Description));
		if(sender.hasPermission("itembox.givedynamicbox")||sender.hasPermission("itembox.*"))
			sender.sendMessage(ChatColor.YELLOW + ItemBox.getLang().parseFirstString(Languages.Command_GiveDynamicBox_Usage) + ChatColor.GOLD + " - " + ItemBox.getLang().parseFirstString(Languages.Command_GiveDynamicBox_Description));
		if(sender.hasPermission("itembox.givealldynamicbox")||sender.hasPermission("itembox.*"))
			sender.sendMessage(ChatColor.YELLOW + ItemBox.getLang().parseFirstString(Languages.Command_GiveAllDynamicBox_Usage) + ChatColor.GOLD + " - " + ItemBox.getLang().parseFirstString(Languages.Command_GiveAllDynamicBox_Description));
		if(sender.hasPermission("itembox.additemtodynamicbox")||sender.hasPermission("itembox.*"))
			sender.sendMessage(ChatColor.YELLOW + ItemBox.getLang().parseFirstString(Languages.Command_AddItemToDynamicBox_Usage) + ChatColor.GOLD + " - " + ItemBox.getLang().parseFirstString(Languages.Command_AddItemToDynamicBox_Description));
		if(sender.hasPermission("itembox.reload")||sender.hasPermission("itembox.*"))
			sender.sendMessage(ChatColor.YELLOW + ItemBox.getLang().parseFirstString(Languages.Command_Reload_Usage) + ChatColor.GOLD + " - " + ItemBox.getLang().parseFirstString(Languages.Command_Reload_Description));
	
	}
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label,
			String[] args) {
		List<String> list = new ArrayList<String>(){{
			add("open");
			add("send");
			add("claimall");
			add("givedynamicbox");
			add("givealldynamicbox");
			add("sendall");
			add("additemtodynamicbox");
			add("reload");
		}};
		return list;
	}
	
}
