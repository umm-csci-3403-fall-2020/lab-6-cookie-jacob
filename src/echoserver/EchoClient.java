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
		InputStream input = socket.getInputStream(); 
		OutputStream output = socket.getOutputStream(); 

		WriteToServer toServer = new WriteToServer(output, socket); //going to the server
		Thread outputThread = new Thread(toServer); //first thread
		outputThread.start();
		
		ReadFromServer response = new ReadFromServer(input, socket); //response from server
		Thread inputThread = new Thread(response); //second thread
		inputThread.start();
	}
	
	public class ReadFromServer implements Runnable {
		InputStream input;
		int receive;
		Socket sock;

		public ReadFromServer(InputStream input, Socket sock){
			this.input = input;
			this.sock = sock;
		}

		@Override
		public void run(){
			try {
				while ((receive = input.read()) != -1){ //As long as there is still data in the InputStream
					System.out.write(receive);
					System.out.flush();
				}
				sock.shutdownInput(); //close connection when finished
			}
			catch (IOException ioe){
				System.out.println("We caught an unexpected exception");
			}
		}
	}

	public class WriteToServer implements Runnable {
		OutputStream output;
		int send;
		Socket sock;

		public WriteToServer(OutputStream output, Socket sock) throws IOException {
			this.output = output;
			this.sock = sock;
		}

		@Override
		public void run(){
			try {
				while ((send = System.in.read()) != -1){ //As long as the user is still inputting data
					output.write(send);
					output.flush();
				}
				sock.shutdownOutput(); //close connection when finished
			}
			catch (IOException ioe){
				System.out.println("We caught an unexpected exception");
			}
		}
	}
}
