package net.darkhax.runelic.common.impl.config;

import net.darkhax.bookshelf.common.api.function.CachedSupplier;
import net.darkhax.pricklemc.common.api.annotations.Value;
import net.darkhax.runelic.common.impl.RunelicMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;

import java.util.Objects;

public class TradeConfig {

    @Value(comment = "Determines if the trade should be registered or not.")
    public boolean enabled = true;

    @Value(comment = "The ID of the item used to buy this trade.")
    public ResourceLocation currency_item = ResourceLocation.fromNamespaceAndPath("minecraft", "emerald");

    @Value(comment = "The amount of the currency item required.")
    public int cost = 5;

    @Value(comment = "The amount of times the trade can be performed before the villager needs to restock.")
    public int uses = 4;

    @Value(comment = "Determines if the trade should be registered to the rare pool or the common pool.")
    public boolean is_rare = true;

    public final CachedSupplier<MerchantOffer> tradeOffer = CachedSupplier.cache(() -> {
        final Item output = getItem(RunelicMod.id("runelic_pattern"));
        final Item currency = getItem(this.currency_item);
        return new MerchantOffer(new ItemCost(currency, this.cost), output.getDefaultInstance(), this.uses, 1, 0.05f);
    });

    private static Item getItem(ResourceLocation id) {
        final Item item = Objects.requireNonNull(BuiltInRegistries.ITEM.get(id));
        if (item == Items.AIR) {
            throw new IllegalArgumentException("Item must not be AIR! Input: " + id);
        }
        return item;
    }
}