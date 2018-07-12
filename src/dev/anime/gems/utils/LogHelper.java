package dev.anime.gems.utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import dev.anime.gems.Main;

public class LogHelper {
	
	private static final Logger LOGGER = Main.LOGGER;
	
	public static void inform(Object... strings) {
		StringBuilder builder = new StringBuilder();
		for (Object s : strings) builder.append(s);
		LOGGER.log(Level.INFO, builder.toString());
	}
	
	public static void warn(Object... strings) {
		StringBuilder builder = new StringBuilder();
		for (Object s : strings) builder.append(s);
		LOGGER.log(Level.WARN, builder.toString());
	}
	
	public static void error(Object... strings) {
		StringBuilder builder = new StringBuilder();
		for (Object s : strings) builder.append(s);
		LOGGER.log(Level.ERROR, builder.toString());
	}
	
}
