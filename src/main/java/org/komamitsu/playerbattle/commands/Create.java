package org.komamitsu.playerbattle.commands;

import org.komamitsu.playerbattle.Player;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
    name = "create",
    description = "Create a player"
)
public class Create extends Base implements Callable<Void> {
    @CommandLine.Option(names = {"--id"}, description = "Player ID", required = true)
    String playerId;

    @CommandLine.Option(names = {"--hp"}, description = "Player HP", required = true)
    Integer playerHp;

    @CommandLine.Option(names = {"--attack"}, description = "Player attack", required = true)
    Integer playerAttack;

    @Override
    public Void call() throws Exception {
        createPlayerService().upsert(new Player(playerId, playerHp, playerAttack));
        return null;
    }
}
