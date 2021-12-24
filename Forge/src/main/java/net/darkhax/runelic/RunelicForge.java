package net.darkhax.runelic;

import net.minecraft.commands.Commands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(Constants.MOD_ID)
@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RunelicForge {

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {

        RunelicCommands.registerCommands(event.getDispatcher(), event.getEnvironment() == Commands.CommandSelection.DEDICATED);
    }
}