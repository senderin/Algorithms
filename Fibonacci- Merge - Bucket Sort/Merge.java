
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class Merge {
        
    public static void computeMerge() throws FileNotFoundException, InterruptedException
    {
        Random randomGenerator=new Random(); 
        Integer array[][]=new Integer[10][];
        array[0]=new Integer[5];
        array[1]=new Integer[10];
        array[2]=new Integer[30];
        array[3]=new Integer[50];
        array[4]=new Integer[100];
        array[5]=new Integer[200];
        array[6]=new Integer[500];
        array[7]=new Integer[700];
        array[8]=new Integer[850];
        array[9]=new Integer[1000];
        for(int i=0; i<array.length; i++)
        {
            for(int j=0; j<array[i].length; j++)
            {
               int random=randomGenerator.nextInt(995000)+5000;
               array[i][j]=random;
            }
        }
        PrintWriter writer=new PrintWriter("merge.txt");
        for(int i=0; i<array.length; i++)
        {       
            long startTime=System.currentTimeMillis();
            mergeSort(array[i]);
            long endTime=System.currentTimeMillis();
            writer.println(array[i].length+"   "+(endTime-startTime));
        }
        writer.close();
        
    }  
       

    public static void mergeSort(Comparable [ ] a) throws InterruptedException
    {
        
        Comparable[] tmp = new Comparable[a.length];
        mergeSort(a, tmp,  0,  a.length - 1);
    }


    private static void mergeSort(Comparable [ ] a, Comparable [ ] tmp, int left, int right) throws InterruptedException
    {
            if( left < right )
            {
                    int center = (left + right) / 2;
                    mergeSort(a, tmp, left, center);
                    mergeSort(a, tmp, center + 1, right);
                    merge(a, tmp, left, center + 1, right);
            }
    }


    private static void merge(Comparable[ ] a, Comparable[ ] tmp, int left, int right, int rightEnd ) throws InterruptedException
    {
        int leftEnd = right - 1;
        int k = left;
        int num = rightEnd - left + 1;

        while(left <= leftEnd && right <= rightEnd)
            if(a[left].compareTo(a[right]) <= 0)
                tmp[k++] = a[left++];
            else
                tmp[k++] = a[right++];

        while(left <= leftEnd)    // Copy rest of first half
            tmp[k++] = a[left++];

        while(right <= rightEnd)  // Copy rest of right half
            tmp[k++] = a[right++];

        // Copy tmp back
        for(int i = 0; i < num; i++, rightEnd--) {
            
            a[rightEnd] = tmp[rightEnd];
            Thread.sleep(10);
        }
    }

}

    

