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

		WriteToServer toServer = new WriteToServer(socket); //going to the server
		Thread outputThread = new Thread(toServer); //first thread
		outputThread.start();
		
		ReadFromServer response = new ReadFromServer(socket); //response from server
		Thread inputThread = new Thread(response); //second thread
		inputThread.start();
	}
	
	public class ReadFromServer implements Runnable {
		InputStream input;
		Socket sock;

		public ReadFromServer(Socket sock) throws IOException{
			this.input = sock.getInputStream();
			this.sock = sock;
		}

		@Override
		public void run(){
			int receive;
			try {
				while ((receive = input.read()) != -1){ //As long as there is still data in the InputStream
					System.out.write(receive);
					System.out.flush();
				}
				sock.close(); //close connection when finished
			}
			catch (IOException ioe){
				System.out.println("We caught an unexpected exception on the client.");
				System.out.println(ioe);
			}
		}
	}

	public class WriteToServer implements Runnable {
		OutputStream output;
		Socket sock;

		public WriteToServer(Socket sock) throws IOException {
			this.output = sock.getOutputStream();
			this.sock = sock;
		}

		@Override
		public void run(){
			int send;
			try {
				while ((send = System.in.read()) != -1){ //As long as the user is still inputting data
					output.write(send);
					output.flush();
				}
				sock.shutdownOutput(); //stops further input, but doesn't close connection
			}
			catch (IOException ioe){
				System.out.println("We caught an unexpected exception on the client.");
				System.out.println(ioe);
			}
		}
	}
}
