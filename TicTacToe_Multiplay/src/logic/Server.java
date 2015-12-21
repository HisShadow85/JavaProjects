package logic;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;

import javax.swing.JTextArea;

import common.Synchro;
import view.Board;

public class Server extends MultyPlayer {

	private final ServerSocket serverSocket;

	public Server(Synchro syn, ServerSocket serverSocket, Board board, JTextArea messages) {
		super(syn, board, messages);
		this.serverSocket = serverSocket;
		this.start();
	}

	@Override
	public void run() {
		try {
			messages.setText("Waiting for oppenent");
			socket = serverSocket.accept();
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
			messages.setText("Oppenent has connected");
		} catch (IOException e) {
			messages.setText("Can't connect with client");
			return;
		}

		if (!syn.isGame()) {
			syn.setGame(true);
		} else {
			destructor();
			return;
		}

		while (syn.isGame() && !threadSuspended) {
			messages.setText("Your turn");
			if (!threadSuspended) {
				if (!yourTurn()) {
					break;
				}
			}
			messages.setText("Opponent's turn");
			if (!threadSuspended) {
				if (!opponentMove()) {
					break;
				}

			}
		}

		destructor();
	}

	@Override
	protected void destructor() {
		super.destructor();
		try {
			if (serverSocket != null) {
				serverSocket.close();
			}
		} catch (Exception e) {
			// :(
		}
	}
}
