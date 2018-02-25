package project;

import java.io.*;
import java.net.Socket;

public class FileSender extends Thread{
	private Socket clientSocket;
	private FileInputStream fis;
	private BufferedInputStream bis;
	private OutputStream os;
	private File myFile;
	private byte[] myByteArray;
	
	public FileSender(File inFile) throws FileNotFoundException{
		myFile = inFile;
		myByteArray = new byte[(int)myFile.length()];
		fis = new FileInputStream(myFile);
		bis = new BufferedInputStream(fis);
		
	}
	
	public void run() {
		try {
			bis.read(myByteArray, 0, myByteArray.length);
			os = clientSocket.getOutputStream();
			os.write(myByteArray, 0, myByteArray.length);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    /**
     * Connect to a server
     * @param ip
     * @param port
     */
    public void sendFileTo(String ip, int port){
        try {
			clientSocket = new Socket(ip, port);
		} catch (IOException e) {
			e.printStackTrace();
		}
        this.start();
    }

}
