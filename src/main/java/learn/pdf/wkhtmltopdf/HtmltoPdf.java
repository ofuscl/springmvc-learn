package learn.pdf.wkhtmltopdf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by YScredit on 2018/4/26.
 */
public class HtmltoPdf {
    private static final Logger LOG = LoggerFactory.getLogger(HtmltoPdf.class);

    private static final String TOPDFTOOL = "E:/wkhtmltopdf/bin/wkhtmltopdf";

    /**
     * html转pdf
     * @param srcPath html路径，可以是硬盘上的路径，也可以是网络路径
     * @param destPath pdf保存路径
     * @return 转换成功返回true
     */
    public static boolean convert(String srcPath, String destPath) {

        File file = new File(destPath);
        File parent = file.getParentFile();
        // 如果pdf保存路径不存在，则创建路径
        if (!parent.exists()) {
            parent.mkdirs();
        }

        StringBuilder cmd = new StringBuilder();
        cmd.append(TOPDFTOOL);
        cmd.append(" ");
        cmd.append("--page-size A2");// 参数
        cmd.append(" ");
        cmd.append(srcPath);
        cmd.append(" ");
        cmd.append(destPath);

        boolean result = true;
        try {
            Process proc = Runtime.getRuntime().exec(cmd.toString());
            HtmlToPdfInter error = new HtmlToPdfInter(
                    proc.getErrorStream());
            HtmlToPdfInter output = new HtmlToPdfInter(
                    proc.getInputStream());
            error.start();
            output.start();
            proc.waitFor();
            LOG.info("HTML2PDF成功，参数---html路径：{},pdf保存路径 ：{}", new Object[] {srcPath, destPath });
        } catch (Exception e) {
            LOG.error("HTML2PDF失败，srcPath地址：{},错误信息：{}", new Object[]{srcPath, e.getMessage()});
            result = false;
        }
        return result;
    }

    public static void main(String[] args) {
        String url = "https://www.aliyun.com/jiaocheng/240150.html?spm=5176.100033.9.11.02LbjR";
        String path = "F:/baidu.pdf";

        HtmltoPdf.convert(url,path);
    }
}
