/*
 * Class Name:    WordMatch
 *
 * Author:        Abhishek Toshwal
 * Student ID:	  19122421
 * Creation Date: Sunday, May 17 2020, 10:47 
 * Last Modified: Sunday, May 31 2020, 13:53
 * Subject CSE5ALG
 * Class Description:
 *
 */
import java.util.*;
//import java.util.LinkedList;
import java.io.*;
public class AVLNode<T extends Comparable<T> >
{
   private T data;
   private AVLNode<T> leftChild;
   private AVLNode<T> rightChild;
   private int height;
   private int count;	// for frequency
   ArrayList<String> neighbours = new ArrayList<>(); // array to store all neighbour

   //static AVLTree<String> dataStructure = new AVLTree<String>();

   public AVLNode(T data)
   {
      this.data = data;
      leftChild = null;
      rightChild = null;
      height = 0;
      count = 1;
   }
   public int getCount() // return frequency of each word
   {
      return count;
   }

   public void addNeighbours(String word) // adds neighbour in the array
   {
      if(!neighbours.contains(word))
      {
         neighbours.add(word);
      }
   }

   public ArrayList<String> getNeighbour() // return the neighbour list
   {
      return neighbours;
   }

   public void setLeftChild(AVLNode<T> leftChild)
   {
      this.leftChild = leftChild;
   }

   public void setRightChild(AVLNode<T> rightChild)
   {
      this.rightChild = rightChild;
   }
   public void setCount()
   {
      count++;
   }

   public void setData(T data)
   {
      this.data = data;

   }

   public void setHeight(int height)
   {
      this.height = height;
   }

   public AVLNode<T> getLeftChild()
   {
      return leftChild;
   }

   public AVLNode<T> getRightChild()
   {
      return rightChild;
   }

   public T getData()
   {
      return data;
   }

   public int getHeight()
   {
      return height;
   }
}
