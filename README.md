# Scalar DB example

A very simple CLI application to use Scalar DB.

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
$ build/install/scalardb-example/bin/scalardb-example -h
Usage: player-battle [-hV] [--attack=<playerAttack>] -c=<path>
                     [--hp=<playerHp>] --id=<playerId> [--other-id=<otherId>]
                     <commandParam>
Just a sample application for Scalar DB
      <commandParam>
      --attack=<playerAttack>
                             Player attack
  -c, --config=<path>        Scalar DB config file
  -h, --help                 Show this help message and exit.
      --hp=<playerHp>        Player HP
      --id=<playerId>        Player ID
      --other-id=<otherId>   Other player's ID
  -V, --version              Print version information and exit.
```

### Create a player

```
$ build/install/scalardb-example/bin/scalardb-example --config scalardb.properties create --id alice --hp 100 --attack 15
$ build/install/scalardb-example/bin/scalardb-example --config scalardb.properties create --id bob --hp 200 --attack 10
```

### Show a player
```
$ build/install/scalardb-example/bin/scalardb-example --config scalardb.properties get --id alice
Optional[Player[id=alice, hp=100, attack=15]]
$ build/install/scalardb-example/bin/scalardb-example --config scalardb.properties get --id bob
Optional[Player[id=bob, hp=200, attack=10]]
```

### Attack another player
```
$ build/install/scalardb-example/bin/scalardb-example --config scalardb.properties attack --id alice --other-id bob
$ build/install/scalardb-example/bin/scalardb-example --config scalardb.properties get --id bob
Optional[Player[id=bob, hp=185, attack=10]]
```

