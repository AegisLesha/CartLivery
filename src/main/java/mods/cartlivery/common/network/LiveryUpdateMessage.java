package mods.cartlivery.common.network;

import net.minecraft.entity.Entity;
import io.netty.buffer.ByteBuf;
import mods.cartlivery.common.CartLivery;
import mods.cartlivery.common.utils.NetworkUtil;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class LiveryUpdateMessage implements IMessage {

	public int dimId;
	public int entityId;
	public CartLivery livery;
	
	public LiveryUpdateMessage() { }
	
	public LiveryUpdateMessage(Entity cart, CartLivery livery) {
		dimId = cart.worldObj.provider.dimensionId;
		entityId = cart.getEntityId();
		this.livery = livery;
	}
	
	public void fromBytes(ByteBuf buf) {
		dimId = buf.readInt();
		entityId = buf.readInt();
		livery = new CartLivery();
		livery.baseColor = buf.readByte();
		livery.patternColor = buf.readByte();
		livery.pattern = NetworkUtil.readString(buf);
		livery.emblem = NetworkUtil.readString(buf);
	}

	public void toBytes(ByteBuf buf) {
		buf.writeInt(dimId);
		buf.writeInt(entityId);
		buf.writeByte(livery.baseColor);
		buf.writeByte(livery.patternColor);
		NetworkUtil.writeString(livery.pattern, buf);
		NetworkUtil.writeString(livery.emblem, buf);
	}

}
