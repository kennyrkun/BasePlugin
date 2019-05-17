package uk.co.jacekk.bukkit.baseplugin;

// The class that all other object belonging to this plugin should extend.
// author: Jacek Kuzemczak
// <Type>: The plugin that this object belong to.
public abstract class BaseObject<Type>
{
	// The plugin that created this object.
	protected Type plugin;

	// plugin: The plugin that this object belong to.
	public BaseObject(Type plugin)
	{
		this.plugin = plugin;
	}
}
