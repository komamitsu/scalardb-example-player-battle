package org.komamitsu.playerbattle.commands;

import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
    name = "bonus",
    description = "Give a bonus HP if the total HP of the target player and another specified player is less than or equal to the threshold"
)
public class Bonus extends Base implements Callable<Void> {
    @CommandLine.Option(names = {"--id"}, description = "Player ID", required = true)
    String playerId;

    @CommandLine.Option(names = {"--other-id"}, description = "Player HP", required = true)
    String otherId;

    @CommandLine.Option(names = {"--threshold"}, description = "Threshold used to decide if the target player can get a bonus", required = true)
    Integer threshold;

    @CommandLine.Option(names = {"--bonus"}, description = "Bonus HP", required = true)
    Integer bonus;

    @Override
    public Void call() throws Exception {
        createPlayerService().bonus(playerId, otherId, threshold, bonus);
        return null;
    }
}
