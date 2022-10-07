package org.komamitsu.playerbattle.commands;

import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
    name = "show",
    description = "Show a player info"
)
public class Show extends Base implements Callable<Void> {
    @CommandLine.Option(names = {"--id"}, description = "Player ID", required = true)
    String playerId;

    @Override
    public Void call() throws Exception {
        System.out.println(createPlayerService().get(playerId));
        return null;
    }
}
