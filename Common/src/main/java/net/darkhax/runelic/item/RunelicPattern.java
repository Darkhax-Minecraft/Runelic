package net.darkhax.runelic.item;

import net.darkhax.bookshelf.api.Services;
import net.darkhax.runelic.Constants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BannerPatternItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.entity.BannerPattern;

public class RunelicPattern extends BannerPatternItem {

    private static final TagKey<BannerPattern> BANNER_TAG = Services.TAGS.bannerPatternTag(new ResourceLocation(Constants.MOD_ID, "pattern_item/runelic"));
    private static final Properties PROPERTIES = new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).tab(CreativeModeTab.TAB_DECORATIONS);

    public RunelicPattern() {

        super(BANNER_TAG, PROPERTIES);
    }
}