package project;
/**
 * Client
 * 
 * Created 2018-02-19
 */
import java.net.*;
import java.io.*;
import java.util.*;

import project.Server.ClientHandler;

/**
 * Class for handling the connection to a server
 * @author Gustav
 *
 */
public class FileReceiver extends Thread{
	private Socket clientSocket;
    private ServerSocket receiveSocket;
    private int bytesRead;
    private int current;
//    private OutputStream out;
    private FileOutputStream fos;
    private BufferedOutputStream bos;
    private File myFile;
    private int fileSize;
    private String fileName;
    private int usedPort;
    private static ArrayList<Integer> usedPorts;
//    private BufferedInputStream bis;
//    private FileSocketObservable myObservable;
    
    /**
     * Constructor
     */
    public FileReceiver(String inName, int inSize){
    	usedPorts = new ArrayList<Integer>();
    	current = 0;
    	fileSize = inSize;
    	fileName = inName;
//    	myObservable = new FileSocketObservable();
    }
    
    public int getPort() {
//    	if (usedPorts.isEmpty()) {
//    		return 5000;
//    	}
//    	else {
//    		return usedPorts.get(usedPorts.size()-1);
//    	}
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
//        	if (usedPorts.isEmpty()) {
//        		usedPorts.add(5000);
//        		this.startServer(5000);
//        	}
//        	else {
//        		usedPorts.add(usedPorts.get(usedPorts.size()-1)+1);
//        		this.startServer(usedPorts.get(usedPorts.size()-1));
//        	}
        	
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
    
//    /**
//     * Connect to a server
//     * @param ip
//     * @param port
//     */
//    public void startConnection(String ip, int port){
//        try {
//			clientSocket = new Socket(ip, port);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//        this.start();
//    }
    
//    /**
//     * Send string to server
//     * @param msg
//     * @throws IOException
//     */
//    public void sendFile(File ) throws IOException {
//        out.println(msg);
//    }
    
//    /**
//     * Stop connection to a server
//     * @throws IOException
//     */
//    public void stopConnection() throws IOException {
//        in.close();
//        out.close();
//        clientSocket.close();
//    }
    
//    /**
//     * Return the observable
//     * @return
//     */
//    public ClientObservable getObservable(){
//    	return myObservable;
//    }
//    
//    /**
//     * Class for sending incoming strings to observers
//     * @author Gustav
//     *
//     */
//    class ClientObservable extends Observable{
//
//    	/**
//    	 * Send string to observers
//    	 * @param inString
//    	 */
//    	public void sendUpdate(String inString){
//    		setChanged();
//    		notifyObservers(inString);
//    	}
//    	
//    }

}
