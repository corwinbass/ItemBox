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

public class CommandSend {
	
	public static void runCommand(CommandSender sender, String[] args){

		if(!sender.hasPermission("itembox.send")&&!sender.hasPermission("itembox.*")){
			sender.sendMessage(ItemBox.getLang().parseFirstString(Languages.Command_No_Permissions));
			return;
		}
		if(sender instanceof Player){
			Player p = (Player) sender;
			if(args.length != 2){
				p.sendMessage(ChatColor.RED + "Usage: /itembox send [player]");
				return;
			}
			OfflinePlayer op = Bukkit.getOfflinePlayer(args[1]);
			if(args[1].equals(p.getName())){
				p.sendMessage(ItemBox.getLang().parseFirstString(Languages.Command_Send_Cannot_Send_Yourself));
				return;
			}
			if(op == null){
				p.sendMessage(ItemBox.getLang().parseFirstString(Languages.Command_Send_Player_Doesnt_Exist));
				return;
			}
			if(op.getUniqueId().equals(p.getUniqueId())){
				p.sendMessage(ItemBox.getLang().parseFirstString(Languages.Command_Send_Cannot_Send_Yourself));
				return;
			}
			if(p.getItemInHand() == null || p.getItemInHand().getType() == Material.AIR){
				p.sendMessage(ItemBox.getLang().parseFirstString(Languages.Command_Send_Hand_Empty));
				return;
			}
			ItemStack item = p.getItemInHand();
			PlayerInfo info = ItemBox.getInstance().getPlayerDataManager().getOrLoadPlayerInfo(op);
			info.addItem(item);
			p.setItemInHand(null);
			p.sendMessage(ItemBox.getLang().parseFirstString(Languages.Command_Send_Success).replaceAll("%player%", op.getName()));
			if(op.isOnline()){
				Player target = Bukkit.getPlayer(op.getUniqueId());
				target.sendMessage(ItemBox.getLang().parseFirstString(Languages.Command_Send_Receive).replaceAll("%player%", p.getName()));
			}
		}else{
			sender.sendMessage(ItemBox.getLang().parseFirstString(Languages.Command_Only_Players));
		}
	
	}

}
