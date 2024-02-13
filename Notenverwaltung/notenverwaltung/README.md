# Mein JavaFX Projekt
Diese Projekt wurde mit dem Maven Archetype "hftm-archetype-javafx-basic" erstellt.

Bevor du die Maven-Befehle unten ausführst, solltest du prüfen, dass im pom.xml das Property project.mainclass korrekt angegeben ist!  

## Start des Projekts aus Entwicklungsumgebung  
Du kannst deine MainApp-Klasse direkt über die IDE starten oder folgenden Maven-Befehl ausführen:

    mvn javafx:run

  
## Erzeugen einer Auslieferung
Du kannst dein Projekt mit folgendem Befehl in ein auslieferbares Programm packen:  

    mvn javafx:jlink

Anschliessend befindet sich dein aulieferbares Programm unter ``target/App`` und ein bereits verpacktes ZIP-Archiv unter ``target/AppArchive.zip``. Beide enthalten die gleichen Dateien:
-  Eine passende Java-Auslieferung und alle notwendigen Libraries.
-  Im Verzeichnis ``bin`` findest du ein ausführbares File mit dem Namen ``Start`` (z.B. ``Start.bat``). Damit kannst du dein Programm auf jedem Computer mit dem gleichen Betriebsystem starten.  

Beispielhaftes Starten des erzeugten Programms unter Windows:

    call target\App\bin\Start.bat