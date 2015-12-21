package logic;

import javax.swing.JTextArea;

import ai.Minimax;
import common.Synchro;
import common.Synchro.won;
import view.Board;

public class SinglePlay extends GameThread {
	final static byte MAX_ALPHA = 100;
	final static byte MIN_BETA = -100;
	private int cpuMove;
	private byte score;
	private byte tempScore;
	

	public SinglePlay(Synchro syn, Board board, JTextArea messages) {
		super(syn, board, messages);
		this.start();
	}

	public void run() {
		messages.setText("Single Play");
		syn.setGame(true);
		while (!threadSuspended && syn.isGame()) {
			if (!threadSuspended) {
				if (!humanMove()) {
					break;
				}
			}
			if (!threadSuspended) {
				if (!computerMove()) {
					break;
				}
			}
		}
	}

	public boolean humanMove() {
		syn.setPlayerOneTurn(true);
		while (syn.isPlayerOneTurn())
			;
		syn.updateState('X');
		if (syn.getGameWinStatus() == won.playerOneWon) {
			messages.setText("You win");
			syn.setGame(false);
			return false;
		} else if (syn.getGameWinStatus() == won.tie) {
			messages.setText("Game Tie");
			syn.setGame(false);
			return false;
		}
		return true;
	}

	public boolean computerMove() {
		syn.setPlayerTwoTurn(true);
		score = MIN_BETA - 2;
		cpuMove = -1;
		for (counter = 0;counter < 9; ++counter) {
			if (syn.getBoardSymbol(counter) == '\0') {
				syn.setBoardSymbol(counter, 'O');
				tempScore = Minimax.minimax((byte) -1,(byte)-100,(byte)100,syn);
				syn.setBoardSymbol(counter, '\0');
				if (tempScore > score) {
					score = tempScore;
					cpuMove = counter;
				}
			}
		}
		
		syn.setPlayerTwoTurn(false);
		if (cpuMove >= 0 && cpuMove < 10) {
			if (syn.isGame()) {
				syn.setBoardSymbol(cpuMove, 'O');
				board.setButton(cpuMove, 'O');
				syn.updateState('O');

				if (syn.getGameWinStatus() == won.playerTwoWon) {
					messages.setText("Computer win");
					syn.setGame(false);
					return false;
				} else if (syn.getGameWinStatus() == won.tie) {
					messages.setText("Game Tie");
					syn.setGame(false);
					return false;
				} else {
					return true;
				}
			}
		}
		return false;
	}	
}
