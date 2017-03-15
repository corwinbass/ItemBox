package org.itembox.database;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.itembox.main.ItemBox;


public class PlayerDataManager{
	
	public boolean isSaving = false;
	public static HashMap<UUID, PlayerInfo> loadedPlayers = new HashMap<UUID, PlayerInfo>();
	public PlayerDataManager(){
		try {
			  Connection c = null;
			  Statement stmt = null;
		      Class.forName("org.sqlite.JDBC");
		      c = DriverManager.getConnection("jdbc:sqlite:plugins\\ItemBox\\players.db");
		      stmt = c.createStatement();
		      String sql = "CREATE TABLE IF NOT EXISTS PLAYERS " +
		                   "(UUID STRING PRIMARY KEY     NOT NULL," +
		                   " ITEMS           STRING     NOT NULL); "; 
		      stmt.executeUpdate(sql);
		      stmt.close();
		      c.close();
		} catch ( Exception e ) {
		    e.printStackTrace();	
			//Bukkit.getLogger().severe(e.getClass().getName() + ":" + e.getMessage());
		}
		//new Thread(new AutoSaveTask()).start();
	}
	
	public PlayerInfo getOrLoadPlayerInfo(OfflinePlayer p){
		if(loadedPlayers.containsKey(p.getUniqueId())){
			return loadedPlayers.get(p.getUniqueId());
		}
		PlayerInfo info = load(p.getUniqueId());
		if(info != null){
			loadedPlayers.put(p.getUniqueId(), info);
			return info;
		}
		return createPlayerInformation(p);
	}
	
//	private class AutoSaveTask implements Runnable{
//
//		@Override
//		public void run() {
//			while(ItemBox.getInstance().isEnabled()){
//				try {
//					Thread.sleep(60 * 1000L);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				
//				saveAll();
//			}
//		}
//		
//		
//	}
	
	public void saveAll(){
		isSaving = true;
		for(PlayerInfo info:loadedPlayers.values()){
			save(info);
		}
		isSaving = false;
	}
	
	
	
	public PlayerInfo load(UUID uuid){
		
		
		Connection c = null;
	    Statement stmt = null;
	    PlayerInfo info = new PlayerInfo(uuid);
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:plugins\\ItemBox\\players.db");
	      c.setAutoCommit(false);

	      stmt = c.createStatement();
	      ResultSet rs = stmt.executeQuery( "SELECT * FROM PLAYERS;" );
	      while ( rs.next() ) {
	    	 if(rs.getString("UUID").equals(uuid.toString())){
	         info.loadItemsFromString(rs.getString("ITEMS"));
	         break;
	    	 }
	      }
	      rs.close();
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	    	e.printStackTrace();
	    	//Bukkit.getLogger().severe(e.getClass().getName() + "[" + e.getCause() +"]" + ":" + e.getMessage() );
		}
		
		return info;
	}
	
	public void save(PlayerInfo info){
		
		Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:plugins\\ItemBox\\players.db");
	      c.setAutoCommit(false);

	      stmt = c.createStatement();
	      String sql = "DELETE from PLAYERS where UUID='" + info.getPlayer().getUniqueId().toString() + "';";
	      stmt.executeUpdate(sql);
	      String items = "'" + info.getItemsInString() + "'";
	     
	      sql = "INSERT INTO PLAYERS (UUID,ITEMS) " +
	                   "VALUES ('" + info.getPlayer().getUniqueId().toString() + "', " 
	                   + items + ");"; 
	      stmt.executeUpdate(sql);

	      stmt.close();
	      c.commit();
	      c.close();
	    } catch ( Exception e ) {
	    	e.printStackTrace();
	    	//Bukkit.getLogger().severe(e.getClass().getName() + "[" + e.getCause() +"]" + ":" + e.getMessage() );
	    }
		
		
	}
	
	public PlayerInfo createPlayerInformation(OfflinePlayer p) {
		PlayerInfo info = new PlayerInfo(p);
		return info;
	}


	
}
