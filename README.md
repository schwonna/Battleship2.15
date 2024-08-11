# Battleship2.15
## Kurzbeschreibung
Diese Battleship Version ist eine rundenbasierte Singleplayer Anwendung, wobei der Spieler gegen den Computer spielt.
Das Spielfeld ist ein Quadrat aus mehreren kleinen Quadranten, welche das Gewässer darstellen. Innerhalb dieser Quadranten platziert der Spieler seine Schiffe. Diese Schiffe sind verschieden groß.
Der Computer platziert seine Schiffe zufällig.

Spieler und Computer können pro Runde jeweils eine Kanone abfeuern und setzen Markierungen ob ein feindliches Schiff getroffen wurde oder nicht.

Gewinner ist, wer alle gegnerischen Schiffe zuerst zerstört hat.

## verwendete Bibliotheken
- [Processing](www.processing.org)
- [JUnit](https://junit.org/junit5/)
- [gifAnimation](https://github.com/extrapixel/gif-animation?tab=readme-ov-file)

## Bildquellen

- [shootingShip GIF](https://i.gifer.com/origin/a3/a33d510a2fca8a350f2aa5ff28e43cb2.gif)
- [startscreen image](https://i.imgur.com/zKQRu.png)

## Screenshots
Start-Bildschirm:
![screenshot1_start.png](screenshots%2Fscreenshot1_start.png)

Schiffplatzierungs-Bildschirm:
![screenshot2_PLACE_SHIPS.png](screenshots%2Fscreenshot2_PLACE_SHIPS.png)

Spiel-Bildschirm:
![screenshot3_PLAYING.png](screenshots%2Fscreenshot3_PLAYING.png)

End-Bildschirm:
![screenshot4_GAME_OVER.png](screenshots%2Fscreenshot4_GAME_OVER.png)

## Startanleitung
Zum *Starten* der Anwendung müssen folgende Schritte befolgt werden:

1. Öffnen der Datei `Main.java` im `src` Ordner.
2. Starten der Funktion `main()`
## Jshell Anleitung

Um die Anwendung in der JShell zu starten müssen folgende
Schritte befolgt werden:

1. **Verzeichnis:**

   Zum richtigen Verzeichnis navigieren, in der sich die Anwendung befindet. Bsp:

         (base) johanna@pool-10-192-245-173 PiS_Projekte % cd Battleship


2. **class Pfad:**

   Die jshell starten und den richtigen Pfad öffnen, Bsp
   für eine Eingabe in der Konsole:

   johanna@pool-10-192-245-173 Battleship2 %
*jshell --class-path ./out/production/Battleship*



3. **Pfad zum Model bzw. das package importieren:**

   jshell> *import battleship.model.Model*


4. **Objekt des Models erzeugen:**

   jshell> *var game = new Model()*
   <p>
   game ==> battleship.model.Model@4edde6e5


5. **Funktionen anwenden:**

* Example for JShell:
*
* jshell --class-path ./out/production/Battleship
* jshell> import battleship.model.Model
*
* jshell> var game = new Model()
* Welcome to Battleship!
* Player Ships:
*
*   1 2 3 4 5 6 7 8 9 10
* A
* B
* C
* D
* E
* F
* G
* H
* I
* J
*
*
* Enemy Ships:
*
*   1 2 3 4 5 6 7 8 9 10
* A
* B
* C
* D
* E
* F
* G
* H
* I
* J
*
* jshell> game.buildShip('B',2)
* Ship Placement: current ship length: 5

*
* Player Ships:
*
*   1 2 3 4 5 6 7 8 9 10
* A
* B   S S S S S
* C
* D
* E
* F
* G
* H
* I
* J
*
* jshell> game.setVerticalDirection()
*
* jshell> game.getDirection()
* $5 ==> 1
*
* jshell> game.buildShip('D',8)
* Ship Placement: current ship length: 4
*
* Player Ships:
*
*   1 2 3 4 5 6 7 8 9 10
* A
* B   S S S S S
* C
* D               S
* E               S
* F               S
* G               S
* H
* I
* J
*
* jshell> game.getInvPlayerShipsOnCompGrid()
* $8 ==>   1 2 3 4 5 6 7 8 9 10
* A
* B   S S S S S
* C
* D               S
* E               S
* F               S
* G               S
* H
* I
* J
*
*
* jshell> game.playershoot('B',2)
* Your shot was MISSED.
* 
* Player Ships:
*
*   1 2 3 4 5 6 7 8 9 10
* A
* B   S S S S S
* C
* D               S
* E               S
* F               S
* G               S
* H
* I
* J
*
*
* Enemy Ships:
*
*   1 2 3 4 5 6 7 8 9 10
* A
* B   W
* C
* D
* E
* F
* G
* H
* I
* J
*
* jshell> game.computerShoot()
* Computer's turn to shoot.
  Computer shot at: H 1
  Computer shot was MISSED.
*
* Player Ships:
*
*   1 2 3 4 5 6 7 8 9 10
* A
* B   S S S S S
* C
* D               S
* E               S
* F               S
* G               S
* H W
* I
* J
*
*
* Enemy Ships:
*
*   1 2 3 4 5 6 7 8 9 10
* A
* B   W
* C
* D
* E
* F
* G
* H
* I
* J
