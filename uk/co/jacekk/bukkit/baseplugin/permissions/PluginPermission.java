package uk.co.jacekk.bukkit.baseplugin.permissions;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;

public interface PluginPermission {
	
	public List<Player> getPlayersWith();
	
	public Boolean hasPermission(CommandSender sender);
	
	public String getNode();
	
	public PermissionDefault getDefault();
	
	public String getDescription();
	
}
