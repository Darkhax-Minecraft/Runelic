package net.darkhax.runelic;

import net.darkhax.bookshelf.api.entity.merchant.trade.VillagerSells;
import net.darkhax.bookshelf.api.registry.IRegistryObject;
import net.darkhax.bookshelf.api.registry.RegistryDataProvider;
import net.darkhax.runelic.item.RunelicPattern;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BannerPattern;

import java.util.Arrays;

public class Content extends RegistryDataProvider {

    private static final String[] PATTERN_NAMES = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "slash", "period", "dash", "comma", "plus", "asterisk", "right_bracket", "left_bracket", "apostrophe", "and", "percent", "dollar", "hashtag", "quote", "exclamation"};

    public Content() {

        super(Constants.MOD_ID);

        Arrays.stream(PATTERN_NAMES).forEach(this::createPattern);
        final IRegistryObject<Item> patternStencil = this.items.add(RunelicPattern::new, "runelic_pattern");
        this.trades.addRareWanderingTrade(VillagerSells.create(patternStencil, 4, 5, 10, 0.05f));
    }

    private void createPattern(String name) {

        final String id = "runelic_" + name;
        this.bannerPatterns.add(() -> new BannerPattern(id), id);
    }
}