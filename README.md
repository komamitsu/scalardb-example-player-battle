# Scalar DB example

A very small simple CLI application to use Scalar DB. This application supports some commands to create a player and make a player attack another player and etc.

## Build

```
$ ./gradlew installDist
```

## Database schema setup

This tool uses schema-loader. Build schema-loader in advance.

```
$ SCHEMA_LOADER_JAR_PATH=$HOME/src/scalardb/schema-loader/build/libs/scalardb-schema-loader-4.0.0-SNAPSHOT.jar ./setup.sh 
```

## Execute command

```
$ cd build/install/scalardb-example-player-battle/
$ bin/scalardb-example-player-battle -h
Usage: player-battle [-hV] [--attack=<playerAttack>] [--bonus=<bonus>]
                     -c=<path> [--hp=<playerHp>] --id=<playerId>
                     [--other-id=<otherId>] [--threshold=<bonusThreshold>]
                     <commandParam>
Just a sample application for Scalar DB
      <commandParam>         create|delete|get|attack|bonus
      --attack=<playerAttack>
                             Player attack
      --bonus=<bonus>        Bonus amount
  -c, --config=<path>        Scalar DB config file
  -h, --help                 Show this help message and exit.
      --hp=<playerHp>        Player HP
      --id=<playerId>        Player ID
      --other-id=<otherId>   Other player's ID
      --threshold=<bonusThreshold>
                             The target player can get a bonus if the total HP
                               is less than or equal to the threshold
  -V, --version              Print version information and exit.
```

### Create a player

```
$ cd build/install/scalardb-example-player-battle/
$ bin/scalardb-example-player-battle --config ../../../scalardb.properties create --id alice --hp 100 --attack 15
$ bin/scalardb-example-player-battle --config ../../../scalardb.properties get --id alice
Optional[Player[id=alice, hp=100, attack=15]]
```

### Delete a player
```
$ cd build/install/scalardb-example-player-battle/
$ bin/scalardb-example-player-battle --config ../../../scalardb.properties delete --id alice
$ bin/scalardb-example-player-battle --config ../../../scalardb.properties get --id alice
Optional.empty
```

### Show a player
```
$ cd build/install/scalardb-example-player-battle/
$ bin/scalardb-example-player-battle --config ../../../scalardb.properties get --id alice
Optional[Player[id=alice, hp=100, attack=15]]
$ bin/scalardb-example-player-battle --config ../../../scalardb.properties get --id bob
Optional[Player[id=bob, hp=200, attack=10]]
```

### Attack another player
```
$ cd build/install/scalardb-example-player-battle/
$ bin/scalardb-example-player-battle --config ../../../scalardb.properties attack --id alice --other-id bob
$ bin/scalardb-example-player-battle --config ../../../scalardb.properties get --id bob
Optional[Player[id=bob, hp=185, attack=10]]
```

### Provide a bonus HP to a player if the total HP of the target player and another is less than or equal to the threshold
```
$ cd build/install/scalardb-example-player-battle/
$ bin/scalardb-example-player-battle --config ../../../scalardb.properties get --id alice
Optional[Player[id=alice, hp=100, attack=15]]
$ bin/scalardb-example-player-battle --config ../../../scalardb.properties bonus --id alice --other-id bob --threshold 300 --bonus 100
$ bin/scalardb-example-player-battle --config ../../../scalardb.properties get --id alice
Optional[Player[id=alice, hp=200, attack=15]]
```

## Integration testing

```
$ SCALARDB_EXAMPLE_IT_CONFIG_PATH=scalardb.properties SCALARDB_EXAMPLE_IT_SCHEMA_CONFIG_PATH=player-battle.json ./gradlew integrationTest --info
```

## TODO

- CI

