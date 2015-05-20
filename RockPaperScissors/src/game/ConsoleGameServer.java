package game;

import exceptions.GameBrokenException;
import exceptions.NetworkException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import moves.Move;
import moves.Paper;
import moves.Rock;
import moves.Scissors;
import players.Computer;
import players.Human;
import players.NetworkPlayer;
import players.Player;
import results.Result;

public class ConsoleGameServer implements GameSpectator {
	private static final int NUMBER_OF_CONSOLE_LINES = 42;
	
	private boolean enableScreenClearing;
	private Player player1;
	private Player player2;
	
	@Override
	public void giveRoundResults(Move move1, Move move2, Result result1,
			Result result2, int points1, int points2) {
		System.out.println(player1.toString() + " played " + move1 + " against " + move2 
				+ " of " + player2.toString() + " and " + result1);
		System.out.println("Current score:");
		System.out.println(player1.toString() + ": " + points1 + ", " + player2 + ": " + points2);
		System.out.println();
	}

	@Override
	public void giveGameWinner(Player winner) {
		System.out.println("End of game: " + winner + " won!");
		System.out.println();
	}
	
	@Override
	public void playerHasMoved() {
		if (enableScreenClearing) {
			clearScrean();
		}
	}
	
	private void clearScrean() {
		for (int i = 0; i < NUMBER_OF_CONSOLE_LINES; ++i) {
			System.out.println();
		}
	}

	public void run() throws IOException {
		BufferedReader inputBufferedReader = new BufferedReader(new InputStreamReader(System.in));
		
		while (true) {
		
			System.out.println("ROCK PAPER SCISSORS");
			System.out.println("Choose option:");
			System.out.println("[1] Human vs Computer");
			System.out.println("[2] Computer vs Computer");
			System.out.println("[3] Human vs Human");
			System.out.println("[4] Human vs Network Player");
			System.out.println("[5] Computer vs Network Player");
			System.out.println("[6] Network Player vs Network Player");
			System.out.println("[7] Exit");
			
			String input = inputBufferedReader.readLine();
			List<Move> possibleMoves = Arrays.asList(new Rock(), new Paper(), new Scissors());
			GameConfiguration config = new GameConfiguration(possibleMoves, 3);
			
			enableScreenClearing = false;
			if (input.equals("1")) {
				player1 = new Human(inputBufferedReader, "Human");
				player2 = new Computer();
			} else if (input.equals("2")) {
				player1 = new Computer("Computer 1");
				player2 = new Computer("Computer 2");
			} else if (input.equals("3")) {
				player1 = new Human(inputBufferedReader, "Human 1");
				player2 = new Human(inputBufferedReader, "Human 2");
				enableScreenClearing = true;
			} else if ( input.equals("4")) {
				System.out.println("Wait for a network player..");
				player1 = new Human(inputBufferedReader, "Human");
				try {
					player2 = new NetworkPlayer();
				} catch (NetworkException e) {
					System.err.println("Unable to connect with a network player.");
					System.exit(1);
				}
			}
			else if (input.equals("5")) {
				System.out.println("Wait for a network player..");
				player1 = new Computer("Computer");
				try {
					player2 = new NetworkPlayer();
				} catch (NetworkException e) {
					System.err.println("Unable to connect with a network player.");
					System.exit(1);
				}
			}
			else if(input.equals("6")) {
				System.out.println("Wait for a network player..");
				try {
					player1 = new NetworkPlayer();
				} catch (NetworkException e) {
					System.err.println("Unable to connect with a network player.");
					System.exit(1);
				}
				System.out.println("Wait for a network player..");
				try {
					player2 = new NetworkPlayer();
				} catch (NetworkException e) {
					System.err.println("Unable to connect with a network player.");
					System.exit(1);
				}
			}
			else {
				break;
			}
			
			Game game = new Game(this, player1, player2, config);
			try {
				game.start();
			} catch (GameBrokenException e) {
				System.err.println("Game has stopped due to an unexpected error.");
			}
			
			System.out.println("Press enter...");
			inputBufferedReader.readLine();
		}		
	}
	
	public static void main(String[] args) throws IOException {
		new ConsoleGameServer().run();
	}
}
