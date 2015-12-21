package logic;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JTextArea;

import common.Synchro;
import view.Board;

public class Client extends MultyPlayer {

	private final int port;
	private final String ip;

	public Client(int port, String ip, Synchro syn, Board board, JTextArea messages) {
		super(syn, board, messages);
		this.port = port;
		this.ip = ip;
		this.start();
	}

	@Override
	public void run() {
		try {
			socket = new Socket(InetAddress.getByName(ip), port);
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
			messages.setText("Connection  established");
		} catch (IOException e) {
			messages.setText("Unable to connect to the address: " + ip + ":" + port);
			return;
		}

		if (!syn.isGame()) {
			syn.setGame(true);
		} else {
			destructor();
			return;
		}

		while (syn.isGame() && !threadSuspended) {
			messages.setText("Opponent's turn");
			if(!threadSuspended){
				if(!opponentMove()){
					break;
				}
			}
			
			messages.setText("Your turn");
			
			if(!threadSuspended){
				if(!yourTurn()){
					break;
				}
			}
		}

		destructor();
	}

}
