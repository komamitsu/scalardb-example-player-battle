package org.komamitsu.gamebattle;

import com.scalar.db.exception.transaction.TransactionException;
import com.scalar.db.service.TransactionFactory;
import picocli.CommandLine;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, TransactionException {
        Cli cli = new Cli();
        CommandLine commandLine = new CommandLine(cli);
        commandLine.parseArgs(args);
        new CommandLine(cli).execute(args);
        TransactionFactory factory = TransactionFactory.create(cli.path);
        PlayerService playerService = new PlayerService(factory.getTransactionManager());
        if (cli.command instanceof Cli.CreateCommand cmd) {
            playerService.upsert(new Player(cmd.id(), cmd.hp(), cmd.attack()));
        }
        else if (cli.command instanceof Cli.GetCommand cmd) {
            System.out.println(playerService.get(cmd.id()));
        }
        else if (cli.command instanceof Cli.AttackCommand cmd) {
            playerService.attack(cmd.id(), cmd.otherId());
        }
    }
}