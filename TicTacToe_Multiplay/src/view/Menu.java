package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.ServerSocket;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


import common.Synchro;
import logic.SinglePlay;
import logic.Client;
import logic.GameThread;
import logic.Server;

public class Menu extends JPanel {
	private static final long serialVersionUID = 1L;
		JButton host;
        JButton singlePlay;
        JButton hotSeat;
        JLabel ip;
        JTextField ipAddress;
        JLabel port;
        JTextField hostPort;
        JButton connect;
        Synchro syn;
        Board board;
        JTextArea messages;
        GameThread game;
	public Menu(Synchro syn,Board board,JTextArea messages){
        this.syn = syn;
        this.board = board;
        this.messages = messages;
        
		this.setLayout(new BorderLayout());
		
		Box hBoxUp = Box.createHorizontalBox();
		hBoxUp.add(ip = new JLabel("IP"));
		hBoxUp.add(ipAddress = new JTextField("localhost"));
		hBoxUp.add(port = new JLabel("Port"));
		hBoxUp.add(hostPort = new JTextField("1025"));
		hBoxUp.add(connect = new JButton("Connect"));
		
		Box hBoxDown = Box.createHorizontalBox();
		hBoxDown.add(host = new JButton("Host"));
		hBoxDown.add(Box.createHorizontalGlue());
		hBoxDown.add(singlePlay = new JButton("Single play"));
		hBoxDown.add(Box.createHorizontalGlue());
		hBoxDown.add(hotSeat =new JButton("Hot seat"));
		
		this.add(hBoxUp,BorderLayout.NORTH);
		this.add(hBoxDown,BorderLayout.SOUTH);
		
		hotSeat.addActionListener(new ActionListener(){ 			
			@Override
			public void actionPerformed(ActionEvent e) {
				setGame();
				syn.setGame(true);
				syn.setHotSeat(true);
				syn.setPlayerOneTurn(true);
				messages.setText("Player One Turn");
			}
		});
		
		host.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setGame();
				String ip = ipAddress.getText();
				int port = 1025;
				ServerSocket severSocket;
				for (; port <= 65355; port++) {
					if((severSocket = initializeServer(port,ip)) != null ){
						syn.setGame(false);
						hostPort.setText(Integer.toString(port));
						game = new Server(syn,severSocket,board,messages);
						break;
					}
				}			
				if(port > 65355){
					messages.setText("Invalid Ip or there is no free port");
				}
			}
		});
		
		connect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setGame();
				String ip = ipAddress.getText();
				//sr.setIp(ip);
				int port = Integer.parseInt(hostPort.getText());
				//sr.setPort(port);
				syn.setGame(false);
				game = new Client(port,ip,syn,board,messages);
			}
		});
		
		singlePlay.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setGame();
				game = new SinglePlay(syn,board,messages);
			}
		});
	}

	void setGame(){
		syn.setGame(false);
		if(game != null){
			game.terminate();;
		}
		for (int i = 0; i < 9; i++) {
			board.setButton(i, '\0');
		}
		messages.setText("");
	}
	
	public ServerSocket initializeServer(int port,String ip){
		try {
			return new ServerSocket(port, 8, InetAddress.getByName(ip));
		} catch (Exception e) {
			//e.printStackTrace();
			return null;
		}
	}
}
