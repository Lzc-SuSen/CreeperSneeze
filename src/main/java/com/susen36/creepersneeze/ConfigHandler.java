package com.susen36.creepersneeze;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigHandler {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final General GENERAL = new General(BUILDER);
    public static final ForgeConfigSpec spec = BUILDER.build();

    public static class General {
        public final ForgeConfigSpec.ConfigValue<Boolean> HungerPlayers;


        public General(ForgeConfigSpec.Builder builder) {
            builder.push("General");
            HungerPlayers = builder
                    .comment("Set Hunger Effect to Players [false/true|default:true]")
                    .translation("hungerplayers.creepersneeze.config")
                    .define("damagePlayers", true);
        }
    }
}