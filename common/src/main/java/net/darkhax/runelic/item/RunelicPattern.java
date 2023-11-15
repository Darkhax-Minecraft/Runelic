package net.darkhax.runelic.item;

import net.darkhax.runelic.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BannerPatternItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.entity.BannerPattern;

public class RunelicPattern extends BannerPatternItem {

    private static final TagKey<BannerPattern> BANNER_TAG = TagKey.create(Registries.BANNER_PATTERN, new ResourceLocation(Constants.MOD_ID, "pattern_item/runelic"));
    private static final Properties PROPERTIES = new Item.Properties().stacksTo(1).rarity(Rarity.EPIC);

    public RunelicPattern() {

        super(BANNER_TAG, PROPERTIES);
    }
}