package echoserver;
import java.io.OutputStream;
import java.io.IOException;

public class InputReader implements Runnable {
    private OutputStream output;
	private int inputStuff;
	private char c;
		
	public InputReader(OutputStream output){
		this.output = output;
	}

	@Override
	public void run(){
		try {
			while ((inputStuff = System.in.read()) != -1) {
				c = (char) inputStuff;
				output.write(c);
				output.flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
