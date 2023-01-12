import java.util.ArrayList;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
/*Ndërton një qelizë të vetme të enigmës Sudoku*/
public class Cell {
   private int[] domain; //Grupi i vlerave që një qelizë mund të përmbajë
   private  int row;  //Rreshti në të cilin gjendet qeliza
   private  int col;  //Kolona në të cilën gjendet qeliza
   private int value; //Vlera e qelizës
   private boolean found;   //Nëse vlera e qelizës përcaktohet, gjetja bëhet e vërtetë
   private boolean current; // Tregon qelizën në të cilën jemi aktualisht
   public Cell(int row, int col,int value, int[] domain, boolean found, boolean current) {
      this.row = row;
      this.col = col;
      this.value=value;
      this.domain=domain;
      this.found=found;
      this.current = current;	
   }
	/* Kthen rreshtin*/
   public int getRow() {
      return row;
   }
	/*Kthen kolonen*/
   public int getCol() {
      return col;
   }
   /*Rikthen vlerën e qelizës*/
   public int getValue(){
      return value;
   }
   /*Vendos një vlerë të re për vlerën e qelizës*/
   public void setValue(int value){
      this.value=value;
   }
   /*Rikthen vlerën e ndryshores së gjetur*/
   public boolean isFound(){
      return found;
   }
   /*Vendos një vlerë të re për variablin e gjetur*/
   public void setFound(boolean found){
      this.found=found;
   }
   /*Rikthen vlerën aktuale*/
   public boolean getCurrent(){
      return current;
   }
   /*Vendos një vlerë të re për ndryshoren aktuale*/
   public void setCurrent(boolean current){
      this.current = current;
   }
	/* Kthen domenin e qelizës*/
   public  int[]  getDomain() {
   	
      return domain;	
   }
	/*Heq një element nga domeni*/
   public  void removeElement(int element) {
      List<Integer> list = Arrays.stream(domain).boxed().collect(Collectors.toList());
      list.remove(new Integer(element));
      domain = list.stream().mapToInt(i->i).toArray();
   	 
   }
   /*Printon domenin*/
   public void printDomain(){
      for(int i = 0;i<domain.length;i++){
         System.out.print(domain[i] + " ");
      
      }
   }
 /*Pastro përmbajtjen e domenit*/
   public void clearDomain(){
      List<Integer> list = Arrays.stream(domain).boxed().collect(Collectors.toList());
      list.clear();            
      domain = list.stream().mapToInt(i->i).toArray();    
   
   }
    /*Vendos një vlerë të re në grupin e domenit*/
   public void setDomain(int value){
      List<Integer> list = Arrays.stream(domain).boxed().collect(Collectors.toList());
      
      for(int i=1;i<10;i++){
         if(i!=value){
            list.remove(new Integer(i));
            
            
         }
      }
      domain = list.stream().mapToInt(i->i).toArray();
   }
   
   
}