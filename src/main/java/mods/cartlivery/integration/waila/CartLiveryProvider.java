package mods.cartlivery.integration.waila;

import java.util.List;

import cpw.mods.fml.common.Loader;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaEntityAccessor;
import mcp.mobius.waila.api.IWailaEntityProvider;
import mods.cartlivery.client.LiveryTextureInfo;
import mods.cartlivery.client.LiveryTextureRegistry;
import mods.cartlivery.common.CartLivery;
import mods.cartlivery.common.utils.ColorUtils;
import mods.railcraft.client.emblems.Emblem;
import mods.railcraft.client.emblems.EmblemToolsClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class CartLiveryProvider implements IWailaEntityProvider {

	public Entity getWailaOverride(IWailaEntityAccessor paramIWailaEntityAccessor, IWailaConfigHandler paramIWailaConfigHandler) {
		return null;
	}

	public List<String> getWailaHead(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor, IWailaConfigHandler configHandler) {
		return currenttip;
	}

	public List<String> getWailaBody(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor, IWailaConfigHandler configHandler) {
		if (entity.getExtendedProperties(CartLivery.EXT_PROP_NAME) != null) {
			CartLivery livery = (CartLivery) entity.getExtendedProperties(CartLivery.EXT_PROP_NAME);
			LiveryTextureInfo info = LiveryTextureRegistry.map.get(livery.pattern);
			
			if (configHandler.getConfig("cartlivery.showPattern")) {
				if (!livery.pattern.isEmpty()) {
					if (info == null) {
						currenttip.add(String.format("\u00a7m%s", livery.pattern));
					} else {
						if (configHandler.getConfig("cartlivery.showResPack")) {
							currenttip.add("Pattern: "+String.format("%s (%s)", I18n.format("cartlivery." + livery.pattern + ".name"), info.packName));
						} else {
							currenttip.add("Pattern: "+I18n.format("cartlivery." + livery.pattern + ".name"));
						}
					}
				}
			}

			if (configHandler.getConfig("cartlivery.showColors")) {
				if (!livery.pattern.isEmpty()) {
					currenttip.add(String.format("%s / %s", ColorUtils.getColorName(livery.baseColor), ColorUtils.getColorName(livery.patternColor)));
				} else {
					currenttip.add(ColorUtils.getColorName(livery.baseColor));
				}
			}
			
			if (Loader.isModLoaded("Railcraft") && configHandler.getConfig("cartlivery.showEmblem")) {
				if (!livery.emblem.isEmpty()) {
					Emblem emblem = EmblemToolsClient.packageManager.getEmblem(livery.emblem);
					if (emblem != null){
						currenttip.add("Emblem: "+emblem.displayName);
					}
				}
			}
		}
		return currenttip;
	}

	public List<String> getWailaTail(Entity entity, List<String> currenttip, IWailaEntityAccessor accessor, IWailaConfigHandler configHandler) {
		return currenttip;
	}

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP arg0, Entity arg1,
			NBTTagCompound arg2, World arg3) {
		// TODO Auto-generated method stub
		return null;
	}

}
