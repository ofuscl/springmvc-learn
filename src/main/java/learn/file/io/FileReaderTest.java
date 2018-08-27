package learn.file.io;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by yunfan on 2018/5/8.
 */
public class FileReaderTest {

    public static void main(String[] args) {

        FileReaderTest test = new FileReaderTest();
        test.testFileReaderWriter();

    }

    private void testFileReaderWriter(){

        FileReader fr = null;
        FileWriter fw = null;
        try{
            fr = new FileReader("F:/tmp/ktgg1.txt");
            fw = new FileWriter("F:/tmp/fileWriter.txt");
            int ch = 0;
            char []  buf = new char[1024];
            while ((ch=fr.read(buf)) != -1){
                System.out.println(buf.length);
                fw.write(new String(buf,1,ch));
            }

            for(char bu : buf){
                System.out.println(bu);
            }

        }catch (IOException e){
            System.out.println(e);
        }finally {
            if(fr != null){
                try{
                    fr.close();
                }catch (IOException e){
                    System.out.println(e);
                }
            }
            if(fw != null){
                try{
                    fw.close();
                }catch (IOException e){
                    System.out.println(e);
                }
            }
        }
    }
}
