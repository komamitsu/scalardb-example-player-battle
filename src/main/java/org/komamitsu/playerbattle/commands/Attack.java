package org.komamitsu.playerbattle.commands;

import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
    name = "attack",
    description = "Attack other player"
)
public class Attack extends Base implements Callable<Void> {
    @CommandLine.Option(names = {"--id"}, description = "Player ID", required = true)
    String playerId;

    @CommandLine.Option(names = {"--other-id"}, description = "Player HP", required = true)
    String otherId;

    @Override
    public Void call() throws Exception {
        createPlayerService().attack(playerId, otherId);
        return null;
    }
}
