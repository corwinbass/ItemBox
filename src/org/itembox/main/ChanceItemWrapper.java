package org.itembox.main;

import org.bukkit.inventory.ItemStack;

public class ChanceItemWrapper {
	
	public ItemStack getItem() {
		return item;
	}

	public int getChance() {
		return chance;
	}

	private ItemStack item;
	private int chance;
	
	public ChanceItemWrapper(ItemStack item, int chance){
		this.item = item;
		this.chance = chance;
	}
	
	

}
