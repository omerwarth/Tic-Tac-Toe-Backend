# Compiles the appropriate .java files into .class files
default: cpsc2150/extendedConnects/GameScreen.java cpsc2150/extendedConnectX/models/BoardPosition.java cpsc2150/extendedConnectX/models/IGameBoard.java cpsc2150/extendedConnectX/models/AbsGameBoard.java cpsc2150/extendedConnectX/models/GameBoard.java cpsc2150/extendedConnectX/models/GameBoardMem.java
	javac cpsc2150/extendedConnectX/models/BoardPosition.java
	javac cpsc2150/extendedConnectX/models/IGameBoard.java
	javac cpsc2150/extendedConnectX/models/AbsGameBoard.java
	javac cpsc2150/extendedConnectX/models/GameBoard.java
	javac cpsc2150/extendedConnectX/models/GameBoardMem.java
	javac cpsc2150/extendedConnects/GameScreen.java

# Runs the compiled program at the command line
run: cpsc2150/extendedConnects/GameScreen.class cpsc2150/extendedConnectX/models/BoardPosition.class cpsc2150/extendedConnectX/models/IGameBoard.class cpsc2150/extendedConnectX/models/AbsGameBoard.class cpsc2150/extendedConnectX/models/GameBoard.class cpsc2150/extendedConnectX/models/GameBoardMem.class
	java cpsc2150.extendedConnects.GameScreen

# Compiles the appropriate .java files for running JUnit tests into .class files
test: cpsc2150/extendedConnectX/tests/TestGameBoard.java cpsc2150/extendedConnectX/tests/TestGameBoardMem.java cpsc2150/extendedConnectX/models/BoardPosition.java cpsc2150/extendedConnectX/models/IGameBoard.java cpsc2150/extendedConnectX/models/AbsGameBoard.java cpsc2150/extendedConnectX/models/GameBoard.java cpsc2150/extendedConnectX/models/GameBoardMem.java
	javac -cp .:/usr/share/java/junit4.jar cpsc2150/extendedConnectX/tests/TestGameBoard.java cpsc2150/extendedConnectX/tests/TestGameBoardMem.java cpsc2150/extendedConnectX/models/BoardPosition.java cpsc2150/extendedConnectX/models/IGameBoard.java cpsc2150/extendedConnectX/models/AbsGameBoard.java cpsc2150/extendedConnectX/models/GameBoard.java cpsc2150/extendedConnectX/models/GameBoardMem.java

# Runs the JUnit tests for GameBoard.java
testGB: cpsc2150/extendedConnectX/tests/TestGameBoard.class cpsc2150/extendedConnectX/models/BoardPosition.class cpsc2150/extendedConnectX/models/IGameBoard.class cpsc2150/extendedConnectX/models/AbsGameBoard.class cpsc2150/extendedConnectX/models/GameBoard.class cpsc2150/extendedConnectX/models/GameBoardMem.class
	java -cp .:/usr/share/java/junit4.jar org.junit.runner.JUnitCore cpsc2150.extendedConnectX.tests.TestGameBoard

# Runs the JUnit tests for GameBoardMem.java
testGBmem: cpsc2150/extendedConnectX/tests/TestGameBoardMem.class cpsc2150/extendedConnectX/models/BoardPosition.class cpsc2150/extendedConnectX/models/IGameBoard.class cpsc2150/extendedConnectX/models/AbsGameBoard.class cpsc2150/extendedConnectX/models/GameBoard.class cpsc2150/extendedConnectX/models/GameBoardMem.class
	java -cp .:/usr/share/java/junit4.jar org.junit.runner.JUnitCore cpsc2150.extendedConnectX.tests.TestGameBoardMem

# Cleans out the directory by removing the compiled .class files
clean:
	rm -f cpsc2150/extendedConnectX/models/*.class
	rm -f cpsc2150/extendedConnectX/tests/*.class
	rm -f cpsc2150/extendedConnects/*.class