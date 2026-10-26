# Mine

My recreation of the gambling game **Mines** from Stake.com, built with JavaFX and SceneBuilder in Eclipse. Play money only.

## Tech
- Java 21
- JavaFX 23 (UI built with SceneBuilder)

## How to run
You need **JDK 21+** and the **[JavaFX SDK 23](https://gluonhq.com/products/javafx/)**.

**Eclipse (how it was built):** create a JavaFX project, put the files in the `application` package, and run `Main.java`.

**Command line:**
```bash
# PATH_TO_FX = the lib folder of your JavaFX SDK
javac --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml -d out *.java
cp *.fxml out/application/ && touch out/application/application.css
java --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml -cp out application.Main
```
The app loads an `application.css` stylesheet, which Eclipse's JavaFX template normally creates. If you don't have one, an empty file works.

Keep `secret.key` and `Money.txt` in the folder you run from, because that's where your encrypted wallet is stored.

## How to play
1. Set your **bet** and the number of **bombs** with the sliders.
2. Press **Play**, then click tiles to reveal gems. Avoid the bombs!
3. Press **Cashout** to bank your winnings, or **Pick Random** to have a tile picked for you (bigger payout).

## Features
- You start with **$100k**. Your wallet is saved to an encrypted file after every cashout or bomb, or when you press **SAVE**.
- The multiplier depends on the ratio of bombs to gems and goes up as you open more gems.
- **Profit** shows how far up or down you are, including your bet.
- Sliders and buttons are hidden during a round and come back after it ends.
- **Admin** (debug tool, needs a passcode typed in the console) reveals the bombs and adds money.
- If you quit mid-round, that round's progress isn't saved.
