package org.komamitsu.playerbattle;

import com.scalar.db.exception.transaction.TransactionException;
import com.scalar.db.schemaloader.SchemaLoader;
import com.scalar.db.schemaloader.SchemaLoaderException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

public class IntTestMain {
    private String configPath;
    private String schemaConfigPath;

    @BeforeEach
    void setUp() throws SchemaLoaderException {
        configPath = System.getenv("SCALARDB_EXAMPLE_IT_CONFIG_PATH");
        Assumptions.assumeTrue(configPath != null);
        schemaConfigPath = System.getenv("SCALARDB_EXAMPLE_IT_SCHEMA_CONFIG_PATH");
        Assumptions.assumeTrue(schemaConfigPath != null);

        SchemaLoader.unload(Paths.get(configPath), Paths.get(schemaConfigPath), true);
        SchemaLoader.load(Paths.get(configPath), Paths.get(schemaConfigPath), Collections.emptyMap(), true);
    }

    private void withStdoutCapture(Runnable proc, Consumer<String> validator) throws Exception {
        PrintStream origStdout = System.out;

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); PrintStream ps = new PrintStream(bos)) {
            System.setOut(ps);
            proc.run();
            ps.flush();
            System.setOut(origStdout);
            validator.accept(bos.toString());
        }
        finally {
            System.setOut(origStdout);
        }
    }

    private void runCli(String[] args) {
        try {
            Main.main(args);
        } catch (IOException | TransactionException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getButNotFound() throws Exception {
        withStdoutCapture(
                () -> runCli(new String[] {"--config", configPath, "get", "--id", "alice"}),
                (result) -> Assertions.assertEquals("Optional.empty", result.strip())
        );
    }

    @Test
    void create() throws Exception {
        runCli(new String[] {"--config", configPath, "create", "--id", "alice", "--hp", "100", "--attack", "15"});
        withStdoutCapture(
                () -> runCli(new String[] {"--config", configPath, "get", "--id", "alice"}),
                (result) -> Assertions.assertEquals("Optional[Player[id=alice, hp=100, attack=15]]", result.strip())
        );
    }

    @Test
    void attack() throws Exception {
        runCli(new String[] {"--config", configPath, "create", "--id", "alice", "--hp", "100", "--attack", "15"});
        runCli(new String[] {"--config", configPath, "create", "--id", "bob", "--hp", "200", "--attack", "8"});
        runCli(new String[] {"--config", configPath, "attack", "--id", "alice", "--other-id", "bob"});
        withStdoutCapture(
                () -> runCli(new String[] {"--config", configPath, "get", "--id", "bob"}),
                (result) -> Assertions.assertEquals("Optional[Player[id=bob, hp=185, attack=8]]", result.strip())
        );
    }
}
