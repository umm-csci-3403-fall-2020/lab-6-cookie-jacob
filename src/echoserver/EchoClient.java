package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.*;
import java.io.*;

public class EchoClient {
	public static final int PORT_NUMBER = 6013;

	public static void main(String[] args) throws IOException {
		EchoClient client = new EchoClient();
		client.start();
	}

	private void start() throws IOException {
		Socket socket = new Socket("localhost", PORT_NUMBER);
		InputStream input = socket.getInputStream();
		OutputStream output = socket.getOutputStream();

		int nextByte; //gives the ASCII value of the characters in the input
		char c;

		while ((nextByte = System.in.read()) != -1){ //while there is input
			c = (char)nextByte; //casting to a character so it returns what you actually input instead of your input's ASCII value
			output.write(c); //write the byte to the output which will send to the server
			output.flush(); //send the output to the server

			System.out.write(input.read()); //server writing what it needs to send back to the client
								//System.out.write deals with a stream of bytes
			System.out.flush(); //flush the info from the server to the client 
		}

		input.close();
		output.close();
		socket.close();
	}
	// catch (ConnectException ce){
	// 	System.out.println("We were unable to connect to " + "localhost");
	// 	System.out.println("You should make sure the server is running.");
	// }
	// catch (IOException ioe){
	// 	System.out.println("We caught an unexpected exception");
	// 	System.out.println(ioe);
	// }
}