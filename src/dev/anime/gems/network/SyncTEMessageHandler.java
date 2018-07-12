package dev.anime.gems.network;

import dev.anime.gems.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class SyncTEMessageHandler implements IMessageHandler<SyncTEMessage, SyncTEMessage> {

	@Override
	public SyncTEMessage onMessage(SyncTEMessage message, MessageContext ctx) {
		if (ctx.side == Side.CLIENT) {
			System.out.println("Message Received");
			Minecraft.getMinecraft().addScheduledTask(() -> {
				Minecraft.getMinecraft().world.getTileEntity(message.getPos()).readFromNBT(message.getTag());
				Minecraft.getMinecraft().world.markBlockRangeForRenderUpdate(message.getPos(), message.getPos());
			});
		} else {
			WorldServer world = ctx.getServerHandler().player.getServerWorld();
			NBTTagCompound tag = new NBTTagCompound();
			TileEntity te = world.getTileEntity(message.getPos());
			if (te != null) {
				te.writeToNBT(tag);
				Main.WRAPPER.sendToAll(new SyncTEMessage(message.getPos(), tag));
			}
		}
		return null;
	}

}
