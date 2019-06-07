package chessGame;
import java.util.*;
public class game {
	public static boolean gameEndCondition;
	public static void main(String[] args) {
		Scanner kbReader=new Scanner(System.in);
		ChessBoard gameBoard=new ChessBoard();
		gameBoard.fillBoard();
		gameBoard.printBoard();
		int piecex;
		int piecey;
		int destx;
		int desty;
		moveCoor playerMove;

		//pawn pawn1=new pawn();
		do {
			System.out.println("To make a potential castle enter -1 for three inputs and +1 for right side, -1 for left side. For en passant enter -2 for first three inputs, and x coordinate of piece");
			piecex=kbReader.nextInt();
			piecey=kbReader.nextInt();
			destx=kbReader.nextInt();
			desty=kbReader.nextInt();
			playerMove=new moveCoor(desty, destx, piecey, piecex);
			gameBoard.playermv=playerMove;
			gameBoard.playermv.setPiece(gameBoard.getPiece(piecex, piecey));
			gameBoard.makeMove(piecex, piecey, destx, desty);
			gameBoard.printBoard();
			if(gameBoard.getPiece(destx, desty).equals("k")) {
				System.out.println("You won. Aren't you cool");
				break;
			}
			
			//gameBoard.printBoard();
			
			//gameBoard.makeRandomMove();
			gameBoard.makePriorityMove();
			gameBoard.printBoard();
			if(gameEndCondition==true) {
				break;
			}
			
		}while(true);
		
	}
	
	
	
}


