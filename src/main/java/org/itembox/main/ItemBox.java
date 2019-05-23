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
import org.itembox.database.FileSerializeException;
import org.itembox.database.PlayerDataManager;
import org.itembox.database.PlayerInfo;
import org.itembox.scroller.ScrollerInventoryManager;

public class ItemBox extends JavaPlugin {
	
	public static ItemBox instance;
	public static DynamicBoxManager dynamicBoxManager;
	private PlayerDataManager playerDataManager;
	public static LanguageSupport languageManager;
	private CommandManager cmdExe;
	private String lang = "eng";
	public void onEnable(){
		getConfig().options().copyDefaults(false);
		saveConfig();

		if(getConfig().isSet("lang")){
			lang = getConfig().getString("lang");
		}else{
			getConfig().set("lang", "eng");
			saveDefaultConfig();
		}
		dynamicBoxManager = new DynamicBoxManager(this);
		try {
			languageManager = new LanguageSupport(this, lang);
		} catch (FileSerializeException e1) {
			Bukkit.getLogger().info("Could not initiate LanguageSupport. Something went wrong?");
			Bukkit.getLogger().info("Plugin is going to be disabled.");
			this.setEnabled(false);
		}
		
		instance = this;
		playerDataManager = new PlayerDataManager();
		Bukkit.getPluginManager().registerEvents(new ScrollerInventoryManager(), this);
		Bukkit.getPluginManager().registerEvents(new ItemBoxGUIManager(), this);
		Bukkit.getPluginManager().registerEvents(new GeneralListener(), this);
		
		cmdExe = new CommandManager(this);
		getCommand("itembox").setTabCompleter(cmdExe);
	}
	
	public void onDisable(){
		if(!playerDataManager.isSaving) playerDataManager.saveAll();
		playerDataManager.loadedPlayers.clear();
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

	public static DynamicBoxManager getDynamicBoxManager(){
		return dynamicBoxManager;
	}
	
	public PlayerDataManager getPlayerDataManager(){
		return playerDataManager;
	}
	
	public static LanguageSupport getLang(){
		return languageManager;
	}
}
