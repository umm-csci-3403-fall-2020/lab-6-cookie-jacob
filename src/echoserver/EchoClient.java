package echoserver;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class EchoClient {
	public static final int PORT_NUMBER = 6013;
	public static void main(String[] args) throws IOException {
		EchoClient client = new EchoClient();
		client.start();
	}

	private void start() throws IOException {
		Socket socket = new Socket("localhost", PORT_NUMBER);
		InputStream input = socket.getInputStream(); //input stream receiving stuff from server
		OutputStream output = socket.getOutputStream(); //output stream sending stuff to server

		OutputWriter toServer = new OutputWriter(input); //
		Thread outputThread = new Thread(toServer);
		outputThread.start();
		
		InputReader result = new InputReader(output);
		Thread inputThread = new Thread(result);
		inputThread.start();

		input.close();
		output.close();
		socket.close();
	}
	
	// public class InputReader implements Runnable{ //inputReader class
		
	// 	private OutputStream output;
	// 	private int inputStuff;
	// 	private char c;
		
	// 	public InputReader(OutputStream output){
	// 		this.output = output;
	// 	}

	// 	@Override
	// 	public void run(){
	// 		try {
	// 			while ((inputStuff = System.in.read()) != -1) {
	// 				c = (char) inputStuff;
	// 				output.write(c);
	// 				output.flush();
	// 			}
	// 		} catch (IOException e) {
	// 			// TODO Auto-generated catch block
	// 			e.printStackTrace();
	// 		}
	// 	}
	// }

	// public class OutputWriter implements Runnable{
	// 	private InputStream input;
	// 	public int outputStuff;
	// 	public OutputWriter(InputStream input) throws IOException {
	// 		this.input = input;
	// 	}

	// 	@Override 
	// 	public void run(){
	// 		try {
	// 			while ((outputStuff = System.in.read()) != -1) {
	// 				System.out.write(input.read());
	// 				System.out.flush();
	// 			}
	// 		} catch (IOException e) {
	// 			// TODO Auto-generated catch block
	// 			e.printStackTrace();
	// 		}
	// 	}
	// }
}