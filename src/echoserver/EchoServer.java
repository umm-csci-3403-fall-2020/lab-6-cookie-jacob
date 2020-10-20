package echoserver;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


public class EchoServer {
	
	public static final int PORT_NUMBER = 6013; 
	public static void main(String[] args) throws IOException, InterruptedException {
		EchoServer server = new EchoServer();
		server.start();
	}

	private void start() throws IOException, InterruptedException {
		try (ServerSocket serverSocket = new ServerSocket(PORT_NUMBER)){
			while(true){
				Socket socket = serverSocket.accept();
				Runnable client = new Client(socket);
				Thread anotherOne = new Thread(client);
				anotherOne.start();
			}
		}
		catch (IOException ioe) {
			System.out.println("We caught an unexpected exception on the client");
			System.out.println(ioe);
		}
	}


	public class Client implements Runnable{
		OutputStream output;
		InputStream input;
		Socket sock; //Need to send the client the socket

		public Client(Socket sock) throws IOException{
			this.input = sock.getInputStream();
			this.output = sock.getOutputStream();
			this.sock = sock;
		}

		@Override
		public void run(){
			int delivery;
			try{
				while ((delivery = input.read())!=-1){
					output.write(delivery);
					output.flush();
				}
				sock.shutdownOutput();
			}
			catch (IOException ioe){
				System.out.println("We caught an exception on the server.");
				System.out.println(ioe);
			}
		}
	}
}
