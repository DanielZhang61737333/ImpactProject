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
		boolean legalMove=false;

		//pawn pawn1=new pawn();
		do {
			System.out.println("To make a potential castle enter -1 for three inputs and 1 for right side, -1 for left side.");// For en passant enter -2 for first three inputs, and x coordinate of piece
			piecex=kbReader.nextInt();
			piecey=kbReader.nextInt();
			destx=kbReader.nextInt();
			desty=kbReader.nextInt();
			gameBoard.playermv=new moveCoor(desty,destx,piecey,piecex);
			if(piecex==-1) {
				if(desty==-1) {//left side long castle
					/*if(gameBoard.isSafe(0,4, gameBoard.board)==false) {
						System.out.println("Illegal castle");
						continue;
					}
					if(gameBoard.isSafe(0,3, gameBoard.board)==false) {
						System.out.println("Illegal castle");
						continue;
					}
					if(gameBoard.isSafe(0,2, gameBoard.board)==false) {
						System.out.println("Illegal castle");
						continue;
					}*/
					if((gameBoard.PKingMove==false)&&(gameBoard.PLeftRookMove==false)) {
						if((gameBoard.board[0][3].equals(" "))&&(gameBoard.board[0][2].equals(" "))&&(gameBoard.board[0][1].equals(" "))) {	
							gameBoard.board[0][4]=" ";
							gameBoard.board[0][0]=" ";
							gameBoard.board[0][3]="R";
							gameBoard.board[0][2]="K";
							gameBoard.PKingMove=true;
							gameBoard.PLeftRookMove=true;
							playerMove=new moveCoor(0, 0, 0, 0);
							gameBoard.playermv.setPiece(gameBoard.getPiece(0, 0));
							gameBoard.printBoard();
							gameBoard.makePriorityMove();
							gameBoard.printBoard();
							continue;
						}
					}
				}
				if(desty==1) {//right side short castle
						/*if(gameBoard.isSafe(0,4, gameBoard.board)==false) {
							System.out.println("Illegal castle");
							continue;
						}
						if(gameBoard.isSafe(0,5, gameBoard.board)==false) {
							System.out.println("Illegal castle");
							continue;
						}
						if(gameBoard.isSafe(0,6, gameBoard.board)==false) {
							System.out.println("Illegal castle");
							continue;
						}*/
						if((gameBoard.PKingMove==false)&&(gameBoard.PRightRookMove==false)) {
							if((gameBoard.board[0][5].equals(" "))&&(gameBoard.board[0][6].equals(" "))) {	
								gameBoard.board[0][4]=" ";
								gameBoard.board[0][7]=" ";
								gameBoard.board[0][5]="R";
								gameBoard.board[0][6]="K";
								gameBoard.PKingMove=true;
								gameBoard.PRightRookMove=true;
								playerMove=new moveCoor(0, 0, 0, 0);
								gameBoard.playermv.setPiece(gameBoard.getPiece(0, 0));
								gameBoard.printBoard();
								gameBoard.makePriorityMove();
								gameBoard.printBoard();
								continue;
							}
						}
					
				}
			}
			/*if(piecex==-2) {
				continue;
			}*/
			if(gameBoard.isPlayerMoveLegal(piecex,piecey,destx,desty)==false) {
				System.out.println("illegal move");
				continue;
			}
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
				System.out.println("You won. Aren't you cool");
				break;
			}
			
		}while(true);
		
	}
	
	
	
}


