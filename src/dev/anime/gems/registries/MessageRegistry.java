package dev.anime.gems.registries;

import dev.anime.gems.Main;
import dev.anime.gems.network.SyncTEMessage;
import dev.anime.gems.network.SyncTEMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class MessageRegistry {
	
	private static int discriminator;
	
	private static final SimpleNetworkWrapper WRAPPER = Main.WRAPPER;
	
	public static void registerAllMessages() {
		registerMessage(SyncTEMessageHandler.class, SyncTEMessage.class, Side.SERVER);
		registerMessage(SyncTEMessageHandler.class, SyncTEMessage.class, Side.CLIENT);
	}
	
	private static final <REQ extends IMessage, REPLY extends IMessage>void registerMessage(Class<? extends IMessageHandler<REQ, REPLY>> handlerClass, Class<REQ> messageClass, Side handlerSide) {
		WRAPPER.registerMessage(handlerClass, messageClass, discriminator, handlerSide);
		discriminator++;
	}
	
}
