package net.darkhax.runelic.common.impl;

import net.darkhax.bookshelf.common.api.function.CachedSupplier;
import net.darkhax.pricklemc.common.api.config.ConfigManager;
import net.darkhax.runelic.common.impl.config.Config;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class RunelicMod {

    public static final String MOD_ID = "runelic";
    public static final String MOD_NAME = "Runelic";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
    public static final String[] SUPPORTED_CHARACTERS = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "slash", "period", "dash", "comma", "plus", "asterisk", "right_bracket", "left_bracket", "apostrophe", "and", "percent", "dollar", "hashtag", "quote", "exclamation"};
    public static final CachedSupplier<Config> CONFIG = CachedSupplier.cache(() -> ConfigManager.load(MOD_ID, new Config()));

    public static final CachedSupplier<Map<ResourceLocation, Supplier<Boolean>>> PROPERTIES = CachedSupplier.cache(() -> {
        final Map<ResourceLocation, Supplier<Boolean>> properties = new HashMap<>();
        properties.put(id("craftable_banner_pattern"), () -> CONFIG.get().craftable_banner_pattern);
        return properties;
    });

    public static ResourceLocation id(String path) {
        return ResourceLocation.tryBuild(MOD_ID, path);
    }
}