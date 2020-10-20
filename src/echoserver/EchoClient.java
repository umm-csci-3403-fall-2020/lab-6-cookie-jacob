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
		Thread outputThread = new Thread(toServer);
		outputThread.start();
		
		ReadFromServer response = new ReadFromServer(input, socket); //response from server
		Thread inputThread = new Thread(response);
		inputThread.start();
	}
	
	public class ReadFromServer implements Runnable {
		InputStream input;
		//int receive;
		Socket sock;
		public ReadFromServer(InputStream input, Socket sock){
			this.input = input;
			this.sock = sock;
		}

			
		public void run(){
			int receive;
			try {
				while ((receive = input.read()) != -1){
					System.out.write(receive);
					//System.out.flush();
				}
				System.out.flush();
				sock.shutdownInput();
			}
			catch (IOException ioe){
				System.out.println("We caught an unexpected exception");
			}
		}
	}

	public class WriteToServer implements Runnable {
		OutputStream output;
		//int send;
		Socket sock;
		public WriteToServer(OutputStream output, Socket sock) throws IOException {
			this.output = output;
			this.sock = sock;
		}

		
		public void run(){
			int send;
			try {
				while ((send = System.in.read()) != -1){
					output.write(send);
					//output.flush();
				}
				//output.flush();
				sock.shutdownOutput();
			}
			catch (IOException ioe){
				System.out.println("We caught an unexpected exception");
			}
		}
	}
}
