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

## Native Docker Image

Wenn du GraalVM 20 installiert hast, kannst du ein Natives-Binary erstellen mit: `./gradlew build -Dquarkus.package.type=native`.

Hast du keine GraalVM installiert, kannst du die GraalVM auch einfach über Docker verwenden lassen: `./gradlew build -Dquarkus.package.type=native -Dquarkus.native.container-build=true`.

Das Binary startest du dann einfach mit folgendem Befehl (nur Linux): `./build/ch.romix.mpa-1.0.0-SNAPSHOT-runner`

Mehr über Native Images von Quarkus findest du hier: https://quarkus.io/guides/gradle-tooling#building-a-native-executable.

Mit dem Native Image kannst du nun auch ganz leicht ein Docker Image erstellen. Dazu gibt es bereits ein vorbereitetes Dockerfile:
`docker build -f src/main/docker/Dockerfile.native -t romixch/ch.romix.mpa .`

## Java Docker Image
Nicht alle Java-Libraries laufen auch in einem Native Image out of the box. Als Java-Anwendung
startet Quarkus aber auch respektabel schnell. Um ein Java Docker Image zu erhalten, gehst du wie folgt vor:

Baue deine Anwendung: `./gradlew quarkusBuild`. Nun liegt ein `ch.romix.mpa-1.0.0-SNAPSHOT-runner.jar` im Ordner `build`. Die Abhängigkeiten landen im Verzeichnis `build/lib`.

Du kannst die Anwendung nun starten mit `java -jar build/ch.romix.mpa-1.0.0-SNAPSHOT-runner.jar`.

Damit du ein Docker Image erhälst, kannst du wiederum das vorgefertigte Dockerfile verwenden: `docker build -f src/main/docker/Dockerfile.jvm -t romixch/ch.romix.mpa .`

## Deployment

Ein kleines Skript zum Builden und Deployen in die Google Cloud ist in `deploy.sh`.