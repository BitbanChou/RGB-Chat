package com.fred.jianghun.proxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

import java.util.ArrayList;
import java.util.List;

public class ClientProxy extends ProxyBase {
    public static final List<KeyBinding> KEY_BINDINGS = new ArrayList<KeyBinding>();
	//public static final KeyBinding SUMMON = new ModKeyBinding("activate_skill_summon", KeyConflictContext.IN_GAME, KeyModifier.NONE, Keyboard.KEY_V, "key.category.idlframewok");
//	public static final KeyBinding EFFECTME = new ModKeyBinding("activate_skill_effectme", KeyConflictContext.IN_GAME, KeyModifier.NONE, Keyboard.KEY_R, "key.category.idlframewok");

	public boolean isServer()
	{
		return false;
	}

	public void registerItemRenderer(Item item, int meta, String id)
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
	}

//	@Override
//	public void registerParticles() {
//		ModParticles.registerParticles();
//	}
}
