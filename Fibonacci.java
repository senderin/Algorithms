
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Fibonacci {

    public int recursive(int num) throws InterruptedException
    {
        Thread.sleep(10);
        if(num==0 || num==1)
            return num;
        else
            return recursive(num-1)+recursive(num-2);
    }
    
    public void computeFibonacci() throws InterruptedException, FileNotFoundException
    {
        PrintWriter writer=new PrintWriter("fibonacci.txt");
        for(int i=1; i<=15; i++)
        {
            long startTime=System.currentTimeMillis();
            recursive(i);
            long endTime=System.currentTimeMillis();
            writer.println(i+"   "+(endTime-startTime));
        }
        writer.close();
    }
    
}
