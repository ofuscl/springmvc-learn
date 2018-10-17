package biz.file.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * Created by YScredit on 2018/10/17.
 */
public class ZipFileTest {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(ZipFileTest.class);
    
    public static void main(String[] args) {
        zipFile("F:\\test\\unzip","F:\\test\\test2.zip");

        unzipFile("F:\\test\\test2.zip","F:\\test\\unzip");
    }

    /**
     * 压缩文件
     */
    public static void zipFile(String srcPath,String destPath){
        File zipFile = new File(destPath);
        File directory = new File(srcPath);
        try (FileOutputStream fileOutputStream = new FileOutputStream(zipFile);ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);){
            // Create zip file
            createZipFile(zipOutputStream,directory,directory.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param zipOutputStream
     * @param directory
     * @throws IOException
     */
    private static void createZipFile(ZipOutputStream zipOutputStream,File directory,String zipEntryPath) throws IOException {
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                createZipFile(zipOutputStream,file,zipEntryPath);
            } else {
                try (FileInputStream fileInputStream = new FileInputStream(file);){
                    // Create zipEntry
                    String filePath = file.getAbsolutePath();
                    ZipEntry entry = new ZipEntry(filePath.replace(zipEntryPath + File.separator,""));

                    // Set position of stream to the start of entry data
                    zipOutputStream.putNextEntry(entry);

                    // Write data to zip output stream
                    byte data[] = new byte[1024];
                    int i;
                    while ((i = fileInputStream.read(data)) != -1) {
                        zipOutputStream.write(data,0,i);
                    }
                    // Closes the current ZIP entry
                    zipOutputStream.closeEntry();

                } catch(Exception e){
                    logger.error("压缩出错",e);
                    break;
                }
            }
        }
    }

    /**
     * 解压文件
     */
    public static void unzipFile(String srcPath,String destPath){
        File output = new File(destPath);
        try (ZipFile zipFile = new ZipFile(new File(srcPath))){
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                if (entry.isDirectory()) {
                    // Create directory
                    File dir = new File(output,entry.getName());
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                } else {
                    try (FileOutputStream fileOutputStream = new FileOutputStream(new File(output,entry.getName()));InputStream inputStream = zipFile.getInputStream(entry);){
                        // Read Zip file entry
                        int i;
                        byte[] data = new byte[1024];
                        while ((i = inputStream.read(data)) != -1) {
                            fileOutputStream.write(data,0,i);
                        }
                    } catch(Exception e){
                        System.out.println("解压失败:"+e);
                        break;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
