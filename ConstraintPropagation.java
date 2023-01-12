import java.util.*;


public class ConstraintPropagation {
   public static Cell[][] sudoku = new Cell[9][9]; //Ndërton enigmën duke përdorur qelizat
   public static int[][]  target ={ //Enigma që duam të zgjidhim
            {0,0,0,1,0,5,0,6,8},
            {0,0,0,0,0,0,7,0,1},
            {9,0,1,0,0,0,0,3,0},
            {0,0,7,0,2,6,0,0,0},
            {5,0,0,0,0,0,0,0,3},
            {0,0,0,8,7,0,4,0,0},
            {0,3,0,0,0,0,8,0,5},
            {1,0,5,0,0,0,0,0,0},
            {7,9,0,4,0,1,0,0,0},          
              };

   public static void main(String[] args) {
      init(); //Inicializoni sudokun nga grupi i synuar
      System.out.println("Constraint Propagation Algorithm");
           long startMilliSecond=System.currentTimeMillis(); //Start time
           long stopMilliSecond=0;//Koha e përfundimit
         //  System.out.println("start "+startMilliSecond);
        
      while (!isOver(sudoku)){
        
            //eliminate start--------------------
         eliminate(sudoku);
         set_single_value(sudoku);
         eliminate(sudoku);
            //eliminate stop--------------------
      
            //only-row start--------------------
         only_choice_row(sudoku);
            //only-row stop--------------------
      
            //eliminate start--------------------
         eliminate(sudoku);
         set_single_value(sudoku);
         eliminate(sudoku);
            //eliminate stop--------------------
      
            //only-col start--------------------
         only_choice_col(sudoku);
            //only-col stop--------------------
      
         //eliminate start--------------------
         eliminate(sudoku);
         set_single_value(sudoku);
         eliminate(sudoku);
            //eliminate stop--------------------
      
            //only-box start--------------------
         only_choice_box(sudoku);
            //only-box stop--------------------
      
            //eliminate start--------------------
         eliminate(sudoku);
         set_single_value(sudoku);
         eliminate(sudoku);
            //eliminate stop--------------------
      }
      
        stopMilliSecond=System.currentTimeMillis();
      // System.out.println("stop "+ stopMilliSecond);
      display(convert(sudoku)); //Display sudoku
     System.out.println("Time: "+(stopMilliSecond-startMilliSecond)+" ms");
   
   }
   /*Inicializoni qelizat sudoku dhe përcaktoni domenin e secilës qelizë*/
   public static void init(){
      for(int i =0;i< sudoku.length;i++){
         for(int j = 0;j<sudoku[i].length;j++){
            if(target[i][j]==0){
               int[] domain =new int[9];
               for(int k=0;k<domain.length;k++){
                  domain[k]=k+1;
               }
            
            
               sudoku[i][j] = new Cell(i,j,0,domain, false, false);
            }
            else{
               int[] domain = new int[9];
               sudoku[i][j] = new Cell(i,j,target[i][j],domain, true,false);
            }
         
         }
      }
   
   }
/*Vendos vlerën e vetme të qelizës nëse domeni përmban vetëm një vlerë*/
   public static Cell[][] set_single_value(Cell[][] sudoku){
      for(int i=0;i<sudoku.length;i++){
         for(int j=0;j<sudoku[i].length;j++){
            if(sudoku[i][j].getDomain().length==1){
               sudoku[i][j].setValue(sudoku[i][j].getDomain()[0]);
               sudoku[i][j].setFound(true);
            }
         }
      }
      return sudoku;
   }
	/*Rikthen true nëse grupi përmban elementin specifik*/
   public static boolean contains(int[] array,int number) {
      for(int i=0;i<array.length;i++) {
         if(array[i]==number) {
            return true;
         }
      }
      return false;
   }
	
     
  /*Eliminon vlerën e një qelize aktuale nga rreshti, kolona dhe katrori në të cilin ndodhet ajo qelizë*/ 
   public static Cell[][] eliminate(Cell[][] sudoku){
   //Hiqeni nga rreshti
      for(int row = 0;row<sudoku.length;row++){
         for(int col = 0;col<sudoku[row].length;col++){
         
            if(!sudoku[row][col].isFound()){
               ArrayList<Integer> containsNumbers = new  ArrayList<Integer>();
            
               for(int i=0;i<sudoku.length;i++){
                  if(sudoku[row][i].isFound()){
                     if(!containsNumbers.contains(sudoku[row][i].getValue())){
                        containsNumbers.add(sudoku[row][i].getValue());
                     }
                  
                  }
               
               }
               //Hiqeni nga kolona
               for(int i =0;i<sudoku.length;i++){
                  if(sudoku[i][col].isFound()){
                     if(!containsNumbers.contains(sudoku[i][col].getValue())){
                        containsNumbers.add(sudoku[i][col].getValue());
                     }
                  
                  }              
               
               }
               //Hiq nga katrori 3x3
               int r = row - row % 3;
               int c = col - col % 3;
               
               for (int i = r; i < r + 3; i++){
                  for (int j = c; j < c + 3; j++){
                     if (sudoku[i][j].isFound()){
                        if (!containsNumbers.contains(sudoku[i][j].getValue())){
                           containsNumbers.add(sudoku[i][j].getValue());
                        }
                     }
                  }
               }
               
               for (int i =0; i<containsNumbers.size();i++){
               //Nëse vlera e qelizës është e pranishme në kolonën, rreshtin ose katrorin në të cilin ndodhet qeliza, hiqeni atë nga lista e vlerave
                  if (contains(sudoku[row][col].getDomain(),containsNumbers.get(i))){
                     sudoku[row][col].removeElement(containsNumbers.get(i));
                  }
               }
            
               containsNumbers.clear(); //Fshini elementet nga lista përmban numra
               
            }
         }
      }
      return sudoku;
   }
   /*Vendosni vlerën unike të domenit në krahasim me katrorin */
   public static Cell[][] only_choice_box(Cell[][] sudoku){
      for (int row=0; row<sudoku.length ; row++){
         for (int col=0; col<sudoku[row].length ; col++){
            boolean shouldContinue1 = true;
                //shkoni çdo qelizë të vetme që nuk gjendet
            if (!sudoku[row][col].isFound()){
                    //bëje këtë qelizë aktuale Qelizë pune 
               sudoku[row][col].setCurrent(true);
               for (int i =0; i< sudoku[row][col].getDomain().length; i++){
                       
                  if (shouldContinue1){
               //do të shikojë domenin e çdo qelize në kutinë 3x3, nëse gjetja dhe aktuale është false në të njëjtën kohë
                     int r = row - row % 3;
                     int c = col - col % 3;
                     boolean shouldContinue = true;
                     for (int a = r; a< r+3; a++){
                        for (int b = c; b< c+3; b++) {
                        
                           if (shouldContinue) {
                              if (!sudoku[a][b].isFound()) {
                                 if (!sudoku[a][b].getCurrent()) {
                                                //nëse ndonjë qelizë në kutinë 3x3 përmban këtë numër si numër domeni,
                                	 			// pushim për ndryshimin e numrit të përkohshëm të domenit
                                    if (contains(sudoku[a][b].getDomain(),sudoku[row][col].getDomain()[i])) {
                                                    //përdorni shouldCountinue për të mos hyrë më brenda
                                       shouldContinue = false;
                                       break;
                                    }}}
                          //nëse në qelizën e fundit të kutisë(fundit) dhe nuk gjendet ndonjë numër i përbashkët
                              if (a == r + 2 && b == c + 2) {
                                            //e bëjnë këtë qelizë siç është gjetur
                                 sudoku[row][col].setCurrent(false);
                                 sudoku[row][col].setFound(true);
                                 sudoku[row][col].setValue(sudoku[row][col].getDomain()[i]);
                                 sudoku[row][col].clearDomain();
                                 shouldContinue1= false;
                              }}}}
                  }else {
                     break;
                  }
               }
                    //puna e përfunduar në këtë qelizë
               sudoku[row][col].setCurrent(false);
            }
         }
      }
      return sudoku;
   }
   /*Vendosni vlerën unike të domenit në krahasim me kolonën */
   public static Cell[][] only_choice_col(Cell[][] sudoku){
   
      for (int row=0; row<sudoku.length;row++){
         for (int col =0; col< sudoku[row].length; col++){
            boolean shouldContinue = true;
                //shkoni çdo qelizë të vetme që nuk gjendet
            if (!sudoku[row][col].isFound()){
                    //bëje këtë qelizë aktuale Qelizë pune
               sudoku[row][col].setCurrent(true);
            
               for (int i =0; i< sudoku[row][col].getDomain().length; i++){
                  if (shouldContinue){
                            //do të shikojë domenin e çdo qelize në këtë kolonë,
                      // nëse gjetja dhe aktuale është false në të njëjtën kohë
                     for (int c = 0; c<sudoku.length; c++){
                        if (!sudoku[c][col].isFound()){
                           if (!sudoku[c][col].getCurrent()){
                        	 //nëse ndonjë qelizë në këtë kolonë përmban këtë numër si numër domeni, thyejeni për
                               // ndryshimi i numrit të përkohshëm të domenit
                              if (contains(sudoku[c][col].getDomain(),sudoku[row][col].getDomain()[i])){
                                 break;
                              }
                           }
                        }
                      //nëse në qelizën e fundit të kësaj kolone dhe nuk gjendet ndonjë domen i përbashkët
                        if (c == 8){
                                    //e bëjnë këtë qelizë siç është gjetur
                           sudoku[row][col].setCurrent(false);
                           sudoku[row][col].setFound(true);
                           sudoku[row][col].setValue(sudoku[row][col].getDomain()[i]);
                           sudoku[row][col].clearDomain();
                           shouldContinue = false;
                        }
                     }
                  }else {
                     break;
                  }
               }
                    //puna e përfunduar në këtë qelizë
               sudoku[row][col].setCurrent(false);
            }
         }
      }
      return sudoku;
   }
   /*Vendosni vlerën unike të domenit në krahasim me rreshtin */
   public static Cell[][] only_choice_row(Cell[][] sudoku){
   
      for (int row=0; row < sudoku.length; row++){
         for (int col =0; col < sudoku[row].length; col++){
            boolean shouldContinue = true;
                //shkoni çdo qelizë të vetme që nuk gjendet
            if (!sudoku[row][col].isFound()){
                    //bëje këtë qelizë aktuale Qelizë pune
               sudoku[row][col].setCurrent(true);
            
               for (int i=0;i< sudoku[row][col].getDomain().length;i++){
               
                  if (shouldContinue){
                  
                	//do të shikojë domenin e çdo qelize në këtë rresht,
                      // nëse gjetja dhe aktuale është false në të njëjtën kohë
                     for (int r = 0; r<sudoku.length; r++){
                        if (!sudoku[row][r].isFound()){
                           if (sudoku[row][r].getCurrent()){
                        	 //nëse ndonjë qelizë në këtë rresht përmban këtë numër si numër domeni, thyejeni për
                               // ndryshimi i numrit të përkohshëm të domenit
                              if (contains(sudoku[row][r].getDomain(), sudoku[row][col].getDomain()[i])){
                                 break;
                              }
                           }
                        }
                      //nëse në qelizën e fundit të këtij rreshti dhe nuk gjendet ndonjë numër i zakonshëm domeni
                        if (r == 8){
                                    //e bëjnë këtë qelizë siç është gjetur
                           sudoku[row][col].setCurrent(false);
                           sudoku[row][col].setFound(true);
                           sudoku[row][col].setValue(sudoku[row][col].getDomain()[i]);
                           sudoku[row][col].clearDomain();
                           shouldContinue = false;
                        }
                     }
                  }else {  break;                   }
               }
                    //puna e përfunduar në këtë qelizë
               sudoku[row][col].setCurrent(false);
            }
         }
      }
      return sudoku;
   }
    /*Kontrollon nëse sudoku është zgjidhur saktë*/
   public static boolean checkSudoku(Cell[][] sudoku){
   
      if (checkSudokuRow(sudoku) && checkSudokuColumn(sudoku)){
         return true;
      }else {
         return false;
      }
   
   }
   /*Kontrollon rreshtat e sudokut*/
   public static boolean checkSudokuRow(Cell[][] sudoku){
   
        //ruani të gjithë Numrat e këtij rreshti në këtë listë
      for (int row=0;row < sudoku.length ; row++){
         ArrayList<Integer> numberList = new ArrayList<>();
         for (int i = 0; i<sudoku.length ;i++){
                //if there is going to be any duplicate
            if (numberList.contains(sudoku[row][i].getValue())){
               return false;
            }else {
                    //else thjesht shtoni këtë listë
               numberList.add(sudoku[row][i].getValue());
            }
         }
      }
   
      return true;
   
   }
   /*Kontrollon kolonat e sudokut*/
   public static boolean checkSudokuColumn(Cell[][] sudoku){
   
      for (int col=0; col<sudoku.length; col++){
         ArrayList<Integer> numberList = new ArrayList<>();
         for (int i = 0; i< sudoku.length ; i++){
                //nëse do të ketë ndonjë dublikatë
            if (numberList.contains(sudoku[i][col].getValue())){
               return false;
            }else {
                    //përndryshe thjesht shtoni këtë listë
               numberList.add(sudoku[i][col].getValue());
            }
         }
      }
      return true;
   
   }
   /*Sinjalet nëse sudoku zgjidhet*/
   public static boolean isOver(Cell[][] sudoku){
   
      for (int i=0;i<sudoku.length;i++){
         for (int j =0; j<sudoku[i].length; j++){
            if (!sudoku[i][j].isFound()){
               return false;
            }
         }
      }
      return true;
   }
   
   /*Shndërron një grup objektesh Celular në një grup*/
   public static  int[][] convert(Cell[][] sudoku){
      int[][] convertSudoku=new int[9][9];
      for(int i=0;i<sudoku.length;i++){
         for(int j=0;j<sudoku[i].length;j++){
            convertSudoku[i][j]=sudoku[i][j].getValue();
         }
      }
      return convertSudoku;
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