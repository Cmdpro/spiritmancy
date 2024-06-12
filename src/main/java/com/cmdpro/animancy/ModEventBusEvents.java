package com.cmdpro.animancy;

import com.cmdpro.animancy.api.AnimancyUtil;
import com.cmdpro.animancy.config.AnimancyConfig;
import com.cmdpro.animancy.entity.*;
import com.cmdpro.animancy.init.EntityInit;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryBuilder;

@Mod.EventBusSubscriber(modid = Animancy.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(EntityInit.SOULKEEPER.get(), SoulKeeper.setAttributes());
    }
    @SubscribeEvent
    public static void onModConfigEvent(ModConfigEvent event) {
        ModConfig config = event.getConfig();
        if (config.getSpec() == AnimancyConfig.COMMON_SPEC) {
            AnimancyConfig.bake(config);
        }
    }
    @SubscribeEvent
    public static void registerStuff(RegisterEvent event) {
        event.register(ForgeRegistries.Keys.RECIPE_TYPES, helper -> {

        });
    }
    @SubscribeEvent
    public static void registerRegistries(NewRegistryEvent event) {

    }
    @SubscribeEvent
    public static void attributeModifierEvent(EntityAttributeModificationEvent event) {

    }
}