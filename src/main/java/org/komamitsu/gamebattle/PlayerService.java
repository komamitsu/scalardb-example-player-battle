package org.komamitsu.gamebattle;

import com.scalar.db.api.*;
import com.scalar.db.exception.transaction.CrudException;
import com.scalar.db.exception.transaction.TransactionException;
import com.scalar.db.io.Key;

import java.util.Optional;

public class PlayerService {
    private static final String NAMESPACE = "game";
    private static final String TABLE_NAME = "players";
    private static final String KEY_ID = "id";
    private static final String KEY_HP = "hp";
    private static final String KEY_ATTACK = "attack";

    private final DistributedTransactionManager transactionManager;

    public PlayerService(DistributedTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    private Optional<Player> getInTx(DistributedTransaction tx, String id) throws CrudException {
        return tx.get(
                Get.newBuilder()
                        .namespace(NAMESPACE)
                        .table(TABLE_NAME)
                        .partitionKey(Key.ofText(KEY_ID, id))
                        .build()).map(result ->
                new Player(
                        result.getText(KEY_ID),
                        result.getInt(KEY_HP),
                        result.getInt(KEY_ATTACK)));
    }

    private void putInTx(DistributedTransaction tx, Player player) throws TransactionException {
        PutBuilder.Buildable builder = Put.newBuilder()
                .namespace(NAMESPACE)
                .table(TABLE_NAME)
                .partitionKey(Key.ofText(KEY_ID, player.id()));

        // To avoid blind writes
        Optional<Player> old = getInTx(tx, player.id());
        if (old.isEmpty() || old.get().hp() != player.hp()) {
            builder.intValue(KEY_HP, player.hp());
        }
        if (old.isEmpty() || old.get().attack() != player.attack()) {
            builder.intValue(KEY_ATTACK, player.attack());
        }

        tx.put(builder.build());
    }

    public void upsert(Player player) throws TransactionException {
        DistributedTransaction tx = transactionManager.start();

        try {
            // To avoid blind writes
            getInTx(tx, player.id());

            // Reduce the HP of the other
            putInTx(tx, player);

            tx.commit();
        } catch (Throwable e) {
            tx.abort();
            throw new PlayerServiceException(e, player.id());
        }
    }

    public Optional<Player> get(String id) throws TransactionException {
        DistributedTransaction tx = transactionManager.start();

        try {
            Optional<Player> optResult = getInTx(tx, id);

            tx.commit();

            return optResult;
        } catch (Throwable e) {
            tx.abort();
            throw new PlayerServiceException(e, id);
        }
    }

    public void attack(String playerId, String otherId) throws TransactionException {
        DistributedTransaction tx = transactionManager.start();

        try {
            Player player = getInTx(tx, playerId).get();
            Player other = getInTx(tx, otherId).get();

            // Reduce the HP of the other
            putInTx(tx, new Player(other.id(), other.hp() - player.attack(), other.attack()));

            tx.commit();
        } catch (Throwable e) {
            tx.abort();
            throw new PlayerServiceException(e, playerId);
        }
    }
}
