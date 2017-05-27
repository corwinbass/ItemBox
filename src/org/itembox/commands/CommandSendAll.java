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

public class CommandSendAll {
	
	public static void runCommand(CommandSender sender, String[] args){

		if(!sender.hasPermission("itembox.sendall")&&!sender.hasPermission("itembox.*")){
			sender.sendMessage(ItemBox.getLang().parseFirstString(Languages.Command_No_Permissions));
			return;
		}
		if(sender instanceof Player){
			Player p = (Player) sender;
			if(args.length != 1){
				p.sendMessage(ChatColor.RED + "Usage: /itembox sendall");
				return;
			}
			ItemStack item = p.getItemInHand();
			p.setItemInHand(null);
			if(p.getItemInHand() == null || p.getItemInHand().getType() == Material.AIR){
				p.sendMessage(ItemBox.getLang().parseFirstString(Languages.Command_Send_Hand_Empty));
				return;
			}
			int i = 0;
			for(OfflinePlayer op:Bukkit.getOfflinePlayers()){
				i++;
				PlayerInfo info = ItemBox.getInstance().getPlayerDataManager().getOrLoadPlayerInfo(op);
				info.addItem(item);
				if(op.isOnline()){
					Player target = Bukkit.getPlayer(op.getUniqueId());
					target.sendMessage(ItemBox.getLang().parseFirstString(Languages.Command_Send_Receive).replaceAll("%player%", p.getName()));
				}
			}
			p.sendMessage(ItemBox.getLang().parseFirstString(Languages.Command_Send_Success).replaceAll("%player%", i + ""));
			
		}else{
			sender.sendMessage(ItemBox.getLang().parseFirstString(Languages.Command_Only_Players));
		}
	
	}

}
