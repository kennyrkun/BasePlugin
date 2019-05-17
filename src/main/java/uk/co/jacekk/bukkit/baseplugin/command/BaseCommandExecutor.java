package uk.co.jacekk.bukkit.baseplugin.command;

import uk.co.jacekk.bukkit.baseplugin.BaseObject;

/**
 * The base class that all command executors should extend. Commands are registered
 * using {@link CommandManager} and defined using the {@link CommandHandler} annotations.
 * 
 * @author Jacek Kuzemczak
 *
 * @param <Type>	The plugin that this executor belongs to.
 */
public abstract class BaseCommandExecutor<Type> extends BaseObject<Type>
{
	/**
	 * @param plugin	The plugin that this executor belongs to.
	 */
	public BaseCommandExecutor(Type plugin){
		super(plugin);
	}
}
