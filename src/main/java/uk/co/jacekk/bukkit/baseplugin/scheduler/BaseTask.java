package uk.co.jacekk.bukkit.baseplugin.scheduler;

import uk.co.jacekk.bukkit.baseplugin.BaseObject;

/**
 * The base class that any repeating or scheduled tasks should extend.
 * 
 * @author Jacek Kuzemczak
 *
 * @param <Type>	The plugin that this task belongs to.
 */
public abstract class BaseTask<Type> extends BaseObject<Type> implements Runnable
{
	/**
	 * @param plugin 	The plugin that this task belongs to.
	 */
	public BaseTask(Type plugin)
	{
		super(plugin);
	}
}
