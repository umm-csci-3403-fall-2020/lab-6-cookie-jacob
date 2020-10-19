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

		WriteToServer toServer = new WriteToServer(output); //going to the server
		Thread outputThread = new Thread(toServer);
		outputThread.start();
		
		ReadFromServer response = new ReadFromServer(input, socket); //response from server
		Thread inputThread = new Thread(response);
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
				while ((receive = System.in.read()) != -1){
					System.out.write(input.read());
					System.out.flush();
				}
				sock.shutdownInput();
			}
			catch (IOException e){
				e.printStackTrace();
			}
		}
	}

	public class WriteToServer implements Runnable {
		OutputStream output;
		int send;
		WriteToServer(OutputStream output) throws IOException {
			this.output = output;
		}

		@Override
		public void run(){
			try {
				while ((send = System.in.read()) != -1){
					//System.out.write(input.read());
					output.write(send);
					output.flush();
				}
			}
			catch (IOException e){
				e.printStackTrace();
			}
		}
	}
}
