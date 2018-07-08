package dev.anime.gems.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class SyncTEMessage implements IMessage {
	
	private BlockPos pos;
	private NBTTagCompound tag;
	
	public SyncTEMessage() { }
	
	public SyncTEMessage(BlockPos pos, NBTTagCompound tag) {
		this.pos = pos;
		this.tag = tag;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		int x = buf.readInt();
		int y = buf.readInt();
		int z = buf.readInt();
		pos = new BlockPos(x, y, z);
		tag = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
		ByteBufUtils.writeTag(buf, tag);
	}
	
	public BlockPos getPos() {
		return pos;
	}
	
	public NBTTagCompound getTag() {
		return tag;
	}
	
}
