package org.itembox.main;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.itembox.database.PlayerInfo;
import org.itembox.main.LanguageSupport.Languages;

public class GeneralListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		PlayerInfo info = ItemBox.getInstance().getPlayerDataManager().getOrLoadPlayerInfo(event.getPlayer());
		if(info.getItems().size() > 0){
			event.getPlayer().sendMessage(ItemBox.getLang().parseFirstString(Languages.Misc_Join_Notif));
		}
	}
}
