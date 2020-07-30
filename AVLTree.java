/*
 * Class Name:    WordMatch
 *
 * Author:        Abhishek Toshwal
 * Student ID:	  19122421
 * Creation Date: Sunday, May 17 2020, 10:47 
 * Last Modified: Sunday, May 31 2020, 13:54
 * Subject CSE5ALG
 * Class Description:
 *
 */

import java.util.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.PrintWriter;

public class AVLTree
{

   private AVLNode<String> root;
   private AVLNode<String> tempRoot;
   PrintWriter pw;
   Pattern p;
   Matcher m;  


   public AVLTree(PrintWriter pw)
   {

      root = null;
      tempRoot = null;
      this.pw = pw;
   }


   public void printInorder(AVLNode<String> node) //traversing the data inorder sorting
   { 

      if (node == null) 
         return; 

      // first recur on left child 
      printInorder(node.getLeftChild()); 

      // then writing the data of node in ascending order & finding the neighbours
      findNeighbour(node.getData());
      //  System.out.println(node.getData()); 
      pw.println(node.getData() + "	" + node.getCount() + "	" + node.getNeighbour()	);
      //getNode(node);

      /* now recur on right child */
      printInorder(node.getRightChild()); 
   } 

   void printInorder()
	{
	   printInorder(root); 
	}







   public void findNeighbour(String word)
   {
      displaySubtreePrefixOrder(root, word);
   }

   private void displaySubtreePrefixOrder(AVLNode<String> localRoot, String word)
   {
      int counter =0; // to check words differ by only one char
      if (localRoot != null)
      {

         if(localRoot.getData().length() == word.length() && localRoot.getData() != word) // length is same but words are not same condition of neighbour
         {
            for(int i = 0; i < word.length(); i++)
            {
               if(word.charAt(i) == localRoot.getData().charAt(i) )
               {
                  counter++; //checking how many character are same
               }

            }
            if(counter == (word.length()-1)) // if both words only differ by 1 character 
            {
               findRoot(word).addNeighbours(localRoot.getData()); //finding the root of that word and storing data in beighbour array
            }
            counter =0;	//resetting counter for new matches
         }


         displaySubtreePrefixOrder(localRoot.getLeftChild(), word);
         displaySubtreePrefixOrder(localRoot.getRightChild(), word);
      }
   }




   public void printP(AVLNode<String> localRoot, String pattern, PrintWriter pw1) // pattern finder method
   { 	
      String patternCopy = pattern;

      if (localRoot == null) 
         return; 


      printP(localRoot.getLeftChild(), pattern, pw1); //first finding pattern in all left nodes 

      patternCopy = patternCopy.toLowerCase();
      patternCopy = patternCopy.replaceAll("\\?", "\\."); //using regex and replacing ? wild card with .
      boolean found = false;

      if(patternCopy.contains("*"))
      {


         patternCopy = patternCopy.substring(0, pattern.length()-1);//.. replacing "* with .*$"
         patternCopy = patternCopy.concat(".*$");

         //System.out.println(patternCopy);

         //System.out.println(pattern);

      }



      if (localRoot != null)
      {
         p = Pattern.compile(patternCopy);
         m = p.matcher(localRoot.getData());
         found = m.matches();
         if(found) //if match found
         {
            //System.out.println(localRoot.getData() + "\t" + localRoot.getCount());
            pw1.println(localRoot.getData() + "\t" + localRoot.getCount());// writing to the file

         }
         printP(localRoot.getRightChild(), pattern, pw1); // now checking for pattern matches in right nodes
      }
   }	

   public void findPattern(String pattern, PrintWriter pw1)
   {
      printP(root, pattern, pw1); 
   }


   public boolean insertElement(String data)
   {

      if(contains(data))	//if data word repeats
      {
         tempRoot = findRoot(data);// find root of existing data and increase the frequency count
         tempRoot.setCount();
         tempRoot = null;
         return true;
      }
      // System.out.println(data);
      root = insertElement(root, data);
      return true;
   }


   private AVLNode<String> insertElement(AVLNode<String> localRoot, String data)
   {
      if (localRoot == null)
      {
         localRoot = new AVLNode<String>(data);	   
      }


      else if (data.compareTo(localRoot.getData()) < 0)
      {
         AVLNode<String> leftChild = localRoot.getLeftChild();
         AVLNode<String> subtree = insertElement(leftChild, data);
         localRoot.setLeftChild(subtree);
         localRoot = rebalance(localRoot);
      }
      else 
      {
         AVLNode<String> rightChild = localRoot.getRightChild();
         AVLNode<String> subtree = insertElement(rightChild, data);
         localRoot.setRightChild(subtree);
         localRoot = rebalance(localRoot);
      }

      setHeight(localRoot);
      return localRoot;
   }

   private void setHeight(AVLNode<String> localRoot)
   {	
      if (height(localRoot.getLeftChild()) > height(localRoot.getRightChild()))
      {
         localRoot.setHeight(height(localRoot.getLeftChild()) + 1);
      }
      else
      {
         localRoot.setHeight(height(localRoot.getRightChild()) + 1);
      }
   }

  /* public boolean removeElement(String data)
   {
      AVLNode<String> deleted = root;
      AVLNode<String> parent = null;
      while(true)
      {
         if(deleted == null)
         {
            return false;
         }
         else if(data.compareTo(deleted.getData()) == 0)
         {
            break;
         }
         else if(data.compareTo(deleted.getData()) < 0)
         {
            parent = deleted;
            deleted = deleted.getLeftChild();
         }
         else if(data.compareTo(deleted.getData()) > 0)
         {
            parent = deleted;
            deleted = deleted.getRightChild();
         }
      }
      if(deleted.getLeftChild() == null && deleted.getRightChild() == null)
      {
         deleteLeafNode(deleted, parent);
         return true;
      }
      else if ((deleted.getLeftChild() != null && deleted.getRightChild() == null)
            ||
            (deleted.getLeftChild() == null && deleted.getRightChild() != null))
      {
         deleteNodeWithOneChild(deleted, parent);
         return true;
      }
      else
      {
         deleteNodeWith2Children(deleted, parent);
         return true;
      }
   }*/

   private void deleteLeafNode(AVLNode<String> deleted, AVLNode<String> parent)
   {
      if(deleted == root)
      {
         root = null;
      }
      else if(parent.getLeftChild() == deleted)
      {
         parent.setLeftChild(null);
      }
      else
      {
         parent.setRightChild(null);
      }
   }


   private int height(AVLNode<String> localRoot)
   {
      if (localRoot == null) 
      {
         return -1;
      }
      else
      {
         return localRoot.getHeight();
      }
   }



   private void deleteNodeWithOneChild(AVLNode<String> deleted, AVLNode<String> parent)
   {
      AVLNode<String> child = null;
      if(deleted.getLeftChild() != null)
      {
         child = deleted.getLeftChild();
      }
      else
      {
         child = deleted.getRightChild();
      }
      if(deleted == root)
      {
         root = child;
      }
      else if(deleted == parent.getLeftChild())
      {
         parent.setLeftChild(child);
      }
      else
      {
         parent.setRightChild(child);
      }
   }


   private void deleteNodeWith2Children(AVLNode<String> deleted, AVLNode<String> parent)
   {
      AVLNode<String> largest = deleted.getLeftChild();
      AVLNode<String> parentOfLargest = deleted;

      while(largest.getRightChild() != null)
      {
         parentOfLargest = largest;
         largest = largest.getRightChild();
      }

      deleted.setData(largest.getData());
      deleted.setData(largest.getData());

      if(largest.getLeftChild() == null)
      {
         deleteLeafNode(largest, parentOfLargest);
      }
   }



   public boolean contains(String searchElement)
   {
      boolean found = false;
      AVLNode<String> current = root;
      String temp = null;

      while (current != null && !found)
      {
         if (current.getData().compareTo(searchElement) > 0)
         {
            current = current.getLeftChild();
         }
         else if (current.getData().compareTo(searchElement) < 0)
         {
            current = current.getRightChild();
         }
         else
         {
            temp = current.getData();
            found = true;
         }
      }
      return found;
   }





   public AVLNode<String> findRoot(String searchElement) // function to return the root of a word
   {
      boolean found = false;
      AVLNode<String> current = root;
      //String temp = null;

      while (current != null && !found)
      {
         if (current.getData().compareTo(searchElement) > 0)
         {
            current = current.getLeftChild();
         }
         else if (current.getData().compareTo(searchElement) < 0)
         {
            current = current.getRightChild();
         }
         else
         {
        //    temp = current.getData();
            found = true; // word found hence need to return the node of it
         }
      }
      return current;
   }





   private AVLNode<String> rightRotation(AVLNode<String> node)
   {
      AVLNode<String> tempNode = node.getLeftChild();
      node.setLeftChild(tempNode.getRightChild());
      tempNode.setRightChild(node);
      setHeight(node);
      return tempNode;
   }

   private AVLNode<String> leftRotation(AVLNode<String> node)
   {
      AVLNode<String> tempNode = node.getRightChild();
      node.setRightChild(tempNode.getLeftChild());
      tempNode.setLeftChild(node);
      setHeight(node);
      return tempNode;
   }

   private AVLNode<String> rightLeftRotation(AVLNode<String> node)
   {
      AVLNode<String> tempNode = node.getRightChild();
      node.setRightChild(rightRotation(tempNode));

      return leftRotation(node);
   }

   private AVLNode<String> leftRightRotation(AVLNode<String> node)
   {
      AVLNode<String> tempNode = node.getLeftChild();
      node.setLeftChild(leftRotation(tempNode));

      return rightRotation(node);
   }

   private int getHeightDifference(AVLNode<String> node)
   {
      int leftHeight = -1;
      int rightHeight = -1;

      if(node.getLeftChild() != null)
      {
         leftHeight = node.getLeftChild().getHeight();
      }

      if(node.getRightChild() != null)
      {
         rightHeight = node.getRightChild().getHeight();
      }

      return leftHeight - rightHeight;
   }

   private AVLNode<String> rebalance(AVLNode<String> rootNode)
   {
      int diff = getHeightDifference(rootNode);

      if(diff < -1)
      {
         if(getHeightDifference(rootNode.getRightChild()) < 0)
         {
            rootNode = leftRotation(rootNode);
         }
         else
         {
            rootNode = rightLeftRotation(rootNode);
         }
      }
      else if(diff > 1)
      {
         if(getHeightDifference(rootNode.getLeftChild()) > 0)
         {
            rootNode = rightRotation(rootNode);
         }
         else
         {
            rootNode = leftRightRotation(rootNode);
         }
      }
      return rootNode;
   }

   private void displaySubtreeInOrder(AVLNode<String> localRoot, PrintWriter p)
   {
      if(localRoot != null)
      {
         displaySubtreeInOrder(localRoot.getLeftChild(), p);
         p.println(localRoot.getData());
         displaySubtreeInOrder(localRoot.getRightChild(), p);
      }
   }
   
}
