package biz.file.excel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.CharsetUtil;
import util.IOUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * 报表下载工具类
 *
 * @author yunfan
 */
public class ExcelDownloadUtil {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(ExcelDownloadUtil.class);

    private static final String DOWN_LOAD_PATH = "F:/test/";

    public static void main(String[] args) {

        String fileName = "demo_alone.xlsx";
        String templatePath = ExcelDownloadUtil.class.getResource("/").getPath() + "template/excel/"+"demo_alone.xlsx";
        HttpServletRequest request = null;
        HttpServletResponse response = null;
        Map<String, Object> params = new HashMap<>();
        download(request,response,params,fileName,templatePath);
    }

    public static void download(HttpServletRequest request, HttpServletResponse response,Map<String, Object> params, String templateName, String fileName) {

        OutputStream os = null;
        InputStream stream = null;
        File tempFile = null;
        String filePath = DOWN_LOAD_PATH+fileName;
        try {
            // 生成excel
            ExcelExportUtil.alone(params,templateName,fileName);

            // 读取excel
            tempFile = new File(filePath);
            if(!tempFile.exists()){
                logger.error("导出Excel失败！");
                return;
            }

            fileName = getDownloadFileName(request,fileName);
            logger.info("path:{}--fileName:{}", filePath);
            response.reset();//来清除首部的空白行
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/octet-stream");//以流的形式下载文件
            response.addHeader("Content-Disposition", "attachment; fileName=" + fileName);//设置附件的名称
            response.addHeader("Content-Length", String.valueOf(tempFile.length()));//设置附件的大小
            os = response.getOutputStream();
            byte[] b = new byte[2048];
            int length;
            stream = new FileInputStream(filePath);
            while ((length = stream.read(b)) > 0) {
                os.write(b, 0, length);
            }
        } catch (Exception e) {
            logger.error("下载文件失败：{}", e);
        } finally {
            IOUtil.close(stream);
            IOUtil.close(os);
            //删除临时文件
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
        }
    }

//    public void downloadPicZip(Integer apprId, String entName, HttpServletRequest request, HttpServletResponse response) {
//        InputStream stream = null;
//        ZipOutputStream zos = null;
//        try {
//            StringBuffer sb = new StringBuffer();
//            String downloadFilename = sb.append(entName).append("_").append(DateUtils.getDate(DateUtils.parsePatterns[14])).append(".zip").toString();//文件的名称
//            downloadFilename = FileDownloadUtil.getDownloadFileName(downloadFilename, request);
//            response.setContentType("application/octet-stream");// 指明response的返回对象是文件流
//            response.setHeader("Content-Disposition", "attachment;filename=" + downloadFilename);// 设置在下载框默认显示的文件名
//            zos = new ZipOutputStream(response.getOutputStream());
//            for (SysPictureBo sysPictureBo : sysPictureBoList) {
//                String path = sysPictureBo.getPicPath().replace(ConfigConstants.FILE_BASE_PATH, ConfigConstants.FILE_BASE_URL);
//                URL url = new URL(path);
//                zos.putNextEntry(new ZipEntry(path.replace(ConfigConstants.FILE_BASE_URL, "")));
//                stream = url.openConnection().getInputStream();
//                byte[] buffer = new byte[1024];
//                int r = 0;
//                while ((r = stream.read(buffer)) != -1) {
//                    zos.write(buffer, 0, r);
//                }
//                stream.close();
//            }
//            zos.flush();
//            zos.close();
//
//        } catch (Exception e) {
//            logger.error("下载图片失败[{}]：{}", apprId, e.getMessage(), e);
//        } finally {
//            // 这里主要关闭。
//            if (stream != null) {
//                try {
//                    stream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (zos != null) {
//                try {
//                    zos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

    /**
     * 根据浏览器类型获取下载的文件名称
     *
     * @param fileName 下载文件名称
     * @param request 请求
     * @return String   处理后的文件名称
     * @throws UnsupportedEncodingException
     */
    public static String getDownloadFileName(HttpServletRequest request,String fileName) throws UnsupportedEncodingException {

        String reqAgent = request.getHeader("User-Agent");//浏览器类型
        if (reqAgent.contains("Trident") || reqAgent.contains("MSIE")) {
            return CharsetUtil.encodeURL(fileName, "UTF-8").replace("+", "%20");
        } else if (reqAgent.contains("Edge")) {
            return new String(fileName.getBytes("gb2312"), "ISO8859-1");
        } else {
            return new String(fileName.getBytes("utf-8"), "iso-8859-1");
        }
    }
}




