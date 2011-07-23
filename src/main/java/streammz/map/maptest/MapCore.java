package streammz.map.maptest;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.event.map.MapInitializeEvent;
import org.bukkit.event.map.MapListener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import streammz.map.maptest.Map.ServerStaticBase;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class MapCore extends JavaPlugin {
	public static PermissionHandler permissionHandler;
	public static short MapId = (short) 10;
	
	
	public void onDisable() {
	}

	public void onEnable() {
		final MapCore plugin = this;
		
		setupPermissions();
		
		getServer().getPluginManager().registerEvent(Type.MAP_INITIALIZE, new MapListener() {
			@Override
			public void onMapInitialize(MapInitializeEvent event) {
				System.out.println("Initialize");
				if (event.getMapView().getId() == MapCore.MapId) {
					event.getMapView().registerVirtualBase(new ServerStaticBase(), plugin);
					
					return;
				}
			}
		}, Priority.Normal, this);
		
		PluginDescriptionFile pdfFile = getDescription();
	    System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player p = (Player)sender;
		String cmd = command.getName().toLowerCase();
		
		if (cmd.equals("maptest")) {
			ItemStack mapStack = new ItemStack(Material.MAP, 1, MapId, null);
			
			int a = p.getInventory().firstEmpty();
			p.getInventory().setItem(a, mapStack);
			
			p.getInventory().getItem(a).setDurability(MapId);
			System.out.println(p.getInventory().getItem(a).getDurability());
		}
		
		return true;
	}
	
	
	private void setupPermissions() {
	    Plugin permissionsPlugin = this.getServer().getPluginManager().getPlugin("Permissions");
	    if (permissionHandler == null) {
	    	if (permissionsPlugin != null) {
	    		permissionHandler = ((Permissions) permissionsPlugin).getHandler();
         	} else {
	     	}
	     }
	}
	public static boolean hasPermission(Player p, String node) {
		if (p.isOp()) { return true; }
		if (!Bukkit.getServer().getPluginManager().isPluginEnabled("Permissions")) { return false; }
		if (permissionHandler.has(p, node)) { return true; }
		return false;
	}
	public static boolean hasPermission(CommandSender s, String node) {
		if (s instanceof Player) return hasPermission((Player)s,node);
		return true;
	}
}