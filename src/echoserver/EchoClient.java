package echoserver;
import java.util.concurrent.atomic.AtomicInteger;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class EchoClient {
	public static final int PORT_NUMBER = 6013;
	private int count = 0;
	private AtomicInteger atomicCount = new AtomicInteger(0);

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
	
	public class InputReader implements Runnable{ //inputReader class
		
		private int inputStuff;
		private char c;
		public InputReader(OutputStream output) throws IOException{ //the input from keyboard will go to the output stream
			while ((inputStuff = System.in.read()) != -1){
				c = (char)inputStuff;
				output.write(c);
				output.flush();
			}
		}

		@Override
		public void run(){
			//stuff
		}
	}

	public class OutputWriter implements Runnable{
		public int outputStuff;
		public OutputWriter(InputStream input) throws IOException {
			while ((outputStuff = System.in.read()) != -1){
				System.out.write(input.read());
				System.out.flush();
			}
		}

		@Override 
		public void run(){
			//even more stuff
		}
	}
}