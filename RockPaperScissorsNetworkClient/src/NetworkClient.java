import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class NetworkClient {
	
	private BufferedReader userIn;
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	private PrintWriter userOut;
	private String name; 
	
	public NetworkClient(InputStream userIn, OutputStream userOut,
			InputStream socketIn, OutputStream socketOut, String name ) {
		
		this.socketOut = new PrintWriter(socketOut, true);
		this.socketIn = new BufferedReader(new InputStreamReader(socketIn));
		this.userIn = new BufferedReader(new InputStreamReader(userIn));
		this.userOut = new PrintWriter(userOut);
		this.name = name;
	}
	
	public void run() {
        
        try {
                socketOut.println(name);
        		String input;
                while ((input = socketIn.readLine()) != null) {
                    if(input.equals("chooseMove")) {
                    	String possibleMovesString = socketIn.readLine();
                    	String possibleSymbolsString = socketIn.readLine();
                    	String[] possibleMoves = possibleMovesString.split(",");
                    	String[] possibleSymbols = possibleSymbolsString.split(",");
                    	assert(possibleMoves.length == possibleSymbols.length);
                    	socketOut.println(chooseMove(possibleMoves, possibleSymbols));
                    }
                    else if(input.equals("roundResults")) {
                    	String resultsString = socketIn.readLine();
                    	String[] results = resultsString.split(",");
                    	assert(results.length == 4);
                    	String yourMove = results[0];
                    	String opponentMove = results[1];
                    	String result = results[2];
                    	String points = results[3];
                    	displayRoundResults(yourMove, opponentMove, result, points);
                    	
                    }
                    else if(input.equals("gameResults")) {
                    	displayGameResults(socketIn.readLine());
                    	break;
                    }
                    else {
                    	throw new ProtocolException("Incorrect message header.");
                    }
                }
            } 
        	catch (UnknownHostException e) {
                e.printStackTrace();
                System.exit(1);
            } 
        	catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        	catch (ProtocolException e) {
        		e.printStackTrace();
        		System.exit(1);
        	}
	}
	
	private String chooseMove(String[] possibleMoves, String[] possibleSymbols) throws IOException {
		String possibleMoveString = getPossibleMoveString(possibleMoves, possibleSymbols);
		while(true) {
			userOut.println(name + ", choose move (" + possibleMoveString + "):");
			userOut.flush();
			String inputSymbol;
			inputSymbol = userIn.readLine();
			List<String> possibleSymbolsList = new ArrayList<String>();
			Collections.addAll(possibleSymbolsList, possibleSymbols);
			if(possibleSymbolsList.contains(inputSymbol)) {
				return inputSymbol;
			}
			userOut.println("Wrong choice. Try again.");
		}
	}
	
	private String getPossibleMoveString(String[] possibleMoves, String[] possibleSymbols) {
		List<String> l = new ArrayList<String>();
		for(int i = 0; i< possibleMoves.length; i++) {
			StringBuilder sb = new StringBuilder();
			sb.append(possibleMoves[i]);
			sb.append(" [");
			sb.append(possibleSymbols[i]);
			sb.append("]");
			l.add(sb.toString());
		}
		return String.join(", ", l);
	}

	private void displayRoundResults(String yourMove, String opponentMove, String result, String points) {
		userOut.println("You played " + yourMove + " against " + opponentMove 
				+ " and " + result);
		userOut.println("Your current score: " + points);
		userOut.println();
	}
	
	private void displayGameResults(String result) {
		userOut.println("End of game. You " + result + "!");
		userOut.println();
	}
	
	public static void main(String[] args) throws IOException {
		if (args.length != 3) {
            System.err.println("Usage: java EchoClient <host name> <port number> <your name>");
            System.exit(1);
        }
		
		String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        String name = args[2];
        
        Socket socket = null;
        try {
	        socket = new Socket(hostName, portNumber);    
	        NetworkClient client = new NetworkClient(System.in, System.out, 
	        		socket.getInputStream(), socket.getOutputStream(), name);
	        client.run();
        } finally {
        	if (socket != null) {
        		socket.close();
        	}
        }
	}
}
