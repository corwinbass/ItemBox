package org.itembox.commands;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.itembox.database.PlayerInfo;
import org.itembox.main.ChanceItemWrapper;
import org.itembox.main.DynamicBoxManager;
import org.itembox.main.ItemBox;
import org.itembox.main.ItemBoxGUIManager;
import org.itembox.main.LanguageSupport.Languages;

public class CommandClaimAll {
	
	public static void runCommand(CommandSender sender, String[] args){
		if(!sender.hasPermission("itembox.claimall")&&!sender.hasPermission("itembox.*")){
			sender.sendMessage(ItemBox.getLang().parseFirstString(Languages.Command_No_Permissions));
			return;
		}
		if(sender instanceof Player){
			Player p = (Player) sender;
			PlayerInfo info = ItemBox.getInstance().getPlayerDataManager().getOrLoadPlayerInfo(p);
			int amt = 0;
			if(info.getItems().size() > 0){
				for(ItemStack item:info.getItems()){
					amt++;
					
					DynamicBoxManager manager = ItemBox.getDynamicBoxManager();
					if(manager.isItemDynamicBox(item)){
						String name = manager.getBoxNameFromItemStack(item);
						if(manager.dynamicBoxes.containsKey(name)){
							for(ChanceItemWrapper reward:manager.dynamicBoxes.get(name)){
								if(randInt(0,100)<=reward.getChance()){
									if(p.getInventory().firstEmpty() != -1){
										p.getInventory().addItem(reward.getItem());
									}else{
										p.getWorld().dropItem(p.getLocation(), reward.getItem());
									}
								}
							}
						}
						continue;
					}
					
					
					if(p.getInventory().firstEmpty() != -1){
						p.getInventory().addItem(item);
					}else{
						p.getWorld().dropItem(p.getLocation(), item);
					}
				}
				p.updateInventory();
			}
			info.clearItems();
			p.sendMessage(ChatColor.GREEN + ItemBox.getLang().parseFirstString(Languages.Command_ClaimAll_Success).replaceAll("%amt%", "" + amt));
			
		}else{
			sender.sendMessage(ItemBox.getLang().parseFirstString(Languages.Command_Only_Players));
		}
	
	}
	
	public static int randInt(int min, int max) {

	    Random rand = new Random();

	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	

}
