package net.darkhax.runelic.common.impl;

import com.mojang.serialization.MapCodec;
import net.darkhax.bookshelf.common.api.data.conditions.ILoadCondition;
import net.darkhax.bookshelf.common.api.entity.villager.trades.VillagerOffers;
import net.darkhax.bookshelf.common.api.registry.IContentProvider;
import net.darkhax.bookshelf.common.api.registry.register.Register;
import net.darkhax.bookshelf.common.api.registry.register.RegisterVillagerTrades;
import net.darkhax.runelic.common.impl.config.TradeConfig;
import net.darkhax.runelic.common.impl.data.conditions.ConfigProperty;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BannerPatternItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class ContentProvider implements IContentProvider {

    @Override
    public void registerItems(Register<Item> registry) {
        registry.add("runelic_pattern", new BannerPatternItem(TagKey.create(Registries.BANNER_PATTERN, RunelicMod.id("pattern_item/runelic")), new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    }

    @Override
    public void registerTrades(RegisterVillagerTrades registry) {
        final TradeConfig config = RunelicMod.CONFIG.get().banner_stencil_trade;
        if (config.enabled) {
            registry.addWanderingTrade(new VillagerOffers(config.tradeOffer), config.is_rare);
        }
    }

    @Override
    public void registerLoadConditions(Register<MapCodec<? extends ILoadCondition>> registry) {
        registry.add(ConfigProperty.TYPE_ID, ConfigProperty.CODEC);
    }

    @Override
    public String contentNamespace() {
        return RunelicMod.MOD_ID;
    }
}