package net.darkhax.runelic.common.impl;

import net.darkhax.bookshelf.common.api.registry.ContentProvider;
import net.darkhax.bookshelf.common.impl.registry.adapter.ItemRegistryAdapter;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Rarity;

public class RunelicContent implements ContentProvider {

    public static final Identifier BANNER_PATTERN_TAG = RunelicMod.id("pattern_item/runelic");

    @Override
    public void defineItems(ItemRegistryAdapter registry) {
        registry.addSimple("runelic_pattern", props -> props.stacksTo(1).rarity(Rarity.UNCOMMON).delayedComponent(DataComponents.PROVIDES_BANNER_PATTERNS, ctx -> ctx.getOrThrow(TagKey.create(Registries.BANNER_PATTERN, BANNER_PATTERN_TAG))));
    }

    @Override
    public String namespace() {
        return RunelicMod.MOD_ID;
    }
}