package net.darkhax.runelic;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Constants.MOD_ID)
public class RunelicForge {

    public RunelicForge() {

        new RunelicCommon();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::addItem);
    }

    private void addItem(CreativeModeTabEvent.BuildContents event) {

        if (event.getTab() == CreativeModeTabs.INGREDIENTS) {

            final Item item = BuiltInRegistries.ITEM.get(new ResourceLocation(Constants.MOD_ID, "runelic_pattern"));
            event.getEntries().putAfter(new ItemStack(Items.PIGLIN_BANNER_PATTERN), new ItemStack(item), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }
}