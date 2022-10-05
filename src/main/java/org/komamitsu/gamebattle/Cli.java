package org.komamitsu.gamebattle;

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

    record AttackCommand(String id, String otherId) implements Command {}

    public Command command;

    @CommandLine.Parameters(index = "0") String commandParam;

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
            case "get" -> command = new GetCommand(playerId);
            case "attack" -> command = new AttackCommand(
                    playerId,
                    notNull("otherId", otherId));
            default -> throw new IllegalArgumentException("Unexpected command: " + commandParam);
        }
        return null;
    }
}
