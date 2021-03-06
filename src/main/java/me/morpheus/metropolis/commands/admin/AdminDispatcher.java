package me.morpheus.metropolis.commands.admin;

import me.morpheus.metropolis.command.AbstractCommandDispatcher;
import me.morpheus.metropolis.commands.admin.plot.PlotDispatcher;
import me.morpheus.metropolis.commands.admin.town.TownDispatcher;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;

import java.util.Optional;

public final class AdminDispatcher extends AbstractCommandDispatcher {

    @Override
    public void registerDefaults() {
        register(new SaveCommand(), "save");

        registerManager(new TownDispatcher(), "town");
        registerManager(new PlotDispatcher(), "plot");

        register(new ForceTaxCommand(), "forcetax");
    }

    @Override
    public Optional<Text> getShortDescription(CommandSource source) {
        return Optional.empty();
    }
}
