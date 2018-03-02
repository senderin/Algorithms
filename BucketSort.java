
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BucketSort {
    private static final int DEFAULT_BUCKET_SIZE = 1;
    
    public static void computeBucket() throws FileNotFoundException, InterruptedException
    { 
        Double array[][]=new Double[10][];
        array[0]=new Double[5];
        array[1]=new Double[10];
        array[2]=new Double[30];
        array[3]=new Double[50];
        array[4]=new Double[100];
        array[5]=new Double[200];
        array[6]=new Double[500];
        array[7]=new Double[700];
        array[8]=new Double[850];
        array[9]=new Double[1000];
        for(int i=0; i<array.length; i++)
        {
            for(int j=0; j<array[i].length; j++)
            {
               double random=Math.random();
               array[i][j]=random;
            }
        }
        PrintWriter writer=new PrintWriter("bucket.txt");
        for(int i=0; i<array.length; i++)
        {       
            long startTime=System.currentTimeMillis();
            sort(array[i]);
            long endTime=System.currentTimeMillis();
            writer.println(array[i].length+"   "+(endTime-startTime));
        }
        writer.close();
        
    }  

    public static void sort(Double[] array) throws InterruptedException {
        sort(array, DEFAULT_BUCKET_SIZE);
    }

    public static void sort(Double[] array, int bucketSize) throws InterruptedException {
        if (array.length == 0) {
            return;
        }

        // Determine minimum and maximum values
        Double minValue = array[0];
        Double maxValue = array[0];
        for (int i = 1; i < array.length; i++) {
            
            if (array[i] < minValue) {
                minValue = array[i];
            } else if (array[i] > maxValue) {
                maxValue = array[i];
            }
        }

        // Initialise buckets
        int max=(int)(maxValue*10);
        int min=(int)(minValue*10);
        int bucketCount= (max-min)/bucketSize + 1;
        List<List<Double>> buckets = new ArrayList<List<Double>>(bucketCount);
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<Double>());    
        }

        // Distribute input array values into buckets
        for (int i = 0; i < array.length; i++) {
            buckets.get(((int)(array[i] - minValue) / bucketSize)).add(array[i]);
            
        }

        // Sort buckets and place back into input array
        int currentIndex = 0;
        for (int i = 0; i < buckets.size(); i++) {
            Double[] bucketArray = new Double[buckets.get(i).size()];
            bucketArray = buckets.get(i).toArray(bucketArray);
            
            insertionSort(bucketArray);
            for (int j = 0; j < bucketArray.length; j++) {
                array[currentIndex++] = bucketArray[j];
            }
        }
    }
    
    public static void insertionSort(Double[] x) throws InterruptedException
    {	
        for(int i=1; i<x.length; i++)
        {			
            double tmp=x[i];			
            int j;			
            for(j=i; j>0; j--)
            {
                Thread.sleep(1);
                if(x[j-1]>tmp)
                {					
                    x[j]=x[j-1];					
                }				
                else
                {					
                    break;
                }					
            }				
            x[j]=tmp;		
        }
    }
}
