/**
    * ThreeInaRow.java
    * Programming Assignment 2
    * @author Bikash Thapa
    * Due: 04/13/2017
    * A simple program that solves the Three in a row puzzle by using backtracking. Implements a class ThreeInaRow.java
      to create the solution for the puzzle by taking its initial state from the file, then creates a solution for the puzzle.
      It takes two helper private method to fill the puzzle with blue and white squares where each row and column has same number of
      blue and white squares and no three same sequence of sqaures of blue or white on either row or column. 
    *
    */  
     
import java.io.*;
import java.util.*;

public class ThreeInaRow
{
   // Declare instance variables
   private char Matrix [][];
   private char MatrixPromising [][];
   private char empty='\0';
   private int i,j=0;
   private int count=0;              
   private int row=0;
   private int noOfColor=0;   // Number of color of squares of Blue and White in each row and column of matrix
   private String color="";
   private int index=0;
   private boolean Solution= true;
   
 /* A constructor to initialize the two-dimensional matrix from the text file which is passed as argument to another method. It also determines 
    the number of white and blue squares that needs be filled in the matrix.
    * @param filename- name of the text file that contain the inital size
    * @param N- the size of two dimesional matrix
    */
   
   public ThreeInaRow (String filename, int N)
   {
      noOfColor=N/2;
      count=N*N; // Total number of Blue and White color of required matrix
      Matrix= new char [N][N];
      MatrixPromising= new char [N+4][N+4];
      for ( int k=0; k<noOfColor; k++)
      {
         for (int i=0; i<N; i++)
         {
            color+="WB";
         }
      }
      try 
      { 
         File infile=new File (filename);
         Scanner sc=new Scanner (infile);
         while (sc.hasNextLine())
         {
            String line=sc.nextLine();
            if(line.length()<N)
            {
               while(line.length()!=N)
                  line=line+" ";
            }
            
            for (int n=0;n<N;n++)
            {
               char a=line.charAt(n);
               if ( a=='W' || a=='B')
               { 
                  Matrix[row][n]=a;
                  MatrixPromising[row+2][n+2]=a;
                  // Subtract the no of Blue and white if already filled 
                  count --;
               }
            }
            row++;
          }
          
         if( ThreeInRow (Matrix,color.charAt(index),index,i,j)==false)
         {
            Solution = false;
         }
      }
      catch (FileNotFoundException e)
      {
         e.printStackTrace();
      }
   }

/* A boolean method that takes matrix, and the character that needs to be inserted after checking the required condition from 
   promising method. This method adds the blue and white squares in matrix recursively until the solution is found by solving
   the puzzle through backtracking.
   * @param board- A two dimesional matrix whose solution needs to be found 
   * @param fill- A square B or W which needs to filled after meeting requirements
   * @param k- position of the sqaure B or W which needs to be inserted
   * @param row- the number of rows in a matix 
   * @param col- the number of column in a matrix
   * @return true- if all the squares are assigned in right way  matching the required condition 
   */

   private boolean ThreeInRow(char board[][],char fill,int k, int row,int col)
   {
     /************************
     Base Case for Recursion
     ************************/
      if ( k==count)
      {
         return true;
      }
      else
      {
         if(row<board.length)
         {
            if (col < board[row].length) 
            {
               if(board[row][col]==empty)
               {
                  if ( promissing(fill,row,col))
                  {
                     board[row][col]=fill;
                     MatrixPromising[row+2][col+2]=fill;
                     
                     /***********************************************
                          Recursion case and return if success
                      **********************************************/
                     if(ThreeInRow(board,color.charAt(k+1),k+1,row,col))
                     {
                        return true;
                     }
                     /*****************************************
                            Backtracking 
                       Unassigned value if boolean return false
                      *****************************************/
                     board[row][col]=empty;
                     MatrixPromising[row+2][col+2]=empty;
                  }
                   
                  if ( promissing(color.charAt(k+1),row,col))
                  {
                     board[row][col]=color.charAt(k+1);
                     MatrixPromising[row+2][col+2]=color.charAt(k+1);
                     
                     if(ThreeInRow(board,color.charAt(k+1),k+1,row,col))
                     {
                        return true;
                     }
                     
                     board[row][col]=empty;   
                     MatrixPromising[row+2][col+2]=empty;
                  }
                                                            
               }
               /*************************************
                         Increase the Column
                ************************************/
               else
               {
                  return (ThreeInRow(board,fill,k,row,col+1));
                               
               }
            }
            
            /****************************************
               Increase the Row and set Column 0
             ***************************************/
            else
            {
               return (ThreeInRow(board,fill,k,row+1,0));
            }
            
         }             
                           
      } 
      return false;            
   }
   
/**
   * A method that display the blue and white squares as B and W as the solution of puzzle in the form of matrix with
     complete solution with correct formatting.
   * @return solution- A string which contains the contents of the matrix in row-major order.
   */

   public String solution ()
   {
      String solution = "";
      if (Solution==false)
      {
         solution="NONE";
      }
      else
      {
         for (int row = 0; row <Matrix.length; row++) 
         {
            solution+="|";
            for (int col = 0; col <Matrix[row].length; col++) 
            {
               solution+= Matrix[row][col];
            }
         }
      }
      return solution;
   }
   
/* The  method returs the correct solution of puzzle in two dimesnional grid.
   @return reQuired- A String that gives the correct solution of puzzle
   */   
   public String toString ()
   {
      System.out.println("\nA solution of problem in two dimensional-grid");
      String reQuired="";
      for (int row = 0; row <Matrix.length; row++) 
      {
         for (int col = 0; col <Matrix[row].length; col++) 
         {
            reQuired+=" | "+ Matrix[row][col];
         }
         reQuired +=" |\n";
      }
      return reQuired;
   }
   
/**
   * This method returns a boolean which indicates whether it will be promising to assign squares to the given row or column location of matrix. 
     The suares will be added if there are no three same sequence of squares and also there should be same number of blue and white sqaures in row
     and column.
   * @param checked- A square B or W which needs to filled after meeting requirements
   * @param x- A place in row in which a square is inserted after condition is met
   * @param y- A place in column in which a square is inserted after condition is met
   * @return promised- true if it is legal to insert color in this matrix.
   */   
 
   private boolean promissing ( char checked, int x, int y)
   {
      x=x+2; // MatrixPromising [x+2][y+2]=Matrix[x][y] 
      y=y+2;
      int n=0,m=0;
      boolean promised=true;
      /*********************************************************
         Check in right and Left
      *********************************************************/
      if ((checked==MatrixPromising[x-1][y] && checked ==MatrixPromising[x-2][y]))
      {
         promised= false;
      }
      
      if ((checked==MatrixPromising[x-1][y] && checked ==MatrixPromising[x+1][y]))
      {
         promised= false;
      }
   
      if ((checked==MatrixPromising[x+1][y] && checked==MatrixPromising[x+2][y]))
      {
         promised=false;
      }
   
     /*********************************************************
                    Check Up and Down
      *********************************************************/
      if ((checked==MatrixPromising[x][y-1] && checked ==MatrixPromising[x][y-2] ))
      {
         promised=false;
      }
      
      if ((checked==MatrixPromising[x][y-1] && checked ==MatrixPromising[x][y+1] ))
      {
         promised=false;
      }
   
      if ((checked==MatrixPromising[x][y+1] && checked==MatrixPromising[x][y+2]))
      {
         promised=false;
      }
      
      /*************************************
                Check in Column 
       *************************************/
      for (int col = 2; col <MatrixPromising[0].length; col++) 
      {
         if (checked == MatrixPromising[x][col])
         { 
            n++;
         }
      }
       /*************************************
                  Check in Row 
        *************************************/
      for ( int row=2; row <MatrixPromising.length; row++)
      {
         if (checked==MatrixPromising[row][y])
         {
            m++;
         }
      }
      
      /*************************************************************
      If required no of Blue and white is already there return False
      **************************************************************/
      if (n >=noOfColor )
      {
         promised=false;
      }
      
      if (m>=noOfColor)
      {
         promised=false;
      }
     
      return promised;
   }     
}
