/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lz77;

/**
 *
 * @author Aabdallah Mostafa
 */
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.lang.*;
class Tag
{
    char next;
    int steps,len;
}
public class LZ77 {

    public static void writeInFile(String fileName,Vector<Tag> vectorOfTags)
    {
         try (FileWriter file=new FileWriter(fileName))
        {
            for(int i=0 ; i<vectorOfTags.size();i++)
            {
                file.write((byte)vectorOfTags.elementAt(i).steps);
                file.write((byte)vectorOfTags.elementAt(i).len);
                int nextchar=(int)vectorOfTags.elementAt(i).next;
                file.write(( byte)nextchar);

            }
            file.close();
        }
        catch(IOException e){
                System.out.print("Error");
            }
         
    }
    
       /*
    */
    public static Vector<Tag> readFromFile(String fileName) 
    {
        Vector<Tag> vectorOfTags=new Vector<Tag>();
        try{
            Path file = Paths.get(fileName);
            byte[] data = Files.readAllBytes(file);
            Tag temp=new Tag();
            for(int i=0 ; i<data.length;i++)
            {                
                if(i%3==0)
                    temp.steps=data[i];
                else if(i%3==1)
                    temp.len=data[i];
                else
                {
                    byte byt=data[i];
                    temp.next=(char)byt;
                    vectorOfTags.add(temp);
                    temp=new Tag();
                }
            }
        }
        catch(IOException e){
                System.out.print("Error: file not found");
            }
        return vectorOfTags;
    }
    public static Vector<Tag> compress(String text)
    {
        Vector<Tag> vectorOfTags=new Vector<Tag>();       
        char next;
        
        int len,steps=0;
        
        for(int i=0 ; i< text.length() ; i++)
        {
            next='#';
            String temporary="";
            temporary+=text.charAt(i);
      
            int t=i;
            while( i!=0 && (text.substring(0, t)).contains(temporary)==true && i<text.length()-1)
            {
                ++i;
                temporary+=text.charAt(i);
            }
            //len,next
            if(i==text.length()-1 && (text.substring(0, t)).contains(temporary)==true )
            {   
                len=temporary.length();
            }
            else
            {                
                len=temporary.length()-1;
                String s=temporary.substring(temporary.length()-1, temporary.length());
                next=s.charAt(0);
                if(t!=0)
                    temporary=temporary.substring(0, temporary.length()-1);
            }
            //steps
           
            if(t!=0 && (text.substring(0, t)).lastIndexOf(temporary, t-1)!=-1 && temporary.compareTo("")!=0)
            {
                steps=t-text.substring(0,t).lastIndexOf(temporary, t-1);
            }  
            else
                steps=0;
            Tag tagobj=new Tag();
            tagobj.steps=steps;
            tagobj.len=len;
            tagobj.next=next;
            vectorOfTags.add(tagobj);
            System.out.println("< "+steps+", "+len+", "+next+" >");
            
        }
        writeInFile("LZ77.txt",vectorOfTags);
        return vectorOfTags;
        
    }
    public static String decompress()
    {
        Vector<Tag> vec=new Vector<Tag>();
        vec=readFromFile("LZ77.txt");
        String text="";
        for(int i=0 ; i<vec.size() ; i++)
        {
            int cur=text.length()-vec.elementAt(i).steps;
      
            for(int j=0 ; j< vec.elementAt(i).len ; j++)
            {
               
                text+=text.charAt(cur);
                cur++;
            }
            if(vec.elementAt(i).next!='#')
                text+=vec.elementAt(i).next;
        }       
        return text;
    }
    public static void main(String[] args) {
        Scanner cin=new Scanner(System.in);
        String text;
    
        text=cin.nextLine();
        compress(text);
        System.out.println(decompress());
      
    }
    
}
