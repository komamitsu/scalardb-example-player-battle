package org.komamitsu.playerbattle.commands;

import com.scalar.db.service.TransactionFactory;
import org.komamitsu.playerbattle.PlayerService;
import picocli.CommandLine;

import java.io.IOException;

abstract class Base {
    @CommandLine.ParentCommand Cli base;

    protected PlayerService createPlayerService() throws IOException {
        TransactionFactory factory = TransactionFactory.create(base.path);
        return new PlayerService(factory.getTransactionManager());
    }
}
