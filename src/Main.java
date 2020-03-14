import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws Exception{
        Scanner sc=new Scanner(System.in);
        while(true){
            System.out.println("please input '[order] [filename]':");
            System.out.print("wc.exe ");
            String command[]=sc.nextLine().split(" ");
            Operation(command);
        }
    }
    //实现基本操作
    public static void Operation(String[] command) throws Exception{
        String line=null;
        int CharCount=0,WordCount=0,LineCount=0;
        FileInputStream fin=new FileInputStream(command[1]);
        InputStreamReader reader=new InputStreamReader(fin);
        BufferedReader br=new BufferedReader(reader);
        //统计字符数
        if ("-c".equals(command[0])){
            while((line=br.readLine())!=null){
                CharCount=CharCount+line.length();
            }
            System.out.println("CharCount:"+CharCount);
        }
        //统计词个数
        else if ("-w".equals(command[0])){
            while ((line=br.readLine())!=null){
                //Matcher m=Pattern.compile("(\\w+)").matcher(line);
                Matcher m=Pattern.compile("\\b\\w+\\b").matcher(line);
                while(m.find()){
                    WordCount++;
                }
            }
            System.out.println("WordCount:"+WordCount);
        }
        //统计行数
        else if ("-l".equals(command[0])){
            while((line=br.readLine())!=null){
                LineCount++;
            }
            System.out.println("LineCount:"+LineCount);
        }
        else if ("-a".equals(command[0])){
            SpecialLine(br);
        }
    }
    //统计特殊行
    public static void SpecialLine(BufferedReader br)throws Exception{
        String line=null;
        boolean flag=false;
        int BlankLine=0,CodesLine=0,CommentsLine=0;
        //统计空行
        while ((line=br.readLine())!=null){
            //除去注释前的空格
            line=line.trim();
            //m1匹配空行
            Matcher m1=Pattern.compile("^\\s*$").matcher(line);
            //m2匹配//型注释
            Matcher m2=Pattern.compile("(^\\/{2,})|(\\/{2,}\\w*$)").matcher(line);
            if (m1.find()) {
                BlankLine++;
            }
            else if (m2.find()){
                CommentsLine++;
            }
            /*else if (line.startsWith("//")){
                CommentsLine++;
            }
            else if (line.matches("\\/{2,}\\w*$")){
                CommentsLine++;
            }*/
            else if (line.startsWith("/*")&&!line.endsWith("*/")){
                CommentsLine++;
                flag=true;
            }
            else if (line.startsWith("/*")&&line.endsWith("*/")){
                CommentsLine++;
            }
            else if (flag==true){
                CommentsLine++;
                if (line.endsWith("*/")){
                    flag=false;
                }
            }
            else {
                CodesLine++;
            }
        }
        System.out.println("CodesLine:"+CodesLine);
        System.out.println("BlankLine:"+BlankLine);
        System.out.println("CommentsLine："+CommentsLine);
    }
}

