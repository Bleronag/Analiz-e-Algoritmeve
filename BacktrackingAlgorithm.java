import java.util.Arrays;

public class BacktrackingAlgorithm {
   private static  int[][] sudoku= {      //Puzzle enigma që duam të zgjidhim   		  
		   {0,0,0,1,0,5,0,6,8},
            {0,0,0,0,0,0,7,0,1},
            {9,0,1,0,0,0,0,3,0},
            {0,0,7,0,2,6,0,0,0},
            {5,0,0,0,0,0,0,0,3},
            {0,0,0,8,7,0,4,0,0},
            {0,3,0,0,0,0,8,0,5},
            {1,0,5,0,0,0,0,0,0},
            {7,9,0,4,0,1,0,0,0}
            };
            
   public static void main(String[] args) { 
      long startMilliSecond=System.currentTimeMillis();
      System.out.println("Backtracking Algorithm");
     // System.out.println("start: "+startMilliSecond);
      long stopMilliSecond=0;
      if(solve()){
         stopMilliSecond=System.currentTimeMillis();
         //System.out.println("end: "+stopMilliSecond);
         display(sudoku);
      }else{
         System.out.println("Unsolvable");
      }
      System.out.println("Time: "+(stopMilliSecond-startMilliSecond)+" ms");
   }
   
/*Gjeni një hapësirë të re boshe*/
   public static int[] find_next_empty(int[][] sudoku) {
      int[] result= {-1,-1};
      for(int i=0;i<sudoku.length;i++) {
         for(int j=0;j<sudoku[i].length;j++) {
            if(sudoku[i][j]==0) {
               result[0]=i;
               result[1]=j;
            }
         		
         }
      }
      return result;
   }
	
   /*Kthehet true nëse një vlerë e vlefshme mund të jetë zgjidhje në sudoku*/
   public static boolean isValid(int[][] sudoku, int row, int column, int guess) {
   	
	   //kontrollon kolonat
      for(int i=0;i<9;i++) {
         if(sudoku[row][i]==guess)
            return false;
      }
   	//kontrollon rreshtat
      for(int i=0;i<9;i++) {
         if(sudoku[i][column]==guess)
            return false;
      }
   	//kontrollon katrorët
      int row_start=(row/3)*3;
      int column_start=(column/3)*3;
      for(int i=0;i<3;i++) {
         for(int j=0;j<3;j++) {
            if(sudoku[row_start+i][column_start+j]==guess)
               return false;
         }
      }
      return true;
   	
   }
	
   /*Zgjidhja e enigmës sudoku*/
   public static boolean solve() {
      int[] position=find_next_empty(sudoku);
      if(position[0]==-1) {
         return true; //meqenëse nuk ka hapësira boshe, sudoku zgjidhet
      }
      for(int guess=1;guess<10;guess++) {
         if(isValid(sudoku,position[0],position[1],guess)) {
            sudoku[position[0]][position[1]]=guess;
            if(solve()) {
               return true;
            } 
         }
        
         sudoku[position[0]][position[1]]=0;
      
      }
     
      return false;
   }
  
   /*Shfaq sudokun*/
   public static void display(int[][] sudoku){
      for(int i=0;i<sudoku.length;i++){
         if(i%3==0){
            System.out.println();
         }
         for(int j=0;j<sudoku[i].length;j++){
            if(j%3==0){
               System.out.print(" ");
            }
            
            System.out.print(sudoku[i][j]  + " ");
         }
         System.out.println();
      }
   }
}
