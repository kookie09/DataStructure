/*
 * Class Name:    WordMatch
 *
 * Author:        Abhishek Toshwal
 * Student ID:	  19122421
 * Creation Date: Sunday, May 17 2020, 10:47 
 * Last Modified: Sunday, May 17 2020, 10:48
 * Subject CSE5ALG
 * Class Description:
 *
 */
 //import java.text.NumberFormat;
// import java.text.DecimalFormat;
import java.util.*;

//import java.util.LinkedList;
import java.io.*;

public class WordMatch
{
	 
	public static void main(String[] args) throws IOException 
	{
		//final long start = System.currentTimeMillis();
	 
		
		File fileOpen = new File(args[0]);					// opening first file 
		Scanner fileInput = new Scanner(fileOpen);	
		ArrayList<String> dataFiles = new ArrayList<>();	
		FileWriter writer = new FileWriter(args[1]);		// creating sameple3.txt
		PrintWriter pw = new PrintWriter(writer);
		AVLTree dataStructure = new AVLTree(pw);	//passing pw for output file name where dataa will be written
		String word = "";
		
		while(fileInput.hasNext())								
      {	
         dataFiles.add(fileInput.next());	// gathering all file names inside the first file
      }
	  
	  for(int i = 0; i < dataFiles.size(); i++)
		{
				fileOpen = new File(dataFiles.get(i));
				fileInput = new Scanner(fileOpen);
				
				while(fileInput.hasNext())
				{
					word = fileInput.next();			
					word = word.toLowerCase();							// converting each word into lower case
					word = word.replaceAll("[^a-zA-Z0-9]", "");			//removing all the special characters
					word = word.replaceAll("[^A-Za-z]", "");					//removing numbers
					
						
					
					
						if(!word.equals(""))
						{
							dataStructure.insertElement(word);
							
						}	
								
							
						
						
				}
				//data.remove("");
		}
		
		PrintWriter z = new PrintWriter(System.out, true);
	
		dataStructure.printInorder();	//calling the function to sort the data and write it to file
		
		pw.close();
		
		fileOpen = new File(args[2]);	//for input2
		fileInput = new Scanner(fileOpen);
		String pattern = fileInput.next();
		//System.out.println(pattern);
		//System.out.println(pattern);

		
		FileWriter writer1			= new FileWriter(args[3]);
		PrintWriter pw1 			= new PrintWriter(writer1);
		
		dataStructure.findPattern(pattern, pw1);// calling the match finding method

		pw1.close();
	//	final long end = System.currentTimeMillis();
//NumberFormat formatter = new DecimalFormat("#0.00000");
//System.out.print("Execution time is " + formatter.format((end - start) / 1000d) + " seconds");
		}
}

