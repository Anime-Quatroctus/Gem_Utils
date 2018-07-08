package dev.anime.gems;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dev.anime.gems.proxies.ServerProxy;
import dev.anime.gems.registries.MessageRegistry;
import dev.anime.gems.registries.TileEntityRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Main.MODID, version = Main.VERSION, name = Main.NAME)
public class Main {
	
	public static final String MODID = "gem_utils";
	public static final String VERSION = "1.0";
	public static final String NAME = "Gem Utilities";
	private static final String BASE_DIRECTORY = "dev.anime.gems";
	
	@SidedProxy(serverSide = BASE_DIRECTORY + ".proxies.ServerProxy", clientSide = BASE_DIRECTORY + ".proxies.ClientProxy")
	public static ServerProxy PROXY;
	
	@Instance(MODID)
	public static Main INSTANCE;
	
	public static final SimpleNetworkWrapper WRAPPER = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
	
	public static final GuiHandler HANDLER = new GuiHandler();
	
	public static final Logger LOGGER = LogManager.getLogger(MODID);;
	public static final boolean DEBUG = true;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		LOGGER.log(Level.INFO, "Gem Utilities Pre-Initialization Start.");
		PROXY.preInit();
		registerAllNonForgeRegistries(event.getSide());
		LOGGER.log(Level.INFO, "Gem Utilities Pre-initialization End.");
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		LOGGER.log(Level.INFO, "Gem Utilities Initialization Start.");


		
		LOGGER.log(Level.INFO, "Gem Utilities Initialization End.");
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		LOGGER.log(Level.INFO, "Gem Utilities Post-Initialization Start.");
		
		
		
		LOGGER.log(Level.INFO, "Gem Utilities Post-Initialization End.");
	}
	
	private static final void registerAllNonForgeRegistries(Side side) {
		MessageRegistry.registerAllMessages();
		TileEntityRegistry.registerAllTileEntities();
		NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, HANDLER);
	}
	
}