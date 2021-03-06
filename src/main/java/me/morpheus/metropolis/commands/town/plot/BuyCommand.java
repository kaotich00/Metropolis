package me.morpheus.metropolis.commands.town.plot;

import me.morpheus.metropolis.Metropolis;
import me.morpheus.metropolis.api.data.citizen.CitizenData;
import me.morpheus.metropolis.api.command.AbstractHomeTownCommand;
import me.morpheus.metropolis.api.plot.Plot;
import me.morpheus.metropolis.api.town.Town;
import me.morpheus.metropolis.util.EconomyUtil;
import me.morpheus.metropolis.util.TextUtil;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.service.economy.account.Account;
import org.spongepowered.api.service.economy.transaction.ResultType;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.math.BigDecimal;
import java.util.Optional;

class BuyCommand extends AbstractHomeTownCommand {

    public BuyCommand() {
        super(
                Metropolis.ID + ".commands.town.plot.buy",
                Text.of()
        );
    }

    @Override
    public CommandResult process(Player source, CommandContext context, CitizenData cd, Town t, Plot plot) throws CommandException {
        if (!plot.isForSale()) {
            source.sendMessage(TextUtil.watermark(TextColors.RED, "This plot is not for sale"));
            return CommandResult.empty();
        }
        final Optional<Account> bOpt = t.getBank();
        if (!bOpt.isPresent()) {
            source.sendMessage(TextUtil.watermark(TextColors.RED, "Unable to retrieve Town bank"));
            return CommandResult.empty();
        }
        final EconomyService es = Sponge.getServiceManager().provideUnchecked(EconomyService.class);

        final Optional<Account> accOpt = es.getOrCreateAccount(source.getIdentifier());
        if (!accOpt.isPresent()) {
            source.sendMessage(TextUtil.watermark(TextColors.RED, "Unable to retrieve player account"));
            return CommandResult.empty();
        }
        final ResultType result = EconomyUtil.transfer(accOpt.get(), bOpt.get(), es.getDefaultCurrency(), BigDecimal.valueOf(plot.getPrice()));
        if (result == ResultType.ACCOUNT_NO_FUNDS) {
            source.sendMessage(TextUtil.watermark(TextColors.RED, "Not enough money"));
            return CommandResult.empty();
        }
        if (result != ResultType.SUCCESS) {
            source.sendMessage(TextUtil.watermark(TextColors.RED, "Error while paying: ", result.name()));
            return CommandResult.empty();
        }
        plot.setOwner(source.getUniqueId());
        source.sendMessage(TextUtil.watermark(TextColors.AQUA, "You bought this plot for ", plot.getPrice()));
        plot.setPrice(0.0);
        plot.setForSale(false);

        return CommandResult.success();
    }
}
