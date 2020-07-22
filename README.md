# Multi Page Applications

## Für eine simple Web-Entwicklung

Dieses Beispiel-Projekt soll zeigen, wie auch jenseits von React, Angular und Vue ganz simple und 
trotzdem moderne Web-Applikationen entwickelt werden können.

Die Web-App läuft unter https://mpa.romix.ch/. Dort findest du auch mehr zu diesem Thema.  

## Anwendung starten

Die Anwendung ist in Java auf Quarkus geschrieben. Mit folgendem Befehl kannst du den Dev-Server
starten. Die Anwendung startet automatisch neu, wenn du den Code änderst. So, wie du das vielleicht 
aus NodeJS-Anwendungen kennst.

```
./gradlew quarkusDev
```

## Packaging

So erstellst du ein Jar-File der Anwendung: `./gradlew quarkusBuild`.
Es erstellt `ch.romix.mpa-1.0.0-SNAPSHOT-runner.jar` im Ordner `build`.
Dies ist kein _über-jar_. Die Abhängigkeiten landen im Verzeichnis `build/lib`.

Du kannst die Anwendung nun starten mit `java -jar build/ch.romix.mpa-1.0.0-SNAPSHOT-runner.jar`.

Um ein über-jar zu erstellen, füge die Option `--uber-jar` hinzu:
```
./gradlew quarkusBuild --uber-jar
```

## Ein Native Image erstellen

Wenn du GraalVM 19 installiert hast, kannst du ein Natives-Binary erstellen mit: `./gradlew build -Dquarkus.package.type=native`.

Hast du keine GraalVM installiert, kannst du die GraalVM auch einfach über Docker verwenden lassen: `./gradlew build -Dquarkus.package.type=native -Dquarkus.native.container-build=true`.

Das Binary startest du dann einfach mit folgendem Befehl (nur Linux): `./build/ch.romix.mpa-1.0.0-SNAPSHOT-runner`

Mehr über Native Images von Quarkus findest du hier: https://quarkus.io/guides/gradle-tooling#building-a-native-executable.

## Deployment

Ein kleines Skript zum Builden und Deployen in die Google Cloud ist in `deploy.sh`.