import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Test;


public class NetworkClientTest {

	@Test
	public void testRunWithChooseMove() {
		InputStream userIn = new ByteArrayInputStream("L\n".getBytes());
		OutputStream userOut = new ByteArrayOutputStream();
		InputStream socketIn = new ByteArrayInputStream("chooseMove\nlala,ojoj\nL,O\n".getBytes());
		OutputStream socketOut = new ByteArrayOutputStream();
		String name = "name";
		NetworkClient client = new NetworkClient(userIn, userOut, socketIn, socketOut, name);
		client.run();
		assertEquals("name, choose move (lala [L], ojoj [O]):\n", userOut.toString());
		assertEquals("name\nL\n", socketOut.toString());
	}

}
