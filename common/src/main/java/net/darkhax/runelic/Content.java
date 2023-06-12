package net.darkhax.runelic;

import net.darkhax.bookshelf.api.entity.merchant.trade.VillagerSells;
import net.darkhax.bookshelf.api.item.tab.ITabBuilder;
import net.darkhax.bookshelf.api.registry.IRegistryObject;
import net.darkhax.bookshelf.api.registry.RegistryDataProvider;
import net.darkhax.runelic.item.RunelicPattern;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BannerPattern;

import java.util.Arrays;
import java.util.function.Consumer;

public class Content extends RegistryDataProvider {

    private static final String[] PATTERN_NAMES = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "slash", "period", "dash", "comma", "plus", "asterisk", "right_bracket", "left_bracket", "apostrophe", "and", "percent", "dollar", "hashtag", "quote", "exclamation"};

    private final IRegistryObject<Item> patternStencil;

    public Content() {

        super(Constants.MOD_ID);

        Arrays.stream(PATTERN_NAMES).forEach(this::createPattern);
        patternStencil = this.items.add(RunelicPattern::new, "runelic_pattern");
        this.trades.addRareWanderingTrade(VillagerSells.create(patternStencil, 4, 5, 10, 0.05f));
        this.creativeTabs.add(this::makeItemTab, "creative_tab");
    }

    private Consumer<ITabBuilder> makeItemTab() {

        return builder -> {

            builder.icon(Items.CREEPER_BANNER_PATTERN::getDefaultInstance);
            builder.displayItems((params, output) -> {
                output.accept(patternStencil);
            });
        };
    }

    private void createPattern(String name) {

        final String id = "runelic_" + name;
        this.bannerPatterns.add(() -> new BannerPattern(id), id);
    }
}