package com.susen36.creepersneeze;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;

@Mod("creepersneeze")
@Mod.EventBusSubscriber(modid = "creepersneeze", bus = Mod.EventBusSubscriber.Bus.MOD)

public class CreeperSneeze
{
	public CreeperSneeze() {
	ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.COMMON, com.susen36.creepersneeze.ConfigHandler.spec);

	}
	public static SoundEvent ACHOO;
	@SubscribeEvent
	public static void registerSound(RegistryEvent.Register<SoundEvent> e) {
		final ResourceLocation loc = new ResourceLocation("creepersneeze","achoo");
		ACHOO = new SoundEvent(loc).setRegistryName(loc);
		e.getRegistry().register(ACHOO);
	}
}