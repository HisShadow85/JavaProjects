package logic;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JTextArea;

import common.Synchro;
import common.Synchro.won;
import view.Board;

public abstract class MultyPlayer extends GameThread{
	protected Socket socket;
	protected DataOutputStream dos;
	protected DataInputStream dis;
	
	public MultyPlayer(Synchro syn, Board board, JTextArea messages){
		super(syn,board,messages);
	}
	
	public boolean yourTurn() {
		syn.setPlayerOneTurn(true);
		while (syn.isPlayerOneTurn())
			;
		if (!syn.isGame()) {
			return false;
		}
		
		for (counter = 0; counter < 10; counter++) {
			try {
				dos.writeInt(syn.getSetCell());
				break;
			} catch (IOException e) {
				if (counter == 9) {
					messages.setText("Unable To Communicate With Opponent");
					syn.setGame(false);
					return false;
				}
			}
		}
		
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

	public boolean opponentMove() {
		syn.setPlayerTwoTurn(true);
		for (counter = 0; counter < 10; counter++) {
			try {
				int cell = dis.readInt();

				if (syn.getBoardSymbol(cell) != '\0') {
					if (counter == 9) {
						messages.setText("Unsynchronized communication!");
						return false;
					}
					continue;
				}
				if (syn.isGame()) {
					syn.setBoardSymbol(cell, 'O');
					board.setButton(cell, 'O');
					syn.updateState('O');

					if (syn.getGameWinStatus() == won.playerTwoWon) {
						messages.setText("Opponent win");
						syn.setGame(false);
						return false;
					} else if (syn.getGameWinStatus() == won.tie) {
						messages.setText("Game Tie");
						syn.setGame(false);
						return false;
					} else {
						break;
					}
				}
				return false;
			} catch (IOException e) {
				if (counter == 9) {
					messages.setText("Unable To Communicate With Opponent");
					syn.setGame(false);
					return false;
				}
			}
		}

		syn.setPlayerTwoTurn(false);
		return true;
	}

	protected void destructor() {
		try {
			if (socket != null) {
				socket.close();
			}
			if (dos != null) {
				dos.close();
			}
			if (dis != null) {
				dis.close();
			}
		} catch (Exception e) {
			// :(
		}
	}
	
	@Override
	public void terminate(){
		super.terminate();
		this.destructor();
	}
	
	@Override
	public void finalize(){
		destructor();
	}
}
