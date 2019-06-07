package chessGame;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
public class ChessBoard {
	boolean castled=false;
	boolean CPKingMove=false;
	boolean CPLeftRookMove=false;
	boolean CPRightRookMove=false;
	boolean lowerCaseFound=false;
	boolean isPlayerValidMove=false;
	moveCoor playermv;
	moveCoor mv;
	//String[][] holdBoard;
	ArrayList<moveCoor> allMoves;
	Comparator<moveCoor> byWeight= (moveCoor o1, moveCoor o2) -> new Integer(o1.weight).compareTo(new Integer(o2.weight));
	int[][] knightOffset= {{-1,-2},
						  {-2,-1},
						  {-2,1},
						  {-1,2},
						  {1,2},
						  {2,1},
						  {2,-1},
						  {1,-2}};
	int[][] kingOffset= {{-1,0},
						{-1,1},
						{0,1},
						{1,1},
						{1,0},
						{1,-1},
						{0,-1},
						{-1,-1}};
	//static int KINGWEIGHT = 10000;
    String[][] board = new String[8][8];
    String[][] testBoard;
    
    public String getPiece(int xpos, int ypos) {
    	return(board[ypos][xpos]);
    }
    
    public void fillBoard() {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = " ";
            }
        }
        board [0][0] = "R";
        board [0][7] = "R";
        board [7][0] = "r";
        board [7][7] = "r";
        board [0][1] = "N";
        board [0][6] = "N";
        board [7][1] = "n";
        board [7][6] = "n";
        board [0][2] = "B";
        board [0][5] = "B";
        board [7][2] = "b";
        board [7][5] = "b";
        board [0][3] = "Q";
        board [7][3] = "q";
        board [0][4] = "K";
        board [7][4] = "k";
        for (int i = 0; i < 8; i++) {
            board[1][i] = "P";
            board[6][i] = "p";
        }
    }
    
    public void printBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(board[i][j]); 
                System.out.print("|");
            }
            //System.out.println();
            for (int j = 0; j < 8; j++) {
            	//System.out.print("-"+" ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    public void makeMove(int piecex, int piecey, int destx, int desty) {
    	if(board[desty][destx].equals("K")) {
    		board[desty][destx]=board[piecey][piecex];
    		board[piecey][piecex]=" ";
    		System.out.println("The Computer Won! Maybe you should go challenge someone else.......");
    		game.gameEndCondition=true;
    	}
    	else {
    		board[desty][destx]=board[piecey][piecex];
    		board[piecey][piecex]=" ";
    	}
    	
    }
    
    public void testMakeMove(int piecex, int piecey, int destx, int desty, String[][] testBoard) {
    	testBoard[desty][destx]=testBoard[piecey][piecex];
		testBoard[piecey][piecex]=" ";
    }
    
    public void makeRandomMove() {
    	ArrayList<moveCoor> allMoves=collectAllMoves();
    	mv=selectAMove(allMoves);
    	makeMove(mv.piecex, mv.piecey,mv.destx, mv.desty);
    }
    
    public void makePriorityMove() {
    	if(castled==true) {
    		
    	}
    	else {
    		allMoves=collectAllMoves();
    		//testAllMoves(allMoves);
    		Collections.sort(allMoves, byWeight);
    		int highWeight=allMoves.get(allMoves.size()-1).weight;
    		//moveCoor mv=allMoves.get(allMoves.size()-1);
    		//for(int i=0;i<allMoves.size();i++) {
    		//	allMoves.get(i).showMe();
    		//}
    		//System.out.println(allMoves);
    		ArrayList<moveCoor> highestWeight=new ArrayList<moveCoor>();
    		for(int x=0;x<allMoves.size();x++) {
    			if(allMoves.get(x).weight==highWeight) {
    				highestWeight.add(allMoves.get(x));
    			}
    		}
    		mv=selectAMove(highestWeight);
    		mv.setPiece(board[mv.piecey][mv.piecex]);
    		if(mv.piece.equals("k")) {
    			CPKingMove=true;
    		}
    		if(mv.piece.equals("r")&&(mv.piecex==0)) {
    			CPLeftRookMove=true;
    		}
    		if(mv.piece.equals("r")&&(mv.piecex==7)) {
    			CPRightRookMove=true;
    		}
    		if(mv.isPassent==true) {
    			board[mv.destx][mv.desty+1]=" ";
    		}
    		if(mv.piece.equals("p")) {
    			if(mv.desty==0) {
    				board[mv.piecey][mv.piecex]="q";
    			}
    		}
    		mv.piece.equals(board[mv.piecey][mv.piecex]);
    		mv.showMe();
    		
    		makeMove(mv.piecex, mv.piecey,mv.destx, mv.desty);
    	}
    	
    }
    
    public moveCoor selectAMove(ArrayList<moveCoor> allMoves) {
    	int leng=allMoves.size();
    	//System.out.println(leng);
    	int pos=(int) (Math.random()*leng);
    	return allMoves.get(pos);
    }
    
    public ArrayList<moveCoor> collectAllMoves(){
    	ArrayList<moveCoor> allMoves=new ArrayList<moveCoor>();
    	int weight=0;
    	for(int i=0;i<8;i++) {//p pawn moves
    		for(int j=0;j<8;j++) {
    			switch (board[i][j]) {
    				case "p":
    					collectPawnMoves(i,j,allMoves);
    					collectEnPassent(i,j,allMoves);
    					break;
    				
    				case "n":{
    					for(int f=0;f<8;f++) {
    						int targetRow=i+knightOffset[f][0];
    						int targetCol=j+knightOffset[f][1];
    						if(isInbounds(targetRow,targetCol)) {
    							if(board[targetRow][targetCol].equals(" ")){
    								allMoves.add(new moveCoor(targetRow,targetCol,i,j));
    							}
    							if(Character.isUpperCase((board[targetRow][targetCol]).charAt(0))){//for capturing
    								if(board[targetRow][targetCol].equals("K")) {
    									weight=1000;
    								}
    								if(board[targetRow][targetCol].equals("Q")) {
    									weight=500;
    								}
    								if(board[targetRow][targetCol].equals("B")) {
    									weight=200;
    								}
    								if(board[targetRow][targetCol].equals("R")) {
    									weight=100;
    								}
    								if(board[targetRow][targetCol].equals("N")) {
    									weight=50;
    								}
    								if(board[targetRow][targetCol].equals("P")) {
    									weight=10;
    								}
    								mv=new moveCoor(targetRow,targetCol,i,j);
    								mv.setWeight(weight);
    								allMoves.add(mv);
    							}
    						}
    					}
    				}
    				break;
    					
    				case "r":{
    					collectRookMoves(i,j,allMoves);
    				}
    				break;
    				
    				case "b":{
    					collectBishopMoves(i,j,allMoves);
    				}
    				break;
    				
    				case "q":{
    					collectBishopMoves(i,j,allMoves);
    					collectRookMoves(i,j,allMoves);
    				}
    				break;
    				
    				case "k":{
    					collectShortCastle(i,j,allMoves);
    					collectLongCastle(i,j,allMoves);
    					for(int f=0;f<8;f++) {
    						int targetRow=i+kingOffset[f][1];
    						int targetCol=j+kingOffset[f][0];
    						if(isInbounds(targetRow,targetCol)) {
    							if((board[targetRow][targetCol].equals(" "))&&(isSafe(targetRow,targetCol, board))){
    								allMoves.add(new moveCoor(targetRow,targetCol,i,j));
    							}
    							if(Character.isUpperCase((board[targetRow][targetCol]).charAt(0))){//for capturing
    								if(board[targetRow][targetCol].equals("K")) {
    									weight=1000;
    								}
    								if(board[targetRow][targetCol].equals("Q")) {
    									weight=500;
    								}
    								if(board[targetRow][targetCol].equals("B")) {
    									weight=200;
    								}
    								if(board[targetRow][targetCol].equals("R")) {
    									weight=100;
    								}
    								if(board[targetRow][targetCol].equals("N")) {
    									weight=50;
    								}
    								if(board[targetRow][targetCol].equals("P")) {
    									weight=10;
    								}
    								mv=new moveCoor(targetRow,targetCol,i,j);
    								mv.setWeight(weight);
    								allMoves.add(mv);
    							}
    						}
    					break;
    			}
    		}
    	}
    }
}
    	return allMoves;
    }
    
    void collectPawnMoves(int i,int j,ArrayList<moveCoor> allMoves) {
			int targetRow=i-1;
			int targetCol=j;
			if(isInbounds(targetRow, targetCol)) {
				if(board[targetRow][targetCol].equals(" ")) {
					mv=new moveCoor(targetRow,targetCol,i,j);
					mv.setWeight(0);
					allMoves.add(mv);
				}
			}
			targetRow=i-1;
			targetCol=j+1;
			if(isInbounds(targetRow, targetCol)) {
				if(Character.isUpperCase((board[targetRow][targetCol]).charAt(0))) {
					mv=new moveCoor(targetRow,targetCol,i,j);
					mv.setWeight(75);
					allMoves.add(mv);
				}
			}
			targetRow=i-1;
			targetCol=j-1;
			if(isInbounds(targetRow, targetCol)) {
				if(Character.isUpperCase((board[targetRow][targetCol]).charAt(0))) {
					mv=new moveCoor(targetRow,targetCol,i,j);
					mv.setWeight(75);
					allMoves.add(mv);
				}
			}
			targetRow=i-2;
			targetCol=j;
			if(i==6) {
				if(isInbounds(targetRow, targetCol)) {
					if(board[targetRow][targetCol].equals(" ")) {
						mv=new moveCoor(targetRow,targetCol,i,j);
						mv.setWeight(0);
						allMoves.add(mv);
					}
				}
			}
			
			
    }
    
    
    void collectRookMoves(int i,int j,ArrayList<moveCoor> allMoves) {
    	int targetRow=i;
    	int targetCol=j;
    	//System.out.println(i);
    	//System.out.println(j);
    	do{
    		targetRow+=1;
    		if(isInbounds(targetRow, targetCol)) {
    			if(board[targetRow][targetCol].equals(" ")) {
    				mv=new moveCoor(targetRow,targetCol,i,j);
					mv.setWeight(0);
					allMoves.add(mv);
    			}
    			if(Character.isUpperCase((board[targetRow][targetCol]).charAt(0))) {
    				mv=new moveCoor(targetRow,targetCol,i,j);
					mv.setWeight(250);
					allMoves.add(mv);
					break;
    			}
    			if(Character.isLowerCase((board[targetRow][targetCol]).charAt(0))) {
					break;
    			}
    		}
    		else {
    			break;
    		}
    	}while(true);
    	targetRow=i;
    	do{
    		targetRow-=1;
    		if(isInbounds(targetRow, targetCol)) {
    			if(board[targetRow][targetCol].equals(" ")) {
    				mv=new moveCoor(targetRow,targetCol,i,j);
					mv.setWeight(0);
					allMoves.add(mv);
    			}
    			if(Character.isUpperCase((board[targetRow][targetCol]).charAt(0))) {
    				mv=new moveCoor(targetRow,targetCol,i,j);
					mv.setWeight(250);
					allMoves.add(mv);
					break;
    			}
    			if(Character.isLowerCase((board[targetRow][targetCol]).charAt(0))) {
					break;
    			}
    		}
    		else {
    			break;
    		}
    	}while(true);
    	targetRow=i;
    	do{
    		targetCol+=1;
    		if(isInbounds(targetRow, targetCol)) {
    			if(board[targetRow][targetCol].equals(" ")) {
    				mv=new moveCoor(targetRow,targetCol,i,j);
					mv.setWeight(0);
					allMoves.add(mv);
    			}
    			if(Character.isUpperCase((board[targetRow][targetCol]).charAt(0))) {
    				mv=new moveCoor(targetRow,targetCol,i,j);
					mv.setWeight(250);
					allMoves.add(mv);
					break;
    			}
    			if(Character.isLowerCase((board[targetRow][targetCol]).charAt(0))) {
					break;
    			}
    		}
    		else {
    			break;
    		}
    	}while(true);
    	targetCol=j;
    	do{
    		targetCol-=1;
    		if(isInbounds(targetRow, targetCol)) {
    			if(board[targetRow][targetCol].equals(" ")) {
    				mv=new moveCoor(targetRow,targetCol,i,j);
					mv.setWeight(0);
					allMoves.add(mv);
    			}
    			if(Character.isUpperCase((board[targetRow][targetCol]).charAt(0))) {
    				mv=new moveCoor(targetRow,targetCol,i,j);
					mv.setWeight(250);
					allMoves.add(mv);
					break;
    			}
    			if(Character.isLowerCase((board[targetRow][targetCol]).charAt(0))) {
					break;
    			}
    		}
    		else {
    			break;
    		}
    	}while(true);
    }
    
    
    void collectBishopMoves(int i,int j,ArrayList<moveCoor> allMoves) {
    	int targetRow=i;
    	int targetCol=j;
    	do{
    		targetRow+=1;
    		targetCol+=1;
    		if(isInbounds(targetRow, targetCol)) {
    			if(board[targetRow][targetCol].equals(" ")) {
    				mv=new moveCoor(targetRow,targetCol,i,j);
					mv.setWeight(0);
					allMoves.add(mv);
    			}
    			if(Character.isUpperCase((board[targetRow][targetCol]).charAt(0))) {
    				mv=new moveCoor(targetRow,targetCol,i,j);
					mv.setWeight(250);
					allMoves.add(mv);
					break;
    			}
    			if(Character.isLowerCase((board[targetRow][targetCol]).charAt(0))) {
					break;
    			}
    		}
    		else {
    			break;
    		}
 
    	}while(true);
    	targetRow=i;
    	targetCol=j;
    	do{
    		targetRow-=1;
    		targetCol+=1;
    		if(isInbounds(targetRow, targetCol)) {
    			if(board[targetRow][targetCol].equals(" ")) {
    				mv=new moveCoor(targetRow,targetCol,i,j);
					mv.setWeight(0);
					allMoves.add(mv);
    			}
    			if(Character.isUpperCase((board[targetRow][targetCol]).charAt(0))) {
    				mv=new moveCoor(targetRow,targetCol,i,j);
					mv.setWeight(250);
					allMoves.add(mv);
					break;
    			}
    			if(Character.isLowerCase((board[targetRow][targetCol]).charAt(0))) {
					break;
    			}
    		}
    		else {
    			break;
    		}

    	}while(true);
    	targetRow=i;
    	targetCol=j;
    	do{
    		targetRow+=1;
    		targetCol-=1;
    		if(isInbounds(targetRow, targetCol)) {
    			if(board[targetRow][targetCol].equals(" ")) {
    				mv=new moveCoor(targetRow,targetCol,i,j);
					mv.setWeight(0);
					allMoves.add(mv);
    			}
    			if(Character.isUpperCase((board[targetRow][targetCol]).charAt(0))) {
    				mv=new moveCoor(targetRow,targetCol,i,j);
					mv.setWeight(250);
					allMoves.add(mv);
					break;
    			}
    			if(Character.isLowerCase((board[targetRow][targetCol]).charAt(0))) {
					break;
    			}
    		}
    		else {
    			break;
    		}
	
	

    	}while(true);
    	targetRow=i;
    	targetCol=j;
    	do{
    		targetRow-=1;
    		targetCol-=1;
    		if(isInbounds(targetRow, targetCol)) {
    			if(board[targetRow][targetCol].equals(" ")) {
    				mv=new moveCoor(targetRow,targetCol,i,j);
					mv.setWeight(0);
					allMoves.add(mv);
    			}
    			if(Character.isUpperCase((board[targetRow][targetCol]).charAt(0))) {
    				mv=new moveCoor(targetRow,targetCol,i,j);
					mv.setWeight(250);
					allMoves.add(mv);
					break;
    			}
    			if(Character.isLowerCase((board[targetRow][targetCol]).charAt(0))) {
					break;
    			}
    		}
    		else {
    			break;
    		}

    	}while(true);
    	
    }
    
    
    boolean isInbounds(int destx, int desty){
    	if(destx<0) {
    		return false;
    	}
    	if(destx>7) {
    		return false;
    	}
    	if(desty<0) {
    		return false;
    	}
    	if(desty>7) {
    		return false;
    	}
    	return true;
    }
    
    boolean isPlayerMoveLegal(int piecex,int piecey,int destx,int desty) {
    	if(isInbounds(destx,desty)==false) {
    		return false;
    	}
    	else {
    		if(board[piecey][piecex].equals("P")) {
    			if(piecex==destx) {
    				if(piecey>desty) {
    					return false;
    				}
    				else {
    					if(piecey==1) {
    						if((desty-piecey==2)||(desty-piecey==1)) {
    							return true;
    						}
    					}
    					else {
    						if(desty-piecey==1) {
    							return true;
    						}
    						else {
    							return false;
    						}
    					}
    				}
    			}
    			else {
    				if(piecex+1==destx){
    					//check if that spot on board is enemy piece, or if en passent applies
    					//Also check to see if it places king in check
    				}
    				else if(piecex-1==destx) {
    					
    				}
    				else {
    					return false;
    				}
    			}
    		}
    		else if(board[piecey][piecex].equals("R")) {
    			//Just see if coordinate is either an enemy piece or open. If not, reject.
    			//Then check to see if lane is still open.
    			//Also check to see that it remains in either the same row or column, never both at once. And if places king in check
    		}
    		else if(board[piecey][piecex].equals("N")) {
    			//Check to see if space is enemy piece or open, and if it places king in check
    		}
    		else if(board[piecey][piecex].equals("B")) {
    			//Same theory as rook, except see if both row and column changed, never both at once. And if by same factor.
    			//And see if it exposes king to check
    		}
    		else if(board[piecey][piecex].equals("K")) {
    			//Just see if it's open or occupied by enemy piece, and if it puts it in danger
    		}
    		else if(board[piecey][piecex].equals("Q")) {
    			//same theory as everything else
    		}
    		else {
    			isPlayerValidMove=false;
    		}
    		return false;
    	}
    	
    	
    }
    
    void collectShortCastle(int i, int j, ArrayList<moveCoor> allMoves) {
    	if(CPKingMove==false) {
    		if(CPRightRookMove==false) {
    			if(board[7][5].equals(" ")&&isSafe(7,5,board)) {
    				if(board[7][6].equals(" ")&&isSafe(7,6,board)) {
        				board[7][6]="k";
        				board[7][5]="r";
        				board[7][4]=" ";
        				board[7][7]=" ";
        				CPKingMove=true;
        		    	CPRightRookMove=true;
        		    	castled=true;
        			}
    			}
    		}
    	}
    	

    }
    void collectLongCastle(int i, int j, ArrayList<moveCoor> allMoves) {
    	if(CPKingMove==false) {
    		if(CPLeftRookMove==false) {
    			if(board[7][1].equals(" ")&&isSafe(7,1,board)) {
    				if(board[7][2].equals(" ")&&isSafe(7,2,board)) {
    					if(board[7][3].equals(" ")&&isSafe(7,3,board)) {
    						board[7][1]="k";
        					board[7][2]="r";
        					board[7][0]=" ";
        					board[7][4]=" ";
        					CPKingMove=true;
        			    	CPLeftRookMove=true;
        			    	castled=true;
    					}
        			}
    			}
    		}
    	}
    	
    }
    
    boolean isSafe(int i, int j, String[][] board) {
    	int targetRow=i;
    	int targetCol=j;
    	for(int f=0;f<8;f++) {              //checks if knights are in range
			targetRow=i+knightOffset[f][0];
			targetCol=j+knightOffset[f][1];
			if(isInbounds(targetRow,targetCol)) {
				if(board[targetRow][targetCol].equals("N")){
					return false;
				}
			}
    	}
    	targetRow=i;                        //checks for rooks/queens
    	targetCol=j;
    	lowerCaseFound=false;
    	do{
    		targetRow+=1;
    		if(isInbounds(targetRow, targetCol)) {
    			if(Character.isLowerCase((board[targetRow][targetCol]).charAt(0))){
    				lowerCaseFound=true;
    				break;
    			}
    			if(Character.isUpperCase((board[targetRow][targetCol]).charAt(0))) {
    				if(((board[targetRow][targetCol]).equals("R"))||((board[targetRow][targetCol]).equals("Q"))){
    					break;
    				}
    				else {
    					lowerCaseFound=true;
        				break;
    				}
    			}
    		}
    		else {
    			break;
    		}
    	}while(true);
    	if(lowerCaseFound=false) {
			return false;
		}
    	targetRow=i;
    	lowerCaseFound=false;
    	do{
    		targetRow-=1;
    		if(isInbounds(targetRow, targetCol)) {
    			if(Character.isLowerCase((board[targetRow][targetCol]).charAt(0))){
    				lowerCaseFound=true;
    				break;
    			}
    			if(Character.isUpperCase((board[targetRow][targetCol]).charAt(0))) {
    				if(((board[targetRow][targetCol]).equals("R"))||((board[targetRow][targetCol]).equals("Q"))){
    					break;
    				}
    				else {
    					lowerCaseFound=true;
        				break;
    				}
    			}
    			
    		}
    		else {
    			break;
    		}
    	}while(true);
    	if(lowerCaseFound=false) {
			return false;
		}
    	targetRow=i;
    	lowerCaseFound=false;
    	do{
    		targetCol+=1;
    		if(isInbounds(targetRow, targetCol)) {
    			if(Character.isLowerCase((board[targetRow][targetCol]).charAt(0))){
    				lowerCaseFound=true;
    				break;
    			}
    			if(Character.isUpperCase((board[targetRow][targetCol]).charAt(0))) {
    				if(((board[targetRow][targetCol]).equals("R"))||((board[targetRow][targetCol]).equals("Q"))){
    					break;
    				}
    				else {
    					lowerCaseFound=true;
        				break;
    				}
    			}
    		}
    		else {
    			break;
    		}
    	}while(true);
		if(lowerCaseFound=false) {
			return false;
		}
    	targetCol=j;
    	lowerCaseFound=false;
    	do{
    		targetCol-=1;
    		if(isInbounds(targetRow, targetCol)) {
    			if(Character.isLowerCase((board[targetRow][targetCol]).charAt(0))){
    				lowerCaseFound=true;
    				break;
    			}
    			if(Character.isUpperCase((board[targetRow][targetCol]).charAt(0))) {
    				if(((board[targetRow][targetCol]).equals("R"))||((board[targetRow][targetCol]).equals("Q"))){
    					break;
    				}
    				else {
    					lowerCaseFound=true;
        				break;
    				}
    			}
    		}
    		else {
    			break;
    		}
    	}while(true);
    	if(lowerCaseFound=false) {
			return false;
		}
    	
    	
    	
    	
    	targetRow=i;                                  //checks for bishops/queens
    	targetCol=j;
    	lowerCaseFound=false;
    	do{
    		targetRow+=1;
    		targetCol+=1;
    			if(isInbounds(targetRow, targetCol)) {
        			if(Character.isLowerCase((board[targetRow][targetCol]).charAt(0))){
        				lowerCaseFound=true;
        				break;
        			}
        			if(Character.isUpperCase((board[targetRow][targetCol]).charAt(0))) {
        				if(((board[targetRow][targetCol]).equals("B"))||((board[targetRow][targetCol]).equals("Q"))){
        					break;
        				}
        				else {
        					lowerCaseFound=true;
            				break;
        				}
        			}
        		}
        		else {
        			break;
        		}
    	}while(true);
    	if(lowerCaseFound=false) {
			return false;
		}
    	targetRow=i;
    	targetCol=j;
    	lowerCaseFound=false;
    	do{
    		targetRow+=1;
    		targetCol+=1;
    			if(isInbounds(targetRow, targetCol)) {
        			if(Character.isLowerCase((board[targetRow][targetCol]).charAt(0))){
        				lowerCaseFound=true;
        				break;
        			}
        			if(Character.isUpperCase((board[targetRow][targetCol]).charAt(0))) {
        				if(((board[targetRow][targetCol]).equals("B"))||((board[targetRow][targetCol]).equals("Q"))){
        					break;
        				}
        				else {
        					lowerCaseFound=true;
            				break;
        				}
        			}
        		}
        		else {
        			break;
        		}
    	}while(true);
    	if(lowerCaseFound=false) {
			return false;
		}
    	targetRow=i;
    	targetCol=j;
    	lowerCaseFound=false;
    	do{
    		targetRow+=1;
    		targetCol+=1;
    			if(isInbounds(targetRow, targetCol)) {
        			if(Character.isLowerCase((board[targetRow][targetCol]).charAt(0))){
        				lowerCaseFound=true;
        				break;
        			}
        			if(Character.isUpperCase((board[targetRow][targetCol]).charAt(0))) {
        				if(((board[targetRow][targetCol]).equals("B"))||((board[targetRow][targetCol]).equals("Q"))){
        					break;
        				}
        				else {
        					lowerCaseFound=true;
            				break;
        				}
        			}
        		}
        		else {
        			break;
        		}
    	}while(true);
    	if(lowerCaseFound=false) {
			return false;
		}
    	targetRow=i;
    	targetCol=j;
    	lowerCaseFound=false;
    	do{
    		targetRow+=1;
    		targetCol+=1;
    			if(isInbounds(targetRow, targetCol)) {
        			if(Character.isLowerCase((board[targetRow][targetCol]).charAt(0))){
        				lowerCaseFound=true;
        				break;
        			}
        			if(Character.isUpperCase((board[targetRow][targetCol]).charAt(0))) {
        				if(((board[targetRow][targetCol]).equals("B"))||((board[targetRow][targetCol]).equals("Q"))){
        					break;
        				}
        				else {
        					lowerCaseFound=true;
            				break;
        				}
        			}
        		}
        		else {
        			break;
        		}
    	}while(true);
    	if(lowerCaseFound=false) {
			return false;
		}
    	
    	
    	
    	
    	return true;
    }

    void collectEnPassent(int i, int j, ArrayList<moveCoor> allMoves) {
    	if(i==3) {
    		int targetRow=i-1;
    		int targetCol=j-1;
    		if(isInbounds(targetCol, targetRow)) {
    			if(playermv.piece.equals("P")) {
    				if((playermv.desty-playermv.piecey)==2) {
    					if(playermv.destx==targetCol) {
    						mv=new moveCoor(targetRow,targetCol,i,j);
    						mv.setPassent(true);
    						mv.setWeight(100);
    						allMoves.add(mv);
    					}
    				}
    			}
    		}
    		
    		targetRow=i-1;
    		targetCol=j+1;
    		if(isInbounds(targetCol, targetRow)) {
    			if(playermv.piece.equals("P")) {
    				if((playermv.desty-playermv.piecey)==2) {
    					if(playermv.destx==targetCol) {
    						mv=new moveCoor(targetRow,targetCol,i,j);
    						mv.setPassent(true);
    						mv.setWeight(100);
    						allMoves.add(mv);
    					}
    				}
    			}
    		}
    		
    		
    	}
    }
    
    void testAllMoves(ArrayList<moveCoor> allMoves) {
    	int kingRow=0;
    	int kingCol=0;
    	int counter=0;
    	for(int i=0;i<allMoves.size();i++) {
    		testBoard=board.clone();
    		mv=allMoves.get(i);
    		testMakeMove(mv.piecex, mv.piecey,mv.destx, mv.desty, testBoard);
    		for(int krow=0;krow<7;krow++) {
    			for(int kcol=0;kcol<7;kcol++) {
    				if(testBoard[krow][kcol].equals("k")) {
    					kingRow=krow;
    					kingCol=kcol;
    				}
    			}
    		}
    		if(isSafe(kingRow,kingCol,testBoard)==false) {
    			allMoves.remove(i);
    			i--;
    		}
    	}
    	
    	//testBoard=board;
    	
    }

}