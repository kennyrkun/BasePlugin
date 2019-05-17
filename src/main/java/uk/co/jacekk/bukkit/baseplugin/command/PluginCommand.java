package uk.co.jacekk.bukkit.baseplugin.command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import uk.co.jacekk.bukkit.baseplugin.BasePlugin;

/**
 * used internally to call the method that should handle a command, this would
 * normally call the onCommand() method.
 * 
 * @author Jacek Kuzemczak
 */
public class PluginCommand extends Command implements PluginIdentifiableCommand
{
	private BasePlugin plugin;
	private BaseCommandExecutor<? extends BasePlugin> handler;
	private Method handlerMethod;
	private HashMap<String, PluginSubCommand> subCommands;
	private String[] tabCompletion;
	
	public PluginCommand(BasePlugin plugin, BaseCommandExecutor<? extends BasePlugin> handler, Method handlerMethod, String[] names, String description, String usage, String[] tabCompletion)
	{
		super(names[0], description, "/<command> " + usage, Arrays.asList(names));
		
		this.plugin = plugin;
		this.handler = handler;
		this.handlerMethod = handlerMethod;
		this.subCommands = new HashMap<String, PluginSubCommand>();
		this.tabCompletion = tabCompletion;
	}
	
	protected void registerSubCommandHandler(String name, PluginSubCommand handler)
	{
		this.subCommands.put(name, handler);
	}
	
	@Override
	public Plugin getPlugin()
	{
		return this.plugin;
	}
	
	@Override
	public boolean execute(CommandSender sender, String label, String[] args)
	{
		try
		{
			Method handlerMethod = null;
			BaseCommandExecutor<? extends BasePlugin> handler = null;
			
			if (args.length > 0 && this.subCommands.containsKey(args[0]))
			{
				handlerMethod = this.subCommands.get(args[0]).handlerMethod;
				handler = this.subCommands.get(args[0]).handler;
				
				String[] subArgs = new String[args.length - 1];
				System.arraycopy(args, 1, subArgs, 0, subArgs.length);
				
				args = subArgs;
			}
			else
			{
				handlerMethod = this.handlerMethod;
				handler = this.handler;
			}
			
			handlerMethod.invoke(handler, sender, label, args);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return true;
	}
	
	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args)
	{
		Method handlerMethod = null;
		BaseCommandExecutor<? extends BasePlugin> handler = null;
		String[] tabCompletion = null;
		
		if (args.length > 1 && this.subCommands.containsKey(args[0]))
		{
			handlerMethod = this.subCommands.get(args[0]).handlerMethod;
			handler = this.subCommands.get(args[0]).handler;
			tabCompletion = this.subCommands.get(args[0]).tabCompletion;
			
			String[] subArgs = new String[args.length - 1];
			System.arraycopy(args, 1, subArgs, 0, subArgs.length);
			
			args = subArgs;
		}
		else
		{
			handlerMethod = this.handlerMethod;
			handler = this.handler;
			tabCompletion = this.tabCompletion;
		}
		
		ArrayList<String> completions = new ArrayList<String>();
		
		boolean empty = args[args.length - 1].isEmpty();
		
		if (args.length <= tabCompletion.length)
		{
			String tab = tabCompletion[args.length - 1];
			String last = args[args.length - 1].toLowerCase();
			
			if (tab.equalsIgnoreCase("<online_player>"))
			{
				for (Player player : plugin.getServer().getOnlinePlayers())
				{
			 		String playerName = player.getName();
			 		String testName = playerName.toLowerCase();
			 		
			 		if (empty || testName.startsWith(last))
			 			completions.add(playerName);
				}
			}
			else if (tab.equalsIgnoreCase("<player>"))
			{
				for (OfflinePlayer player : plugin.getServer().getOfflinePlayers())
				{
			 		String playerName = player.getName();
			 		String testName = playerName.toLowerCase();
			 		
			 		if (empty || testName.startsWith(last))
			 			completions.add(playerName);
				}
			}
			else if (tab.startsWith("[") && tab.endsWith("]"))
			{
				try
				{
					Method tabHandler = handlerMethod.getDeclaringClass().getMethod(tab.substring(1, tab.length() - 1), CommandSender.class, String[].class);
					
					if (tabHandler.getReturnType().equals(List.class))
					{
						for (String value : (List<String>) tabHandler.invoke(handler, sender, args))
						{
							String testValue = value.toLowerCase();
							
							if (empty || testValue.startsWith(last))
								completions.add(value);
						}
					}
				}
				catch (IllegalAccessException e)
				{
					e.printStackTrace();
				}
				catch (InvocationTargetException e)
				{
					e.printStackTrace();
				}
				catch (NoSuchMethodException e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				for (String value : tab.split("\\|"))
				{
					String testValue = value.toLowerCase();
					
					if (empty || testValue.startsWith(last))
						completions.add(value);
				}
			}
		}
		
		return completions;
	}
}
