package net.darkhax.runelic;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class RunelicFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> RunelicCommands.registerCommands(dispatcher, environment.includeDedicated));
    }
}