package net.darkhax.runelic.common.impl.config;

import net.darkhax.pricklemc.common.api.annotations.Value;

public class Config {

    @Value(comment = "The wandering trader may sell the Runelic alphabet banner pattern to players. These options configure the properties of that trade entry.", writeDefault = false)
    public TradeConfig banner_stencil_trade = new TradeConfig();

    @Value(comment = "Determines if the runelic banner pattern recipe should be craftable.")
    public boolean craftable_banner_pattern = true;
}