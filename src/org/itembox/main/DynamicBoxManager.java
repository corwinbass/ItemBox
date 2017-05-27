package org.itembox.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.itembox.main.LanguageSupport.Languages;

public class DynamicBoxManager {
	
	private ItemBox plugin;
	
	public DynamicBoxManager(ItemBox plugin){
		this.plugin = plugin;
		loadDynamicBoxes();
	}
	
	public HashMap<String, ArrayList<ChanceItemWrapper>> dynamicBoxes = new HashMap<String, ArrayList<ChanceItemWrapper>>();
	
	public void loadDynamicBoxes(){
		dynamicBoxes.clear();
		ArrayList<String> stringlist = new ArrayList<String>();
		if(!plugin.getConfig().isSet("dynamic-boxes")){
			stringlist = new ArrayList<String>();
			stringlist.add("IRON_CHESTPLATE,100");
			stringlist.add("IRON_SWORD,15");
			plugin.getConfig().set("dynamic-boxes.default", stringlist);
			plugin.saveConfig();
		}
		
		for(String box:plugin.getConfig().getConfigurationSection("dynamic-boxes").getKeys(false)){
			dynamicBoxes.put(box.toLowerCase(),loadDynamicBox(box));
			Bukkit.getLogger().info("[ItemBox] Loaded dynamic box [" + box + "]");
		}
		
	}
	
	public ArrayList<ChanceItemWrapper> loadDynamicBox(String box){

		ArrayList<ChanceItemWrapper> items = new ArrayList<ChanceItemWrapper>();
		List<String> stringlist = plugin.getConfig().getStringList("dynamic-boxes." + box);
		
		for(String item:stringlist){
			String[] splitSpace = item.split(" ");
			String[] splitComma = splitSpace[0].split(",");
			if(splitComma.length != 2){
				Bukkit.getLogger().severe("[ItemBox] The equipment, " + item + " is in the wrong format! Please check the main page for formats");
				continue;
			}
			
			String smaterial = splitComma[0];
			String schance = splitComma[1];
			int chance = 0;
			int data = 0;
			Material mat = null;
			String[] splitmat = smaterial.split(":");
			if(splitmat.length == 2){
				try{
					data = Integer.parseInt(splitmat[1]);
				}catch(NumberFormatException e){
					Bukkit.getLogger().severe("[ItemBox] The equipment, " + item + " is in the wrong format! Please check the main page for formats");
					continue;
				}
				mat = Material.getMaterial(splitmat[0]);
				
			}else if(splitmat.length == 1){
				mat = Material.getMaterial(splitmat[0]);
			}
			
			if(mat == null){
				Bukkit.getLogger().severe("[ItemBox] The material, " + mat + " does not exist. Please check main page for list of material names");
				continue;
			}
			
			try{
				chance = Integer.parseInt(schance);
			}catch(NumberFormatException e){
				Bukkit.getLogger().severe("[ItemBox] The equipment, " + item + " is in the wrong format! Please check the main page for formats");
				continue;
			}
			
			ItemStack parsedItem = new ItemStack(mat, 1, (byte) data);
			if(splitSpace.length > 1){
				for(String s:splitSpace){
					if(s.equals(splitSpace[0])){
						continue;
					}
					String[] split = s.split(":");
					if(split.length != 2){
						Bukkit.getLogger().severe("[ItemBox] The equipment, " + item + "'s enchantment, " + s + " is in the wrong format! Please check the main page for formats");
						continue;
					}
					
					if(split[0].equalsIgnoreCase("name")){
						ItemMeta meta = parsedItem.getItemMeta();
						meta.setDisplayName(colorise(split[1].replaceAll("_", " ")));
						parsedItem.setItemMeta(meta);
						continue;
					}
					if(split[0].equalsIgnoreCase("lore")){
						ItemMeta meta = parsedItem.getItemMeta();
						List<String> lore = new ArrayList<String>();
						for(String lorestr:colorise(split[1].replaceAll("_", " ")).split("<newline>")) lore.add(lorestr);
						meta.setLore(lore);
						parsedItem.setItemMeta(meta);
						continue;
					}
					
					boolean added = false;
					for(Enchantment e:Enchantment.values()){
						if(e.getName().equalsIgnoreCase(split[0])){
							try{
								added = true;
								parsedItem.addUnsafeEnchantment(e, Integer.parseInt(split[1]));
							}catch(NumberFormatException ex){
								Bukkit.getLogger().severe("[ItemBox] The equipment, " + item + "'s enchantment, " + s + " is in the wrong format! Please check the main page for formats");
								
							}
						}
					}
					if(!added)Bukkit.getLogger().severe("[ItemBox] No such enchantment, " + split[0]);
				}
			}
			items.add(new ChanceItemWrapper(parsedItem,chance));
		}
		return items;
	}
	
	public String stringFromItem(ItemStack item, int chance){
		String str = item.getType().toString();
		if(item.getDurability() != 0){
			str+= ":" + item.getDurability();
		}
		str += "," + chance;
		if(item.getItemMeta() != null){
			if(item.getItemMeta().getDisplayName() != null){
				str += " name:" + item.getItemMeta().getDisplayName().replaceAll("" + ChatColor.COLOR_CHAR, "&");;
			}
			if(item.getItemMeta().getLore() != null){
				str += " lore:";
				for(int i = 0; i < item.getItemMeta().getLore().size(); i++){
					String lore = item.getItemMeta().getLore().get(i);
					str+= lore.replaceAll(ChatColor.COLOR_CHAR + "", "&");;
					if(i < item.getItemMeta().getLore().size()-1) str+="<newline>";
				}
			}
		}
		if(item.getEnchantments().size() > 0){
			for(Enchantment e:item.getEnchantments().keySet()){
				str+= " " + e.getName() + ":" + item.getEnchantments().get(e);
			}
				
		}
		return str;
		
	}
	
	public String colorise(String string){
		return ChatColor.translateAlternateColorCodes('&', string);
	}
	
	public final String boxKey = ChatColor.RED + "" + ChatColor.GREEN + "" + ChatColor.AQUA + "" + ChatColor.DARK_RED + "" + ChatColor.GOLD + ChatColor.RED + "" + ChatColor.GREEN + "" + ChatColor.AQUA + "" + ChatColor.DARK_RED + "" + ChatColor.GOLD + ChatColor.RED + "" + ChatColor.GREEN + "" + ChatColor.AQUA + "" + ChatColor.DARK_RED + "" + ChatColor.GOLD; 
	
	public ItemStack getDynamicBoxItem(String boxName){
		ArrayList<ChanceItemWrapper> items = dynamicBoxes.get(boxName);
		if(items == null) return null;
		ItemStack enderchest = new ItemStack(Material.ENDER_CHEST);
		ItemMeta meta = enderchest.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + boxName.replaceAll("_", " "));
		List<String> lore = new ArrayList<String>();
		HashMap<Material,Integer> map = new HashMap<Material,Integer>();
		lore.add(ItemBox.getLang().parseFirstString(Languages.DynamicBox_Contains));
		
		for(ChanceItemWrapper item:items){
			if(map.containsKey(item.getItem().getType())){
				map.put(item.getItem().getType(), map.get(item.getItem().getType()) + 1);
				
			}else{
				map.put(item.getItem().getType(), 1);
			}
		}
		
		for(Material mat:map.keySet()){
			lore.add(ChatColor.AQUA + mat.toString().toLowerCase() + ChatColor.GREEN + " x" + map.get(mat));
		}
		lore.add(boxKey);
		lore.add(UUID.randomUUID().toString());
		meta.setLore(lore);
		enderchest.setItemMeta(meta);
		
		
		return enderchest;
	}
	
	public boolean isItemDynamicBox(ItemStack box){
		if(box.getItemMeta() == null) return false;
		if(box.getItemMeta().getLore() == null) return false;
		if(box.getItemMeta().getLore().contains(boxKey)) return true;
		return false;
	}
	
	public String getBoxNameFromItemStack(ItemStack box){
		if(box.getItemMeta() != null){
			if(box.getItemMeta().getDisplayName() != null){
				String boxName = ChatColor.stripColor(box.getItemMeta().getDisplayName().replaceAll(" ","_"));
				return boxName;
			}	
		}
		
		return "";
	}

}
