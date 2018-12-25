package biz;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by YScredit on 2018/12/20.
 */
public class FileRead {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(FileRead.class);
    /** 数据 */
    public static final List<Map<String,String>> dataList = new ArrayList<>();

    //    @PostConstruct
    public void init() throws Exception {
        String path = this.getClass().getResource("/").getPath() + "constants/test.txt";
        LineIterator it = FileUtils.lineIterator(new File(path), "utf-8");
        try {
            int num =0;
            while (it.hasNext()) {
                num++;
                String line = it.nextLine();
                String[] lines = line.split("\t");
                if (lines.length < 4){
                    logger.error(line+" 格式异常!");
                    continue;
                }
                Map<String,String> itemMap = new HashMap();
                for (int i=0;i < lines.length; i++){
                    itemMap.put("row"+i+1,lines[i]);
                }
                dataList.add(itemMap);
            }
            logger.info("数据初始化完成，共{}条", num);
        } finally {
            LineIterator.closeQuietly(it);
        }
    }
}
