package org.itembox.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.itembox.database.PlayerInfo;
import org.itembox.main.ItemBox;

public class CommandSend {
	
	public static void runCommand(CommandSender sender, String[] args){

		if(sender instanceof Player){
			Player p = (Player) sender;
			if(args.length != 2){
				p.sendMessage(ChatColor.RED + "Usage: /itembox send [player]");
				return;
			}
			OfflinePlayer op = Bukkit.getOfflinePlayer(args[1]);
			if(args[1].equals(p.getName())){
				p.sendMessage(ChatColor.RED + "You cannot send items to yourself!");
				return;
			}
			if(op == null){
				p.sendMessage(ChatColor.RED + "Specified player, " + args[1] + " doesn't exist. Check letter casing");
				return;
			}
			if(op.getUniqueId().equals(p.getUniqueId())){
				p.sendMessage(ChatColor.RED + "You cannot send items to yourself!");
				return;
			}
			if(p.getItemInHand() == null){
				p.sendMessage(ChatColor.RED + "You need to be holding an item in your hand!");
				return;
			}
			ItemStack item = p.getItemInHand();
			PlayerInfo info = ItemBox.getInstance().getPlayerDataManager().getOrLoadPlayerInfo(op);
			info.addItem(item);
			p.setItemInHand(null);
			p.sendMessage(ChatColor.GREEN + "Item sent!");
			
		}else{
			sender.sendMessage(ChatColor.RED + "Only players may use this command");
		}
	
	}

}
