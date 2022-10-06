package org.komamitsu.playerbattle;

import picocli.CommandLine;

import java.nio.file.Path;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "player-battle", mixinStandardHelpOptions = true,
        description = "Just a sample application for Scalar DB")
public class Cli implements Callable<Void> {

    interface Command {
    }

    record CreateCommand(String id, int hp, int attack) implements Command {}

    record GetCommand(String id) implements Command {}

    record DeleteCommand(String id) implements Command {}

    record AttackCommand(String id, String otherId) implements Command {}

    record BonusCommand(String id, String otherId, int threshold, int bonus) implements Command {}

    public Command command;

    @CommandLine.Parameters(index = "0", description = "create|delete|get|attack|bonus") String commandParam;

    @CommandLine.Option(names = {"-c", "--config"}, description = "Scalar DB config file", required = true)
    Path path;

    @CommandLine.Option(names = {"--id"}, description = "Player ID", required = true)
    String playerId;

    @CommandLine.Option(names = {"--hp"}, description = "Player HP")
    Integer playerHp;

    @CommandLine.Option(names = {"--attack"}, description = "Player attack")
    Integer playerAttack;

    @CommandLine.Option(names = {"--other-id"}, description = "Other player's ID")
    String otherId;

    @CommandLine.Option(names = {"--threshold"}, description = "The target player can get a bonus if the total HP is less than or equal to the threshold")
    Integer bonusThreshold;

    @CommandLine.Option(names = {"--bonus"}, description = "Bonus amount")
    Integer bonus;


    private <T> T notNull(String label, T value) {
        if (value == null) {
            throw new IllegalArgumentException(String.format("'%s' shouldn't be null", label));
        }
        return value;
    }

    @Override
    public Void call() {
        switch (commandParam) {
            case "create" -> command = new CreateCommand(
                    playerId,
                    notNull("hp", playerHp),
                    notNull("attack", playerAttack));
            case "delete" -> command = new DeleteCommand(playerId);
            case "get" -> command = new GetCommand(playerId);
            case "attack" -> command = new AttackCommand(
                    playerId,
                    notNull("otherId", otherId));
            case "bonus" -> command = new BonusCommand(
                    playerId,
                    notNull("otherId", otherId),
                    notNull("bonusThreshold", bonusThreshold),
                    notNull("bonus", bonus));
            default -> throw new IllegalArgumentException("Unexpected command: " + commandParam);
        }
        return null;
    }
}
