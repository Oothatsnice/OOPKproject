package project;
/**
 * Client
 * 
 * Created 2018-02-19
 */
import java.net.*;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.text.StyledDocument;

import project.Server.ClientHandler;

/**
 * Class for handling the connection to a server
 * @author Gustav
 *
 */
public class FileReceiver extends Thread{
	private JFrame myFrame;
	private Socket clientSocket;
    private ServerSocket receiveSocket;
    private int bytesRead;
    private int current;
    private FileOutputStream fos;
    private BufferedOutputStream bos;
    private File myFile;
    private int fileSize;
    private String fileName;
    private int usedPort;
	private JTextPane myTextPane;
	private StyledDocument myDoc;
    
    /**
     * Constructor
     */
    public FileReceiver(String inMsg){
    	current = 0;
		String[] stringArray = inMsg.split("\\s");
	//	for (String a : stringArray) {
	//		System.out.println(a);
	//	}
		int len = stringArray.length;
		String sender = stringArray[1].substring(7, stringArray[1].length()-1);
		fileName = stringArray[3].substring(5, stringArray[3].length());
		fileSize = Integer.parseInt(stringArray[4].substring(5,
				stringArray[4].length()-1));
		StringBuilder question = new StringBuilder();
		question.append(sender);
		question.append(" wants to send you file \"");
		question.append(fileName);
		question.append(" of size \"");
		question.append(fileSize);
		question.append(". Supplied message: ");
		for (int i = 5; i < len - 2; i++) {
			question.append(stringArray[i]);
		}
		
		myFrame = new JFrame();
		myFrame.setLayout(new BoxLayout(myFrame, BoxLayout.Y_AXIS));
		myTextPane = new JTextPane();
		myTextPane.setPreferredSize(new Dimension(400,450));
		myTextPane.setEditable(false);
		
		myDoc = myTextPane.getStyledDocument();
		
		myFrame.add(myTextPane);
		myFrame.setVisible(true);
		
    }
    
    public int getPort() {
    	return usedPort;
    }
    
    /**
     * Client must be run on a new thread and wait for input
     */
    public void run() {
    	usedPort = 5000;
    	boolean tempBool = true;
    	while(tempBool) {
	    	try {
				this.startServer(usedPort);
				tempBool = false;
			} catch (IOException e1) {
				usedPort++;
				tempBool = true;
			}
    	}
    	
    	
        try {        	
        	byte[] myByteArray = new byte[fileSize];
        	InputStream is = clientSocket.getInputStream();
        	fos = new FileOutputStream("C:/Temp/" + fileName);    	
        	bos = new BufferedOutputStream(fos);
        	bytesRead = is.read(myByteArray, 0, myByteArray.length);
        	current = bytesRead;
        	
        	do {
        		bytesRead = is.read(myByteArray, current, myByteArray.length-current);
        		if (bytesRead >= 0) current += bytesRead;
        	} while(current < fileSize);
        	
        	bos.write(myByteArray, 0, current);
        	bos.flush();
		} 
        catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void startServer(int port) throws IOException {
        receiveSocket = new ServerSocket(port);
        clientSocket = receiveSocket.accept();
    }
    
    private class YesButton extends JButton implements ActionListener{
    	public YesButton(){
    		this.setText("Yes");
    		this.addActionListener(this);
    	}

		public void actionPerformed(ActionEvent arg0) {
			
			
		}
    	
    }
    
    private class NoButton extends JButton implements ActionListener{
    	public NoButton(){
    		this.setText("Yes");
    		this.addActionListener(this);
    	}

		public void actionPerformed(ActionEvent arg0) {
			
			
		}
    	
    }
    
//	/**
//	 * Send question to user if it wants to receive file or not
//	 * @param msg
//	 * @return
//	 */
//	public String askFileAcceptance(String msg) {
//		String[] stringArray = msg.split("\\s");
////		for (String a : stringArray) {
////			System.out.println(a);
////		}
//		int len = stringArray.length;
//		String sender = stringArray[1].substring(7, stringArray[1].length()-1);
//		String fileName = stringArray[3].substring(5, stringArray[3].length());
//		String fileSize = stringArray[4].substring(5, stringArray[4].length()-1);
//		StringBuilder question = new StringBuilder();
//		question.append(sender);
//		question.append(" wants to send you file \"");
//		question.append(fileName);
//		question.append(" of size \"");
//		question.append(fileSize);
//		question.append(". Supplied message: ");
//		for (int i = 5; i < len - 2; i++) {
//			question.append(stringArray[i]);
//		}
//		
//		myFileReceiver = new FileReceiver(fileName, Integer.parseInt(fileSize));   ///////////////////////////////////////////////
//		myFileReceiver.openGUI();
//		
//		
////		int ans = JOptionPane.showConfirmDialog(new JFrame(), question.toString());
//		
//		StringBuilder outString = new StringBuilder();
//		
//		if (ans == JOptionPane.YES_OPTION) {
////			myFileReceiver = new FileReceiver(fileName, Integer.parseInt(fileSize));
////			myFileReceiver.start();
//			
//			String respons = JOptionPane.showInputDialog("Leave reply message");
//			
//	    	outString.append("<message");
//			String name = myChatPanel.getName();
//	    	outString.append(" sender=" + name);
//	    	outString.append("> ");
//	    	outString.append("<filerespons");
//	    	outString.append(" reply=yes");
//	    	outString.append(" port=" + myFileReceiver.getPort() + "> ");
//	    	outString.append(respons);
//	    	outString.append(" </filerespons> ");
//	    	outString.append("</message> ");
//	        return outString.toString();
//		}
//		else {
//			
//			St
//		    ring respons = JOptionPane.showInputDialog("Leave reply message");
//			
//	    	outString.append("<message");
//			String name = myChatPanel.getName();
//	    	outString.append(" sender=" + name);
//	    	outString.append("> ");
//	    	outString.append("<filerespons");
//	    	outString.append(" reply=no");
//	    	outString.append(" port=99999> ");
//	    	outString.append(respons);
//	    	outString.append(" </filerespons> ");
//	    	outString.append("</message> ");
//	        return outString.toString();
//		}
//	}    
	
	
	
	
	
}
