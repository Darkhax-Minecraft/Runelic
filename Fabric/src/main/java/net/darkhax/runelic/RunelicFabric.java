package net.darkhax.runelic;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class RunelicFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {

        new RunelicCommon();

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register(content -> {

            final Item item = BuiltInRegistries.ITEM.get(new ResourceLocation(Constants.MOD_ID, "runelic_pattern"));
            content.addAfter(new ItemStack(Items.PIGLIN_BANNER_PATTERN), new ItemStack(item));
        });
    }
}