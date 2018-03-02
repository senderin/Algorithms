// Class IntensityFilter consists of processes of the program.
// Used Bubble Sort Algorithm to sort
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class IntensityFilter {
	
	private final String READ_PATH;               // Path of original image
	protected BufferedImage image;                // field to hold the original image
	BufferedImage[][] subimages;                  // matrix field to hold image 10x10 blocks of pixel
	
	// array field of type Intensity that is nested class that has variables: intensityValue, xIndex and yIndex
	Intensity[] intensityArray;	
	final int COLUMN;                             
	final int ROW;                                 
	
	// Class constructor specifying the original image's name entered by user 
	IntensityFilter(String READ_PATH) throws IOException 
	{			
		this.READ_PATH=READ_PATH;
		image=ImageIO.read(new File(READ_PATH));    
		COLUMN=(image.getWidth()/10);               // calculates number of columns a width of 10 pixel  
		ROW=(image.getHeight()/10);                 // calculates number of rows a width of 10 pixel
		subimages=new BufferedImage[ROW][COLUMN];
		intensityArray=new Intensity[ROW*COLUMN];
	}

	
	/**
	 * Divides the given image into 10x10 blocks of pixels
	 */
	private void getSubimage() throws IOException
	{
		int x=0;                                    // the x coordinate of the upper-left corner of block
		int y=0;                                    // the y coordinate of upper-left corner of block
		for(int row=0; row<ROW; row++)
		{
			y=row*10;                               // increases y coordinate by ten when goes to a bottom row
			for(int column=0; column<COLUMN; column++)
			{
				x=column*10;                        // increases x coordinate by ten when goes to a right column
				
				// assigns each block to "subimages" matrix orderly
				subimages[row][column]=image.getSubimage(x, y, 10, 10);
				
			}	
		}
	}
	
	/**
	 * nested class Intensity 
	 */
	protected class Intensity
	{
		private int xIndex;                        // holds the original row index of block which has that intensity value
		private int yIndex;                        // holds the original column index of block which has that intensity value
		private double intensityValue;             // holds the intensity value of the block
		
		// Class constructor specifying row index, column index and intensity value
		Intensity(int x, int y, double value)
		{
			xIndex=x;
			yIndex=y;
			intensityValue=value;
		}

		public double getIntensityValue() {
			return intensityValue;
		}

		public int getxIndex() {
			return xIndex;
		}

		public int getyIndex() {
			return yIndex;
		}

	}

	
	
	/**
	 * Finds intensity values of each block
	 * Uses that formula I=0.298*red+0.587*green+0.114*blue
	 */
	protected void findIntensity() throws IOException
	{
		getSubimage();                              // calls getSubimage() method firstly
		int red=0; 
		int green=0;
		int blue=0;
		int i=0;                                    // index of IntensityArray
		double intensityValue;
		for(int row=0; row<ROW; row++)
		{
			for(int column=0; column<COLUMN; column++)
			{
				// loop for pixels in each row
				for(int xPixel=0; xPixel<10; xPixel++)
				{
					// loop for pixels in each column
					for(int yPixel=0; yPixel<10; yPixel++)
					{
						// the rgb value of one pixels in a certain block
						int rgb=subimages[row][column].getRGB(xPixel, yPixel);
						red=red+((rgb >> 16) & 0xFF);                      // adds red value of each pixel
						green=green+((rgb>> 8) & 0xFF);                    // adds green value of each pixel
						blue=blue+((rgb>> 0) & 0xFF);                      // adds blue value of each pixel
					}
				}
				
				intensityValue=0.298*red+0.587*green+0.114*blue;           // calculates intensity value
				
				// Creates an Intensity object that holds indices and intensity values for each block
				intensityArray[i]=new Intensity(row, column, intensityValue);  
				i++;                                                      // increases index of "intensityArray" by one		
				red=0;													  // assigns zero for intensity calculation of another block 
				green=0;												  // assigns zero for intensity calculation of another block
				blue=0;													  // assigns zero for intensity calculation of another block
			}
		}
		
	}

	
	/**
	 * Sorts object array of type Intensity called "intensityArray", which holds
	 * row index, column index and intensity values of each block by using intensity values.
	 * Therefore, when objects are sorted in the ascending intensity value order,
	 * sorted indices is obtained to need placing "subimages" into a new empty image. Because sorting objects does not
	 * change any data of these objects.
	 * 
	 * Bubble sort algorithm is used.
	 */
	private void sortIntensity() throws IOException
	{
		findIntensity();                                  // calls findIntensity() method
		
		// Bubble sort
		for (int k=0; k<(intensityArray.length-1); k++)
		{
			for (int j=(intensityArray.length-1); j>k; j--)
			{
				if (intensityArray[j-1].getIntensityValue()>(intensityArray[j].getIntensityValue()))
				{
					Intensity tmp = intensityArray[j];
					intensityArray[j] = intensityArray[j-1];
					intensityArray[j-1] = tmp;
				}
			}		
		}
	}
	
	
	/**
	 * Place the blocks back into a new empty image by getting already sorted indices from "intensityArray"
	 */
	private void mergeSubimages() throws IOException
	{
		sortIntensity();                                 // calls sortIntensity() method
		
		// creates a BufferedImage object that holds new image in sizes of original image
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
						BufferedImage temp=subimages[intensityArray[index].getxIndex()][intensityArray[index].yIndex]; 
						xx=j*10;                         // increases x coordinate by ten when goes to a right column
						g.drawImage(temp, xx, yy, null); // places the block defined coordinates
						index++;                         // increases for other block
						
					}
					yy=i*10;                			 // increases y coordinate by ten when goes to a bottom row
				}
			break; 										 // after the last row blocks are placed, the loop ends
		}
		
		// writes the processed image named "SortedImage" in .png format to user's desktop
		File desktop=new File(javax.swing.filechooser.FileSystemView.getFileSystemView().getHomeDirectory(), "BubbleSortedImage.png");
		ImageIO.write(result, "png", desktop);		
	}
	
	/**
	 *  User calls just this method that triggers off processing.
	 */
	public void getImage() throws IOException
	{
		mergeSubimages();	
	}
}// end of IntensityFilter Class
	