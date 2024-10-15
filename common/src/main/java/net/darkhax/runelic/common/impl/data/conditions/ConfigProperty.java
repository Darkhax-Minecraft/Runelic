package net.darkhax.runelic.common.impl.data.conditions;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.darkhax.bookshelf.common.api.data.codecs.map.MapCodecs;
import net.darkhax.bookshelf.common.api.data.conditions.ConditionType;
import net.darkhax.bookshelf.common.api.data.conditions.ILoadCondition;
import net.darkhax.bookshelf.common.api.data.conditions.LoadConditions;
import net.darkhax.bookshelf.common.api.function.CachedSupplier;
import net.darkhax.runelic.common.impl.RunelicMod;
import net.minecraft.resources.ResourceLocation;

public class ConfigProperty implements ILoadCondition {

    public static final ResourceLocation TYPE_ID = RunelicMod.id("config");
    public static final CachedSupplier<ConditionType> TYPE = CachedSupplier.cache(() -> LoadConditions.getType(TYPE_ID));
    public static final MapCodec<ConfigProperty> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(MapCodecs.RESOURCE_LOCATION.get("property", ConfigProperty::getProperty)).apply(instance, ConfigProperty::new));

    private final ResourceLocation property;

    private ConfigProperty(ResourceLocation property) {
        this.property = property;
    }

    public ResourceLocation getProperty() {
        return this.property;
    }

    @Override
    public boolean allowLoading() {
        return RunelicMod.PROPERTIES.get().containsKey(this.property) && RunelicMod.PROPERTIES.get().get(this.property).get();
    }

    @Override
    public ConditionType getType() {
        return TYPE.get();
    }
}