import java.util.ArrayList;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
/*Nd�rton nj� qeliz� t� vetme t� enigm�s Sudoku*/
public class Cell {
   private int[] domain; //Grupi i vlerave q� nj� qeliz� mund t� p�rmbaj�
   private  int row;  //Rreshti n� t� cilin gjendet qeliza
   private  int col;  //Kolona n� t� cil�n gjendet qeliza
   private int value; //Vlera e qeliz�s
   private boolean found;   //N�se vlera e qeliz�s p�rcaktohet, gjetja b�het e v�rtet�
   private boolean current; // Tregon qeliz�n n� t� cil�n jemi aktualisht
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
   /*Rikthen vler�n e qeliz�s*/
   public int getValue(){
      return value;
   }
   /*Vendos nj� vler� t� re p�r vler�n e qeliz�s*/
   public void setValue(int value){
      this.value=value;
   }
   /*Rikthen vler�n e ndryshores s� gjetur*/
   public boolean isFound(){
      return found;
   }
   /*Vendos nj� vler� t� re p�r variablin e gjetur*/
   public void setFound(boolean found){
      this.found=found;
   }
   /*Rikthen vler�n aktuale*/
   public boolean getCurrent(){
      return current;
   }
   /*Vendos nj� vler� t� re p�r ndryshoren aktuale*/
   public void setCurrent(boolean current){
      this.current = current;
   }
	/* Kthen domenin e qeliz�s*/
   public  int[]  getDomain() {
   	
      return domain;	
   }
	/*Heq nj� element nga domeni*/
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
 /*Pastro p�rmbajtjen e domenit*/
   public void clearDomain(){
      List<Integer> list = Arrays.stream(domain).boxed().collect(Collectors.toList());
      list.clear();            
      domain = list.stream().mapToInt(i->i).toArray();    
   
   }
    /*Vendos nj� vler� t� re n� grupin e domenit*/
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