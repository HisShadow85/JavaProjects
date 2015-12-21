package common;

public class Synchro {

	static final public int[][] winPositions = new int[][] { { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }, { 0, 3, 6 },
			{ 1, 4, 7 }, { 2, 5, 8 }, { 0, 4, 8 }, { 2, 4, 6 } };

	private boolean playerTwoTurn = false;
	private byte usedFields = 0;
	private char[] boardTable = new char[9];

	public static enum won {
		non, playerOneWon, playerTwoWon, tie
	};

	private won winner = won.non;

	//////////////////////////////////////
	private boolean isGame = false;// decide if the game is already started
	private boolean isHotSeat = false;
	private boolean playerOneTurn = false;
	
	char setedChar;
	byte setCell;

	public synchronized char getSetedChar() {
		return setedChar;
	}

	public synchronized void setSetedChar(char setedChar) {
		this.setedChar = setedChar;
	}
	
	public synchronized boolean isPlayerTwoTurn() {
		return playerTwoTurn;
	}

	public synchronized void setPlayerTwoTurn(boolean playerTwoTurn) {
		this.playerTwoTurn = playerTwoTurn;
	}

	public synchronized boolean isPlayerOneTurn() {
		return playerOneTurn;
	}

	public synchronized void setPlayerOneTurn(boolean playerOneTurn) {
		this.playerOneTurn = playerOneTurn;
	}

	public synchronized boolean isHotSeat() {
		return isHotSeat;
	}

	public synchronized void setHotSeat(boolean isHotSeat) {
		this.isHotSeat = isHotSeat;
	}

	public synchronized boolean isGame() {
		return isGame;
	}

	public synchronized void setGame(boolean isGame) {
		this.isGame = isGame;
		this.setDefautBoard();
		this.isHotSeat = false;
		this.playerOneTurn = false;
		this.playerTwoTurn = false;
		this.setedChar = '\0';
		this.usedFields = 0;
		this.winner = won.non;
	}

	public synchronized won getGameWinStatus() {
		return winner;
	}

	public synchronized void setGameWinStatus(won state) {
		winner = state;
	}

	public synchronized char getBoardSymbol(int index) {
		return boardTable[index];
	}

	public synchronized void setBoardSymbol(int index, char ch) {
		if (ch == '\0' && boardTable[index] == '\0') {
			return;
		} else if ((ch == 'X' || ch == 'O') && boardTable[index] == '\0') {
			boardTable[index] = ch;
			usedFields++;
			setCell = (byte) index;
		} else if (ch == '\0' && boardTable[index] != '\0') {
			boardTable[index] = ch;
			usedFields--;
			setCell = (byte) index;
		}
		setedChar = ch;

	}

	public synchronized byte getSetCell() {
		return setCell;
	}

	public synchronized void setSetCell(byte setCell) {
		this.setCell = setCell;
	}

	public synchronized int getUsedFields() {
		return usedFields;
	}



	public synchronized void switchPlayers() {
		if (playerOneTurn) {
			playerOneTurn = false;
			playerTwoTurn = true;
		} else {
			playerTwoTurn = false;
			playerOneTurn = true;
		}
	}

	public synchronized void setDefautBoard() {
		for (int i = 0; i < boardTable.length; i++) {
			boardTable[i] = '\0';
		}
	}

	public synchronized void updateState(char side) {
		if (side != 'O' && side != 'X') {
			return;
		}

		for (int i = 0; i < winPositions.length; i++) {

			if (boardTable[winPositions[i][0]] == side && boardTable[winPositions[i][1]] == side
					&& boardTable[winPositions[i][2]] == side) {
				winner = side == 'O' ? won.playerTwoWon : won.playerOneWon;
			}
		}

		if (winner == won.non) {
			if (usedFields == 9) {
				winner = won.tie;
			}
		}
	}

	public synchronized won getCurrStateNoUpdate(char side) {
		if (side != 'O' && side != 'X') {
			return won.non;
		}

		for (int i = 0; i < winPositions.length; i++) {

			if (boardTable[winPositions[i][0]] == side && boardTable[winPositions[i][1]] == side
					&& boardTable[winPositions[i][2]] == side) {
				return side == 'O' ? won.playerTwoWon : won.playerOneWon;
			}
		}

		if (winner == won.non) {
			if (usedFields == 9) {
				return won.tie;
			}
		}
		return won.non;
	}

}
