package org.komamitsu.playerbattle.commands;

import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
    name = "delete",
    description = "Delete a player"
)
public class Delete extends Base implements Callable<Void> {
    @CommandLine.Option(names = {"--id"}, description = "Player ID", required = true)
    String playerId;

    @Override
    public Void call() throws Exception {
        createPlayerService().delete(playerId);
        return null;
    }
}
