package net.darkhax.runelic;

import net.darkhax.bookshelf.api.Services;

public class RunelicCommon {

    public RunelicCommon() {

        Services.REGISTRIES.loadContent(new Content());
    }
}
