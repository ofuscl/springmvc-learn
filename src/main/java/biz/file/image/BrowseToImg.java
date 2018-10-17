package biz.file.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

/**
 * 自动打开网页完成截图
 * Created by yunfan on 2018/5/19.
 */
public class BrowseToImg {

    public static void main(String[] args) {

        try{

            Desktop.getDesktop().browse(
                    new URL("http://www.gsxt.gov.cn/%7B62DECDE805B83C65E9CC67DA56CB806D487E32BE0335A88F9C21CCE9DF479CB91224B99E8D307C25CB267F91DD763A874EA1A289536850447F7C7E6A514E18123D913D913D3718B49BB449E549E549E949E5CA5BF7B11DBD11BD92BD407E99D24ABD068A175C359E73FF4C634C46EA602BF01EAD21BCF709429977FDB1E8064A7C37BB08278B278B278B-1526711390826%7D").toURI());
            Robot robot = new Robot();
            robot.delay(10000);
            Dimension d = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
            int width = (int) d.getWidth();
            int height = (int) d.getHeight();
            System.out.println("width : "+width +" | height :"+height);
            //最大化浏览器
            robot.keyRelease(KeyEvent.VK_F11);
            robot.delay(2000);
            Image image = robot.createScreenCapture(new Rectangle(0, 0, width,
                    height));
            BufferedImage bi = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = bi.createGraphics();
            g.drawImage(image, 0, 0, width, height, null);
            //保存图片
            ImageIO.write(bi, "png", new File("F:/tmp/newxxx.png"));
            System.out.println("OK");
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
