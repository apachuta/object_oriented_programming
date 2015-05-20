package players;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import moves.Move;
import moves.MoveFactory;
import results.Result;
import exceptions.GameBrokenException;
import exceptions.NetworkException;

public class NetworkPlayer implements Player {
	
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	private String name;
	
	public NetworkPlayer(int port) throws NetworkException {
		try (
				ServerSocket serverSocket = new ServerSocket(port);
				){
			clientSocket = serverSocket.accept();    
			out = new PrintWriter(clientSocket.getOutputStream(), true);                  
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			name = in.readLine();
			System.out.println(name + " has connected.");
		}
		catch(IOException e) {
			throw new NetworkException(e);
		}
	}
	
	public NetworkPlayer() throws NetworkException {
		this(8888);
	}

	@Override
	public Move chooseMove(List<Move> possibleMoves) throws GameBrokenException {
		String possibleMovesString = getPossibleMovesString(possibleMoves);
		String possibleSymbolsString = getPossibleSymbolsString(possibleMoves);
		String chosenSymbol;
		try {
			out.println("chooseMove");
			out.println(possibleMovesString);
			out.println(possibleSymbolsString);
			chosenSymbol = in.readLine();
		}
		catch(IOException e) {
			throw new GameBrokenException(e);
		}
		return MoveFactory.getMove(chosenSymbol);
	}

	@Override
	public void giveRoundResults(Move yourMove, Move opponentMove,
		Result result, int points) {
		out.println("roundResults");
		List<String> l = new ArrayList<String>();
		l.add(yourMove.toString());
		l.add(opponentMove.toString());
		l.add(result.toString());
		l.add(Integer.toString(points));
		out.println(String.join(",", l));
	}

	@Override
	public void giveGameResults(Result result) {
		out.println("gameResults");
		out.println(result.toString());
		
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.close();
		try {
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	private String getPossibleSymbolsString(List<Move> possibleMoves) {
		List<String> possibleSymbols = new ArrayList<String>(); 
		for(Move move : possibleMoves)
		{
			possibleSymbols.add(move.getSymbol());
		}
		return String.join(",", possibleSymbols);
	}
	
	private String getPossibleMovesString(List<Move> possibleMoves) {
		List<String> possibleMovesNames = new ArrayList<String>();
		for(Move m : possibleMoves) {
			possibleMovesNames.add(m.toString());
		}
		return String.join(",", possibleMovesNames);
	}

}
