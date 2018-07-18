package learn.file.image;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Created by YScredit on 2018/5/19.
 */
public class PhantomTools {

    private static final Logger _logger = LoggerFactory.getLogger(PhantomTools.class);

    private static final String _tempPath = "F:/temp/phantom_";
    private String basePath;
    private static final String _shellCommand = "E:/phantomjs-2.1.1-windows/bin/phantomjs";
    private static final String _shellCommand1 = "E:/phantomjs-2.1.1-windows/bin/phantomjs ";
    private static final String _shellCommand2 = "E:/phantomjs-2.1.1-windows/examples/rasterize.js ";

    private String _file;
    private String _size;

    public static void main(String[] args) throws Exception{
        PhantomTools tools = new PhantomTools(1,null);
        tools.getByteImg("http://wenshu.court.gov.cn/list/list/?sorttype=1&number=SCEL3X4Q&guid=5f79f1a1-c16a-0f6df970-eacaafa21c77&conditions=searchWord+QWJS+++%E5%85%A8%E6%96%87%E6%A3%80%E7%B4%A2:%E6%9D%AD%E5%B7%9E%E6%9C%89%E6%95%B0%E9%87%91%E8%9E%8D%E4%BF%A1%E6%81%AF%E6%9C%8D%E5%8A%A1%E6%9C%89%E9%99%90%E5%85%AC%E5%8F%B8");
    }
    /**
     * 构造截图类
     * @param hash 用于临时文件的目录唯一化
     * @param basePath phantomJs所在路径
     */
    public PhantomTools(int hash, String basePath) {
        _file = _tempPath + hash + ".png";
        this.basePath = basePath;
    }

    /**
     * 构造截图类
     * @param hash 用于临时文件的目录唯一化
     * @param size 图片的大小，如800px*600px（此时高度会裁切），或800px（此时 高度最少=宽度*9/16，高度不裁切）
     * @param basePath phantomJs所在路径
     */
    public PhantomTools(int hash, String size, String basePath) {
        _file = _tempPath + hash + ".png";
        if (size != null)
            _size = " " + size;
        this.basePath = basePath;
    }

    /**
     * 将目标网页转为图片字节流
     * @param url 目标网页地址
     * @return 字节流
     */
    public byte[] getByteImg(String url) throws IOException {
        BufferedInputStream in = null;
        ByteArrayOutputStream out = null;
        File file = null;
        byte[] ret = null;
        try {
            if (exeCmd( _shellCommand1 + _shellCommand2 + url + " " + _file + (_size != null ? _size : ""))) {
                file = new File(_file);
                if (file.exists()) {
                    out = new ByteArrayOutputStream();
                    byte[] b = new byte[5120];
                    in = new BufferedInputStream(new FileInputStream(file));
                    int n;
                    while ((n = in.read(b, 0, 5120)) != -1) {
                        out.write(b, 0, n);
                    }
                    file.delete();
                    ret = out.toByteArray();
                }
            } else {
                ret = new byte[] {};
            }
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                _logger.error(e.getMessage());
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                _logger.error(e.getMessage());
            }
            if (file != null && file.exists()) {
                file.delete();
            }
        }
        return ret;
    }

    /**
     * 执行CMD命令
     */
    private static boolean exeCmd(String commandStr) {
        BufferedReader br = null;
        try {
            Process p = Runtime.getRuntime().exec(commandStr);
            if (p.waitFor() != 0 && p.exitValue() == 1) {
                return false;
            }
        } catch (Exception e) {
            _logger.error(e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    _logger.error(e.getMessage());
                }
            }
        }
        return true;
    }
}
