package echoserver;

import java.io.InputStream;
import java.io.IOException;

public class OutputWriter implements Runnable {
    private InputStream input;
	public int outputStuff;
	public OutputWriter(InputStream input) throws IOException {
        this.input = input;
	}

	@Override 
	public void run(){
		try {
			while ((outputStuff = System.in.read()) != -1) {
				System.out.write(input.read());
				System.out.flush();
            }
            input.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
