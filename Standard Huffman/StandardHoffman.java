/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package standardhoffman;
import java.io.*;
import java.util.*;
import java.lang.*;

/**
 *
 * @author lenovo
 */
class node
{
    String values="";
    String code="";
    double prob;
    node left=null;
    node right=null;
    double getprob(){
        return prob;
    }
    
}
public class StandardHoffman {
    static HashMap<Character,String> hash=new HashMap<Character,String>();
    public static void traverse(node root)
    {
        if(root.left==null && root.right==null)
            hash.put(root.values.charAt(0), root.code);
        if(root.left!=null)
        {
            root.left.code=root.code+"0";
            traverse(root.left);
        }
       
        if(root.right!=null)
        {
            root.right.code+=root.code+"1";
            traverse(root.right);
        }
    }
    public static void write(String ans) throws IOException
    {
        FileWriter file=new FileWriter("Huffman.txt");
        file.write(ans+" ");
        for (char key : hash.keySet()) {
           file.write(key+" "+hash.get(key)+" ");
        }
        file.close();
        hash=new HashMap();
    }
    public static String read() throws IOException{
        FileReader file=new FileReader("Huffman.txt");
        Scanner cin=new Scanner(file);
        String ans= cin.next();
        hash=new HashMap();
        while(cin.hasNext())
        {
           char temp=cin.next().charAt(0);
           String code=cin.next();
           hash.put(temp, code);
        }
        file.close();
        return ans;
    }
    public static String compress(String input) throws IOException
    {
        String ans="";
        HashMap<Character,Double>map=new HashMap<Character,Double>();
            
        for(int i=0 ; i<input.length() ; i++)
        {
            char offset=input.charAt(i);
            if(map.get(offset)==null)
            {
                map.put(offset, 1.0);
            }
            else
                map.put(offset, map.get(offset)+1);
        }
        
        for (char key : map.keySet()) {
        map.put(key,map.get(key)/input.length());
        }
        Vector<node> vec=new Vector<node>();
        for(char key:map.keySet())
        {
            node temp=new node();
            temp.values=Character.toString(key);
            temp.prob=map.get(key);
            vec.addElement(temp);
        }
        if(vec.size()==1)
        {
            node temp=new node();
            temp.code="0";
            temp.values=vec.elementAt(0).values;
            vec.remove(0);
            vec.addElement(temp);
            
        }
        else{
        while(vec.size()>1)
        {
            vec.sort(Comparator.comparing(node::getprob));
            node temp=new node();
            temp.left=vec.elementAt(0);
            temp.right=vec.elementAt(1);
            temp.prob=temp.left.prob+temp.right.prob;
            temp.values=temp.left.values+temp.right.values;
            vec.remove(0);
            vec.remove(0);
            vec.addElement(temp);
        }
        }
        traverse(vec.elementAt(0));
  
        for(int i=0  ;i<input.length();i++)
        {
            ans+=hash.get(input.charAt(i));
        }

        write(ans);
        return ans;
        
    }
    public static String decompress() throws IOException
    {
        String ans=read(),temp="",output="";
        for(int i=0 ; i<ans.length();i++)
        {
            temp+=ans.charAt(i);
            for (char key : hash.keySet()) {
                if(hash.get(key).equals(temp))
                {
                    output+=key;
                    temp="";
                }
            }
        }
        return output;
    }
    public static void main(String[] args) throws IOException {
       String input;
       Scanner cin=new Scanner(System.in);
       input=cin.nextLine();
       compress(input);
       decompress();
    }
    
}
