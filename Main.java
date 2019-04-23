
/**
* Haley Anderson
* CPSC 406: Algorithm Analysis
* Knights Tour
* Main class
*/

import java.util.*;
import java.lang.String;
import java.io.*;
import java.text.SimpleDateFormat;

public class Main
{
  public static int boardSize; //ask user
  public static int[][] board;

  public static void main(String[] args)
  {
      int startLocR = 0; //ask user
      int startLocC = 0; //ask user
      int wantHeuristic = -1; //ask user

      System.out.println("Welcome to Haley's Knights Tour Solver!");
      Scanner scan = new Scanner(System.in);

      while(true) //board size input
      {
        try
        {
          System.out.println("How large would you like your board to be? Please enter a positive Integer.");
          boardSize = scan.nextInt();
          scan.nextLine();
          break;
        }
        catch(Exception e)
        {
          System.out.println("Please read the directions. Positive numbers only. Try again.\n");
        }
      }

      while(true) //start location input
      {
        try
        {
          System.out.println("Where would you like the Knight to start? X (row) value. Please enter a positive Integer less than the chosen board size. Base 0.");
          startLocR = scan.nextInt();
          scan.nextLine();
          if(startLocR < 0 || startLocR >= boardSize)
          {
            throw new Exception();
          }

          System.out.println("Y (column) value. Please enter a positive Integer less than the chosen board size. Base 0.");
          startLocC = scan.nextInt();
          scan.nextLine();
          if(startLocC < 0 || startLocC >= boardSize)
          {
            throw new Exception();
          }
          break;
        }
        catch(Exception e)
        {
          System.out.println("Please read the directions. Positive numbers only 0 through your board size - 1. Try again.\n");
        }
      }

      while(true) //ask the user which solution they want to use
      {
        try
        {
          System.out.println("Would you like to use brute force or heuristic? 0 for brute force and 1 for heuristic.");
          wantHeuristic = scan.nextInt();
          scan.nextLine();
          if(wantHeuristic != 0 && wantHeuristic != 1) //if its not a 1 or 0 try again.
          {
            throw new Exception();
          }
          break;
        }
        catch(Exception e)
        {
          System.out.println("Please read the directions. 1 or 0 only. Try again.\n");
        }
      }

      board = new int[boardSize][boardSize]; //initializes to user given board size

      for(int i = 0; i < boardSize; i++)
      {
        for(int j = 0; j < boardSize; j++)
        {
            board[i][j] = -1; //intialize the board to -1, as it has not been stopped on yet
        }
      }

      board[startLocR][startLocC] = 0;

      int currentStops = 1; //the current number of stop we are on

      /* date information gathered from stack overflow */
      java.util.Date date = new java.util.Date();
      SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
      System.out.println(sdf.format(date));

      boolean worked;
      if(wantHeuristic == 0)
      {
        worked = moveBF(startLocR, startLocC, currentStops);
      }
      else //so want heuristic = 1 and they do want waldorffs
      {
        worked = moveH(startLocR, startLocC, currentStops);
      }

      if(worked)
      {
        /* date information gathered from stack overflow */
        java.util.Date date2 = new java.util.Date();
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
        System.out.println(sdf.format(date2));
      }
      else
      {
        System.out.println("Knights Tour Failed."); //if it didnt work
      }

      for(int i = 0; i < boardSize; i++)
      {
        for(int j = 0; j < boardSize; j++)
        {
          System.out.print(" " + board[i][j]);
        }
        System.out.println("");
      }
  }


  public static boolean moveBF(int row, int column, int currentStops) //return true if it works, brute force
  {
      if(currentStops == boardSize * boardSize) //so every board spot has been hit
      {
          return true;
      }

      for(int i = 0; i < 8; i++)
      {
          int nextMoveRow = getNextMoveR(row, i);
          int nextMoveColumn = getNextMoveC(column, i);
          if(onBoardAndOpen(nextMoveRow, nextMoveColumn)) //if this new spot is viable
          {
              board[nextMoveRow][nextMoveColumn] = currentStops;
              boolean recursive = moveBF(nextMoveRow, nextMoveColumn, currentStops + 1);
              if(recursive) //recursive to try to keep going
              {
                  return true;
              }
              else
              {
                  board[nextMoveRow][nextMoveColumn] = -1;
              }
          }
      }
      return false;
  }


  public static boolean moveH(int row, int column, int currentStops) //return true if it works, brute force
  {
      int nextMoveRow = 0;
      int nextMoveColumn = 0;

      if(currentStops == boardSize * boardSize) //total number of places the knight could land
      {
          return true;
      }

      for(int i = 0; i < 8; i++)
      {
          int[] arr = checkNeighbors(row, column); //finds which square to move to (must be open & viable)
          nextMoveRow = arr[0];
          nextMoveColumn = arr[1];

          board[nextMoveRow][nextMoveColumn] = currentStops;
          boolean recursive = moveH(nextMoveRow, nextMoveColumn, currentStops + 1);
          if(recursive) //recursive to try to keep going
          {
              return true;
          }
          else
          {
              board[nextMoveRow][nextMoveColumn] = -1;
          }
      }
      return false;
  }


  public static int getNextMoveR(int row, int counter)
  {
      if(counter == 0)
      {
        return row + 1;
      }
      if(counter == 1)
      {
        return row - 1;
      }
      if(counter == 2)
      {
        return row + 1;
      }
      if(counter == 3)
      {
        return row - 1;
      }
      if(counter == 4)
      {
        return row + 2;
      }
      if(counter == 5)
      {
        return row - 2;
      }
      if(counter == 6)
      {
        return row + 2;
      }
      if(counter == 7)
      {
        return row - 2;
      }

      return row;
  }


  public static int getNextMoveC(int column, int counter)
  {
      if(counter == 0)
      {
        return column + 2;
      }
      if(counter == 1)
      {
        return column + 2;
      }
      if(counter == 2)
      {
        return column - 2;
      }
      if(counter == 3)
      {
        return column - 2;
      }
      if(counter == 4)
      {
        return column - 1;
      }
      if(counter == 5)
      {
        return column - 1;
      }
      if(counter == 6)
      {
        return column + 1;
      }
      if(counter == 7)
      {
        return column + 1;
      }

      return column;
  }


  public static int[] checkNeighbors(int row, int column)
  {
    int leastOpenNeighbors = 10;
    int nextMoveRow = 0;
    int nextMoveColumn = 0;

    int potentialLeast = whichHasLeast(row + 1, column + 2); //will check each posible stop to see if valid & # of open neighbors
    if(potentialLeast < leastOpenNeighbors)
    {
      leastOpenNeighbors = potentialLeast;
      nextMoveRow = row + 1;
      nextMoveColumn = column + 2;
    }
    potentialLeast = whichHasLeast(row + 1, column - 2);
    if(potentialLeast < leastOpenNeighbors)
    {
      leastOpenNeighbors = potentialLeast;
      nextMoveRow = row + 1;
      nextMoveColumn = column - 2;
    }
    potentialLeast = whichHasLeast(row - 1, column - 2);
    if(potentialLeast < leastOpenNeighbors)
    {
      leastOpenNeighbors = potentialLeast;
      nextMoveRow = row - 1;
      nextMoveColumn = column - 2;
    }
    potentialLeast = whichHasLeast(row - 1, column + 2);
    if(potentialLeast < leastOpenNeighbors)
    {
      leastOpenNeighbors = potentialLeast;
      nextMoveRow = row - 1;
      nextMoveColumn = column + 2;
    }
    potentialLeast = whichHasLeast(row + 2, column + 1);
    if(potentialLeast < leastOpenNeighbors)
    {
      leastOpenNeighbors = potentialLeast;
      nextMoveRow = row + 2;
      nextMoveColumn = column + 1;
    }
    potentialLeast = whichHasLeast(row + 2, column - 1);
    if(potentialLeast < leastOpenNeighbors)
    {
      leastOpenNeighbors = potentialLeast;
      nextMoveRow = row + 2;
      nextMoveColumn = column - 1;
    }
    potentialLeast = whichHasLeast(row - 2, column - 1);
    if(potentialLeast < leastOpenNeighbors)
    {
      leastOpenNeighbors = potentialLeast;
      nextMoveRow = row - 2;
      nextMoveColumn = column - 1;
    }
    potentialLeast = whichHasLeast(row - 2, column + 1);
    if(potentialLeast < leastOpenNeighbors)
    {
      leastOpenNeighbors = potentialLeast;
      nextMoveRow = row - 2;
      nextMoveColumn = column + 1;
    }

    int[] answer = new int[2];
    answer[0] = nextMoveRow;
    answer[1] = nextMoveColumn;
    return answer;
  }


  public static int whichHasLeast(int row, int column)
  {
    int leastOpenNeighbors = 10;

    if(onBoardAndOpen(row, column)) //will check each posible stop to see if valid & # of open neighbors
    {
      int num = possibleNextSteps(row, column);
      if(num < leastOpenNeighbors)
      {
        leastOpenNeighbors = num;
      }
    }
    return leastOpenNeighbors;
  }


  public static boolean onBoardAndOpen(int row, int column)
  {
    if(row >= boardSize || column >= boardSize || row < 0 || column < 0) //not valid board location
    {
      return false;
    }
    if(board[row][column] != -1)
    {
      return false;
    }

    return true; //valid board location
  }


  public static int possibleNextSteps(int row, int column) //counts the number of next boxes to go to that are on the board and not already visited
  {
    int counter = 0; //counter, max for 8

    if(onBoardAndOpen(row + 1, column + 2)) { counter++; } //will check each posible stop to see if valid
    if(onBoardAndOpen(row + 1, column - 2)) { counter++; }
    if(onBoardAndOpen(row - 1, column - 2)) { counter++; }
    if(onBoardAndOpen(row - 1, column + 2)) { counter++; }
    if(onBoardAndOpen(row + 2, column + 1)) { counter++; }
    if(onBoardAndOpen(row + 2, column - 1)) { counter++; }
    if(onBoardAndOpen(row - 2, column - 1)) { counter++; }
    if(onBoardAndOpen(row - 2, column + 1)) { counter++; }

    return counter;
  }

}
