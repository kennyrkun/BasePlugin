package uk.co.jacekk.bukkit.baseplugin.util;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.bukkit.ChatColor;

/**
 * A collection of useful methods for working with Minecraft chat.
 * 
 * @author Jacek Kuzemczak
 */
public class ChatUtils
{
	private static final LinkedHashMap<String, ChatColor> formattingCodeMap;
	
	static
	{
		formattingCodeMap = new LinkedHashMap<String, ChatColor>();
		
		for (ChatColor colour : ChatColor.values())
			if (colour.isColor())
				formattingCodeMap.put("&" + colour.getChar(), colour);
			else
				formattingCodeMap.put("#" + colour.name().substring(0, 1).toLowerCase(), colour);
	}
	
	/**
	 * Gets the list of formatting codes and the values they represent.
	 * <p>
	 * Colour codes are a the colours hex value prefixed with a '&'
	 * Formatting codes are the name of the format prefixed with a #, e.g. the code for underlined is #u
	 *
	 * @return	The map.
	 */
	public static LinkedHashMap<String, ChatColor> getFormattingCodeMap()
	{
		return formattingCodeMap;
	}
	
	/**
	 * Replaces colour/formatting codes in a string with the actual colours.
	 * 
	 * @param message	The message to parse.
	 * @return			the message with colour codes replaced.
	 */
	public static String parseFormattingCodes(String message)
	{
		for (Entry<String, ChatColor> entry : formattingCodeMap.entrySet())
			message = message.replaceAll(entry.getKey(), entry.getValue().toString());

		return message;
	}
}
