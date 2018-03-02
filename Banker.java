
package package1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.lang.String;


public class Banker {
    
    int n; //number of process
    int m; //number of sources
    int allocation[][];
    int max[][];
    int need[][];
    int total[];
    int available[];
    String nameOfRes[];
    String nameOfPro[];
    
    public void readTxt() throws IOException
    {
        BufferedReader br=null;        
        br=new BufferedReader(new FileReader(file));
        String line;
        while((line=br.readLine())!=null)
        {             
            //System.out.println(line);
            if(line.compareTo("Total:")==0)
            {
                //System.out.println(line);
                line=br.readLine();
                //System.out.println(line);
                nameOfRes=line.split("\\s+");
                m=nameOfRes.length;
                total=new int[m];
                line=br.readLine();
                //System.out.println(line);
                String part[]=line.split("\\s+");
                for(int i=0; i<part.length; i++)
                    total[i]=Integer.parseInt(part[i]);
            }
            else if(line.compareTo("Allocation:")==0)
            {
                //System.out.println(line);
                line=br.readLine();
                while(line.compareTo("Max:")!=0)
                {
                    line=br.readLine();
                    n++;
                }
                //System.out.println(b.n);
                allocation=new int[n][m];
                max=new int[n][m];
                need=new int[n][m];
                nameOfPro=new String[n];
                break;
            }
        }
        br.close();
//         for(String str: nameOfRes)
//            System.out.print(str+" ");
//        System.out.println("");
//        for(int num: total)
//            System.out.print(num+" ");
//        System.out.println("");
        
        br=new BufferedReader(new FileReader(file));
        while((line=br.readLine())!=null) 
        {
            if(line.compareTo("Allocation:")==0)
            {
                //System.out.println(line);
                int i=0;                       
                while((line=br.readLine()).compareTo("Max:")!=0) {
                    String part[]=line.split("\\s+");
                    nameOfPro[i]=part[0];                   
                    for(int j=1; j<part.length; j++)
                    {                      
                        //System.out.println(line);
                        allocation[i][j-1]=Integer.parseInt(part[j]);
                    }
                    i++;
                }
                if(line.compareTo("Max:")==0) {          
                    i=0;                       
                    while((line=br.readLine())!=null) {
                        String part[]=line.split("\\s+");
                        for(int j=1; j<part.length; j++)
                        {                      
                            max[i][j-1]=Integer.parseInt(part[j]);
                        }
                    i++;    
                    }
                   
                }        
            }
        }
        
        
         
//        for(String str: nameOfPro)
//            System.out.print(str+" ");
//        System.out.println("");
//        for(int i=0; i<n; i++) {
//            for(int j=0; j<m; j++)
//                System.out.print(allocation[i][j]+" ");
//        System.out.println(""); 
//        }
//        System.out.println("");
//        for(int i=0; i<n; i++) {
//            for(int j=0; j<m; j++)
//                System.out.print(max[i][j]+" ");
//        System.out.println(""); 
//        }  
        
        need=new int[n][m];
        available=new int[m];
        for(int i=0; i<n; i++)
            for(int j=0; j<m; j++)
                need[i][j]=max[i][j]-allocation[i][j];
        
        for(int i=0; i<m; i++) {
            int x=0;
            for(int j=0; j<n; j++)
                x+=allocation[j][i];
            available[i]=total[i]-x;
        }
        
//        System.out.println("");
//        for(int i=0; i<n; i++) {
//            for(int j=0; j<m; j++)
//                System.out.print(need[i][j]+" ");               
//        System.out.println(""); }
//        System.out.println("");
//       for(int j=0; j<m; j++)
//            System.out.print(available[j]+" ");     
                
         
    }
    
    boolean finish[];
    int work[];
    public boolean isSafeState()
    {
        finish=new boolean[n];
        for(int i=0; i<n; i++)
            finish[i]=false;
        work=new int[available.length];
        for(int i=0; i<work.length; i++)
            work[i]=available[i];
        
        for(int i=0; i<n; i++)
        {  
            if(finish[i]==false && step1(i)==true)   
               step2(i);                           
        }
        for(int i=0; i<n; i++)
        {
            if(finish[i]==false)
            {
                if(step1(i)==true)
                    step2(i);
            }
        }
        for(int i=0; i<n; i++)
            if(finish[i]==false)
                return false;
         return true;  
    }
    
    public boolean step1(int numOfPro)
    {
        for(int i=0; i<m; i++)
            if(need[numOfPro][i]>work[i]) {
                return false;
            }               
        return true;
    }

    public void step2(int numOfPro)
    {
         for(int k=0; k<m; k++) {
            work[k]=work[k]+allocation[numOfPro][k];
            finish[numOfPro]=true;
         }
    }
    
    
    
    public void request(int numOfPro, int[] request)
    {
        boolean step1=true;
        boolean step2=true;
        for(int i=0; i<m; i++)
        {
            if(request[i]>need[numOfPro][i]) {
                step1=false;
                System.out.println("Sorry. "+nameOfPro[numOfPro]+" already reached to max.");
                break;
            }
        }
        for(int i=0; i<m; i++)
        {
            if(request[i]>available[i]) {
                step2=false;
                System.out.println("There is not enough resource.");
                break;
            }
        }
        if(step1==true && step2==true)
        {
            for(int i=0; i<m; i++)
                available[i]=available[i]-request[i];
            for(int i=0; i<m; i++)
                allocation[numOfPro][i]=allocation[numOfPro][i]+request[i];
            for(int i=0; i<m; i++)
                need[numOfPro][i]=need[numOfPro][i]-request[i];
            
            boolean isValid=isSafeState();
            if(isValid==true) {
                System.out.println("Request granted current allocation:");
                System.out.println("Allocation:");
                 for(int i=0; i<n; i++) {
                     System.out.print(nameOfPro[i]+" ");
                    for(int j=0; j<m; j++)
                        System.out.print(allocation[i][j]+" ");
                    System.out.println(""); 
                }
                System.out.println("Available:");
                for(String str: nameOfRes)
                    System.out.print(str+" ");
                System.out.println("");
                for(int i: available)
                    System.out.print(i+" ");
                System.out.println(""); 
            }
            else
            {
                System.out.println("It is rejected since system would end up in unsafe zone.");
                for(int i=0; i<m; i++)
                    available[i]=available[i]+request[i];
                for(int i=0; i<m; i++)
                    allocation[numOfPro][i]=allocation[numOfPro][i]-request[i];
                for(int i=0; i<m; i++)
                    need[numOfPro][i]=need[numOfPro][i]+request[i];
            }
        }
    }
    
    
    
   
    String file;
    public static void main(String[] args) throws IOException
    {
        Banker b=new Banker();
        b.file=args[0];
        b.readTxt();
        while(true)
        {
            System.out.println("Please enter request:");
            Scanner input=new Scanner(System.in);
            String str[]=(input.nextLine()).split("\\s+");
            int num=0;
            for(int i=0; i<b.n; i++)
                if(str[0].compareTo(b.nameOfPro[i])==0){
                    num=i;
                    break;
            }
            int array[]=new int[str.length-1];
            for(int i=0; i<array.length; i++)
                array[i]=Integer.parseInt(str[i+1]);
            
//            for(int i=0; i<array.length; i++)
//                System.out.println(num+" "+array[i]);
            
            b.request(num, array);
            
        }
        
    }
}
    

