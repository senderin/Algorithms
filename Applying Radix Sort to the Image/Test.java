/**
 *  This program process an image by sorting according to intensities of 10x10 pixel blocks. 
 *  
 *  @author Senderin, Busra
 *  @see    Class IntensityFilter
 *  @see    Class RadixSort
 */

import java.io.IOException;
import java.util.Scanner;

public class Test {
	
	/**
	 * Main method
	 * The String variable fileName is the name of original image that will be processed 
	 * and must specify in that form "fileName.jpg"
	 */
	public static void main(String[] args) throws IOException
	{
		Scanner input=new Scanner(System.in);
		System.out.println("Enter name of the image:\n>");
		String fileName=input.next();
		IntensityFilter tiger=new IntensityFilter(fileName); //creates an IntensityFilter object with argument fileName
		tiger.getImage(); //calls getImage() method of IntensityFilter
		
		//A sign to be understood when the program ends to run successfully 
		System.out.println("Processing is completed successfully...");
		System.out.println("Please, look at the image named 'BubbleSortedImage' on your desktop.");
		
		//Instruction for the continuation of the program after the user looks at the image  
		System.out.println("Then, press a key to repeat process by using Radix Sort...");
		if(input.next() != null)
		{
			System.out.println("Processing is started...Please, wait...");
			// Creates object for inner class RadixSort
			RadixSort object=new RadixSort(fileName);
			
			//A sign to be understood when the program ends to run successfully 
			System.out.println("Processing is completed successfully...");
			System.out.println("Please, look at the image named 'RadixSortedImage' on your desktop.");
		}
		
	}
}
