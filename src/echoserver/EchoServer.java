package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
	
	public static final int PORT_NUMBER = 6013; 
	public static void main(String[] args) throws IOException, InterruptedException {
		EchoServer server = new EchoServer();
		server.start();
	}

	private void start() throws IOException, InterruptedException {
		ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
		while (true) {
			Socket socket = serverSocket.accept();
			InputStream input = socket.getInputStream();
			OutputStream output = socket.getOutputStream();

			Clients cli = new Clients(input, output);
			Thread anotherOne = new Thread(cli);
			anotherOne.start(); 

			// Put your code here.
			// This should do very little, essentially:
			//   * Construct an instance of your runnable class
			//   * Construct a Thread with your runnable
			//      * Or use a thread pool
			//   * Start that thread
		}
	}

	public class Clients implements Runnable{
		OutputStream output;
		InputStream input;
		int delivery;

		public Clients(InputStream input, OutputStream output){
			this.input = input;
			this.output = output;
		}

		@Override
		public void run(){
			try{
				while ((delivery = input.read())!=-1){
					output.write(delivery);
					output.flush();
				}
			}
			catch (IOException e){
				e.printStackTrace();
			}
		}
	}
}
