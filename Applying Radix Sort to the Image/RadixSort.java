// Used Radix Sort Algorithm to sort
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class RadixSort extends IntensityFilter {
							    
		private final int SIZE;						 
		double output[];
		
		// Class constructor specifying the original image's name entered by user 
		RadixSort(String READ_PATH) throws IOException
		{
			super(READ_PATH);	
			SIZE=intensityArray.length;			  
			output=new double[SIZE];  		// holds sorted array
			findIntensity();					
			radixSort();							 
			mergeSubimages(output);			// calls mergeSubimages() method which belongs to RadixSort class to merge blocks 
		}
		
		
		/**
		 * Sorts object array of type Intensity called "intensityArray" by using their intensity values
		 * 
		 * Radix sort algorithm is used by benefitted Histogram algorithm.
		 */
		private void radixSort()
		{
			double maxIntensity=intensityArray[0].getIntensityValue();       
			for(int i=1; i<SIZE; i++)
				if(intensityArray[i].getIntensityValue()>maxIntensity)
					maxIntensity=intensityArray[i].getIntensityValue();
			
			// variable "digit" represents the digit from the rightmost digit to the leftmost digit
			// the condition  "max/exp" determine how many times numbers will divide
			for(int digit=1; maxIntensity/digit>0; digit*=10)					 
			{
				int count[]=new int[10];		     //	array that each index of it corresponds to number of that index
				count=histogram(count, digit);   				
				
				for(int i=SIZE-1; i>=0; i--)
				{
					int remainder=((int) (intensityArray[i].getIntensityValue()/digit)%10);
					
					// be placed the values according to that digit (for first and not the same remainders by starting the specific last index)
					output[count[remainder]-1]=intensityArray[i].getIntensityValue();
					// decreases by one for that every same remainder is placed a top index 
					count[remainder]--;
				}
				
				// sorts the object array "intensityArray" by comparing sorted array "output"
				for(int i=0; i<SIZE;i++)
				{
					for(int j=0; j<SIZE; j++)
						if(intensityArray[j].getIntensityValue()==output[i])
						{
							Intensity temp=intensityArray[i];
							intensityArray[i]=intensityArray[j];
							intensityArray[j]=temp;
						}
				}
				
				// resets the counter
				for(int i=0; i<10; i++)
					count[i]=0;
			}
		
		}
		
		
		/**
		 * Uses Histogram Algorithm to determine how many same numbers there are 
		 * @param  count        an array that each index of it corresponds to number of that index
		 * @param  digit		representa the digit
		 * @return count		returns an integer array
		 */
		private int[] histogram(int[] count, int digit)
		{	
			for(int i=0; i<SIZE; i++)
			{
				int remainder=((int) (intensityArray[i].getIntensityValue()/digit)%10);
				count[remainder]++;		// increases by one for every same remainder
			}
				
			// determines which remainder will be in which last element(=last index+1)
			for(int i=1; i<10; i++)							    
				count[i]+=count[i-1];
			
			return count;
		}
		
		/**
		 * Almost same with "mergeSubimages" method of outer class
		 * There is a few alterations 
		 */
		private void mergeSubimages(double[] output) throws IOException
		{
			// creates a BufferedImage object that holds new image in sizes of original imag
			BufferedImage result=new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
			
			// creates a Graphics object that has context for "result" object
			Graphics g=result.getGraphics();  
			int xx=0;										 // x coordinate of upper-left corner that the block will be placed
			int yy=0;										 // y coordinate of upper-left corner that the block will be placed
			int index=0;									 // index of "intensityArray"
			
			// loop for placing blocks orderly 
			while(true) {
				for(int i=0; i<ROW; i++) {
						for(int j=0; j<COLUMN; j++)
						{
							// holds the block in the order
							BufferedImage temp=subimages[intensityArray[index].getxIndex()][intensityArray[index].getyIndex()]; 
							xx=j*10;                         // increases x coordinate by ten when goes to a right column
							g.drawImage(temp, xx, yy, null); // places the block defined coordinates
							index++;                         // increases for other block
							
						}
						yy=i*10;                			 // increases y coordinate by ten when goes to a bottom row
					}
				break; 										 // after the last row blocks are placed, the loop ends
			}
			
			// writes the processed image named "SortedImage" in .png format to user's desktop
			File desktop=new File(javax.swing.filechooser.FileSystemView.getFileSystemView().getHomeDirectory(), "RadixSortedImage.png");
			ImageIO.write(result, "png", desktop);		
		}

	}// end of RadixSort class
	
