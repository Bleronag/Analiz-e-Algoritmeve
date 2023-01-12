import java.util.*;


public class ConstraintPropagation {
   public static Cell[][] sudoku = new Cell[9][9]; //Nd�rton enigm�n duke p�rdorur qelizat
   public static int[][]  target ={ //Enigma q� duam t� zgjidhim
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
           long stopMilliSecond=0;//Koha e p�rfundimit
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
   /*Inicializoni qelizat sudoku dhe p�rcaktoni domenin e secil�s qeliz�*/
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
/*Vendos vler�n e vetme t� qeliz�s n�se domeni p�rmban vet�m nj� vler�*/
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
	/*Rikthen true n�se grupi p�rmban elementin specifik*/
   public static boolean contains(int[] array,int number) {
      for(int i=0;i<array.length;i++) {
         if(array[i]==number) {
            return true;
         }
      }
      return false;
   }
	
     
  /*Eliminon vler�n e nj� qelize aktuale nga rreshti, kolona dhe katrori n� t� cilin ndodhet ajo qeliz�*/ 
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
               //N�se vlera e qeliz�s �sht� e pranishme n� kolon�n, rreshtin ose katrorin n� t� cilin ndodhet qeliza, hiqeni at� nga lista e vlerave
                  if (contains(sudoku[row][col].getDomain(),containsNumbers.get(i))){
                     sudoku[row][col].removeElement(containsNumbers.get(i));
                  }
               }
            
               containsNumbers.clear(); //Fshini elementet nga lista p�rmban numra
               
            }
         }
      }
      return sudoku;
   }
   /*Vendosni vler�n unike t� domenit n� krahasim me katrorin */
   public static Cell[][] only_choice_box(Cell[][] sudoku){
      for (int row=0; row<sudoku.length ; row++){
         for (int col=0; col<sudoku[row].length ; col++){
            boolean shouldContinue1 = true;
                //shkoni �do qeliz� t� vetme q� nuk gjendet
            if (!sudoku[row][col].isFound()){
                    //b�je k�t� qeliz� aktuale Qeliz� pune 
               sudoku[row][col].setCurrent(true);
               for (int i =0; i< sudoku[row][col].getDomain().length; i++){
                       
                  if (shouldContinue1){
               //do t� shikoj� domenin e �do qelize n� kutin� 3x3, n�se gjetja dhe aktuale �sht� false n� t� nj�jt�n koh�
                     int r = row - row % 3;
                     int c = col - col % 3;
                     boolean shouldContinue = true;
                     for (int a = r; a< r+3; a++){
                        for (int b = c; b< c+3; b++) {
                        
                           if (shouldContinue) {
                              if (!sudoku[a][b].isFound()) {
                                 if (!sudoku[a][b].getCurrent()) {
                                                //n�se ndonj� qeliz� n� kutin� 3x3 p�rmban k�t� num�r si num�r domeni,
                                	 			// pushim p�r ndryshimin e numrit t� p�rkohsh�m t� domenit
                                    if (contains(sudoku[a][b].getDomain(),sudoku[row][col].getDomain()[i])) {
                                                    //p�rdorni shouldCountinue p�r t� mos hyr� m� brenda
                                       shouldContinue = false;
                                       break;
                                    }}}
                          //n�se n� qeliz�n e fundit t� kutis�(fundit) dhe nuk gjendet ndonj� num�r i p�rbashk�t
                              if (a == r + 2 && b == c + 2) {
                                            //e b�jn� k�t� qeliz� si� �sht� gjetur
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
                    //puna e p�rfunduar n� k�t� qeliz�
               sudoku[row][col].setCurrent(false);
            }
         }
      }
      return sudoku;
   }
   /*Vendosni vler�n unike t� domenit n� krahasim me kolon�n */
   public static Cell[][] only_choice_col(Cell[][] sudoku){
   
      for (int row=0; row<sudoku.length;row++){
         for (int col =0; col< sudoku[row].length; col++){
            boolean shouldContinue = true;
                //shkoni �do qeliz� t� vetme q� nuk gjendet
            if (!sudoku[row][col].isFound()){
                    //b�je k�t� qeliz� aktuale Qeliz� pune
               sudoku[row][col].setCurrent(true);
            
               for (int i =0; i< sudoku[row][col].getDomain().length; i++){
                  if (shouldContinue){
                            //do t� shikoj� domenin e �do qelize n� k�t� kolon�,
                      // n�se gjetja dhe aktuale �sht� false n� t� nj�jt�n koh�
                     for (int c = 0; c<sudoku.length; c++){
                        if (!sudoku[c][col].isFound()){
                           if (!sudoku[c][col].getCurrent()){
                        	 //n�se ndonj� qeliz� n� k�t� kolon� p�rmban k�t� num�r si num�r domeni, thyejeni p�r
                               // ndryshimi i numrit t� p�rkohsh�m t� domenit
                              if (contains(sudoku[c][col].getDomain(),sudoku[row][col].getDomain()[i])){
                                 break;
                              }
                           }
                        }
                      //n�se n� qeliz�n e fundit t� k�saj kolone dhe nuk gjendet ndonj� domen i p�rbashk�t
                        if (c == 8){
                                    //e b�jn� k�t� qeliz� si� �sht� gjetur
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
                    //puna e p�rfunduar n� k�t� qeliz�
               sudoku[row][col].setCurrent(false);
            }
         }
      }
      return sudoku;
   }
   /*Vendosni vler�n unike t� domenit n� krahasim me rreshtin */
   public static Cell[][] only_choice_row(Cell[][] sudoku){
   
      for (int row=0; row < sudoku.length; row++){
         for (int col =0; col < sudoku[row].length; col++){
            boolean shouldContinue = true;
                //shkoni �do qeliz� t� vetme q� nuk gjendet
            if (!sudoku[row][col].isFound()){
                    //b�je k�t� qeliz� aktuale Qeliz� pune
               sudoku[row][col].setCurrent(true);
            
               for (int i=0;i< sudoku[row][col].getDomain().length;i++){
               
                  if (shouldContinue){
                  
                	//do t� shikoj� domenin e �do qelize n� k�t� rresht,
                      // n�se gjetja dhe aktuale �sht� false n� t� nj�jt�n koh�
                     for (int r = 0; r<sudoku.length; r++){
                        if (!sudoku[row][r].isFound()){
                           if (sudoku[row][r].getCurrent()){
                        	 //n�se ndonj� qeliz� n� k�t� rresht p�rmban k�t� num�r si num�r domeni, thyejeni p�r
                               // ndryshimi i numrit t� p�rkohsh�m t� domenit
                              if (contains(sudoku[row][r].getDomain(), sudoku[row][col].getDomain()[i])){
                                 break;
                              }
                           }
                        }
                      //n�se n� qeliz�n e fundit t� k�tij rreshti dhe nuk gjendet ndonj� num�r i zakonsh�m domeni
                        if (r == 8){
                                    //e b�jn� k�t� qeliz� si� �sht� gjetur
                           sudoku[row][col].setCurrent(false);
                           sudoku[row][col].setFound(true);
                           sudoku[row][col].setValue(sudoku[row][col].getDomain()[i]);
                           sudoku[row][col].clearDomain();
                           shouldContinue = false;
                        }
                     }
                  }else {  break;                   }
               }
                    //puna e p�rfunduar n� k�t� qeliz�
               sudoku[row][col].setCurrent(false);
            }
         }
      }
      return sudoku;
   }
    /*Kontrollon n�se sudoku �sht� zgjidhur sakt�*/
   public static boolean checkSudoku(Cell[][] sudoku){
   
      if (checkSudokuRow(sudoku) && checkSudokuColumn(sudoku)){
         return true;
      }else {
         return false;
      }
   
   }
   /*Kontrollon rreshtat e sudokut*/
   public static boolean checkSudokuRow(Cell[][] sudoku){
   
        //ruani t� gjith� Numrat e k�tij rreshti n� k�t� list�
      for (int row=0;row < sudoku.length ; row++){
         ArrayList<Integer> numberList = new ArrayList<>();
         for (int i = 0; i<sudoku.length ;i++){
                //if there is going to be any duplicate
            if (numberList.contains(sudoku[row][i].getValue())){
               return false;
            }else {
                    //else thjesht shtoni k�t� list�
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
                //n�se do t� ket� ndonj� dublikat�
            if (numberList.contains(sudoku[i][col].getValue())){
               return false;
            }else {
                    //p�rndryshe thjesht shtoni k�t� list�
               numberList.add(sudoku[i][col].getValue());
            }
         }
      }
      return true;
   
   }
   /*Sinjalet n�se sudoku zgjidhet*/
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
   
   /*Shnd�rron nj� grup objektesh Celular n� nj� grup*/
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