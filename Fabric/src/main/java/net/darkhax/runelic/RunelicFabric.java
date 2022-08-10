package net.darkhax.runelic;

import net.fabricmc.api.ModInitializer;

public class RunelicFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {

        new RunelicCommon();
    }
}