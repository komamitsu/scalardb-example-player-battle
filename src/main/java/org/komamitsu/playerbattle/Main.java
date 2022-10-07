package org.komamitsu.playerbattle;

import com.scalar.db.exception.transaction.TransactionException;
import org.komamitsu.playerbattle.commands.Cli;
import picocli.CommandLine;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, TransactionException {
        Cli cli = new Cli();
        CommandLine commandLine = new CommandLine(cli);
        commandLine.parseArgs(args);
        new CommandLine(cli).execute(args);
    }
}