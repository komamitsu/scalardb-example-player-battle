package org.komamitsu.playerbattle.commands;

import picocli.CommandLine;

import java.nio.file.Path;

@CommandLine.Command(
        name = "player-battle",
        mixinStandardHelpOptions = true,
        description = "Just a sample application for Scalar DB",
        subcommands = {
                Create.class,
                Delete.class,
                Show.class,
                Attack.class,
                Bonus.class
        }
)
public class Cli {
    @CommandLine.Option(
            names = {"-c", "--config"},
            description = "Scalar DB config file",
            required = true
    )
    protected Path path;
}
