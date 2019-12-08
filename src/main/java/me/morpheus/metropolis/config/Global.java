package me.morpheus.metropolis.config;

import me.morpheus.metropolis.api.config.ChatCategory;
import me.morpheus.metropolis.api.config.EconomyCategory;
import me.morpheus.metropolis.api.config.GlobalConfig;
import me.morpheus.metropolis.api.config.TownCategory;
import me.morpheus.metropolis.config.category.SimpleChatCategory;
import me.morpheus.metropolis.config.category.SimpleEconomyCategory;
import me.morpheus.metropolis.config.category.SimpleTownCategory;
import ninja.leaping.configurate.objectmapping.Setting;

import java.time.Duration;

public class Global implements GlobalConfig {

    @Setting(value = "town", comment =
            " +------------------------------------------------------+ \n" +
            " |                         Town                         | \n" +
            " +------------------------------------------------------+   "
    )
    private SimpleTownCategory town = new SimpleTownCategory();

    @Setting(comment =
            " +------------------------------------------------------+ \n" +
            " |                         Chat                         | \n" +
            " +------------------------------------------------------+   "
    )
    private SimpleChatCategory chat = new SimpleChatCategory();

    @Setting(comment =
            " +------------------------------------------------------+ \n" +
            " |                        Economy                       | \n" +
            " +------------------------------------------------------+   "
    )
    private SimpleEconomyCategory economy = new SimpleEconomyCategory();

    @Setting(value = "enable-plot-message", comment = "show town name when entering a plot. Disable by default for performance reason")
    private boolean plotMessage;

    @Setting(value = "users-per-tick", comment = "show town name when entering a plot. Disable by default for performance reason")
    private int userspertick = 100;

    @Setting(value = "save-interval", comment = "show town name when entering a plot. Disable by default for performance reason")
    private Duration saveInterval = Duration.ofMinutes(30L);

    @Override
    public TownCategory getTownCategory() {
        return this.town;
    }

    @Override
    public ChatCategory getChatCategory() {
        return this.chat;
    }

    @Override
    public EconomyCategory getEconomyCategory() {
        return this.economy;
    }

    @Override
    public boolean isPlotMessageEnabled() {
        return this.plotMessage;
    }

    @Override
    public int getUserspertick() {
        return this.userspertick;
    }

    @Override
    public Duration getSaveInterval() {
        return this.saveInterval;
    }
}