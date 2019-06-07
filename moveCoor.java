package chessGame;

public class moveCoor{
	int destx;
	int desty;
	int piecex;
	int piecey;
	int weight;
	String piece;
	String capturedPiece;
	boolean isPassent=false;
	// y mapping to row, x mapping to col
	moveCoor(int desty,int destx, int piecey, int piecex){
		this.destx=destx;
		this.desty=desty;
		this.piecex=piecex;
		this.piecey=piecey;
		
	}
	void setWeight(int w) {
		weight=w;
	}
	/*@Override
	public int compareTo(moveCoor passCoor) {
		if(this.weight>=passCoor.weight) {
			return 1;
		}
		else {
			return 0;
		}

	}*/
	void showMe() {
		System.out.print(piece+" ");
		System.out.print(weight+" ");
		System.out.print(piecex+" ");
		System.out.print(piecey+" ");
		System.out.print(destx+" ");
		System.out.println(desty);
	}
	void setPiece(String s) {
		this.piece=s;
	}
	void setPassent(boolean isp) {
		this.isPassent=isp;
	}
	
	
}
