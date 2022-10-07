package org.komamitsu.playerbattle.commands;

import com.scalar.db.service.TransactionFactory;
import org.komamitsu.playerbattle.PlayerService;
import picocli.CommandLine;

import java.io.IOException;

/**
 * Picocli doesn't allow a parent class that has an annotation including `subcommands` to be inherited by the sub command classes by throwing
 * "picocli.CommandLine$InitializationException: player-battle (org.komamitsu.playerbattle.commands.Cli) has a subcommand (org.komamitsu.playerbattle.commands.Create) that is a subclass of itself".
 * That's reason why we use Base class.
 */
abstract class Base {
    @CommandLine.ParentCommand Cli base;

    protected PlayerService createPlayerService() throws IOException {
        TransactionFactory factory = TransactionFactory.create(base.path);
        return new PlayerService(factory.getTransactionManager());
    }
}
