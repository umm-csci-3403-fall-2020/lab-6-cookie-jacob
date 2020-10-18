package echoserver; //used to reference other classes in the echoserver directory
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
		private InputStream input;
		public int receive;
		public Socket sock;
		public ReadFromServer(InputStream input, Socket sock){
			this.input = input;
			this.sock = sock;
		}

		@Override
		public void run(){
			try {
				while ((receive = System.in.read()) != -1){
					System.out.write(receive);
					System.out.flush();
				}
				sock.shutdownOutput();
			}
			catch (IOException e){
				e.printStackTrace();
			}
		}
	}

	public class WriteToServer implements Runnable {
		private OutputStream output;
		public int send;
		public WriteToServer(OutputStream output) throws IOException {
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
