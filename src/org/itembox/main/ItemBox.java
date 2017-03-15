package org.itembox.main;

import java.sql.Connection;
import java.sql.DriverManager;



import java.sql.Statement;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.itembox.database.PlayerDataManager;
import org.itembox.database.PlayerInfo;
import org.itembox.scroller.ScrollerInventoryManager;

public class ItemBox extends JavaPlugin {
	
	public static ItemBox instance;
	private PlayerDataManager playerDataManager;
	private CommandManager cmdExe;
	public void onEnable(){
		getConfig().options().copyDefaults(false);
		saveConfig();
		instance = this;
		playerDataManager = new PlayerDataManager();
		Bukkit.getPluginManager().registerEvents(new ScrollerInventoryManager(), this);
		Bukkit.getPluginManager().registerEvents(new ItemBoxGUIManager(), this);
		
		cmdExe = new CommandManager(this);
	}
	
	public void onDisable(){
		if(!playerDataManager.isSaving) playerDataManager.saveAll();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		cmdExe.onCommand(sender, command, label, args);
		
		return true;
	}
	
	public static ItemBox getInstance(){
		return instance;
	}

	public PlayerDataManager getPlayerDataManager(){
		return playerDataManager;
	}
}
