package util.sql;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 阿里云慢SQL邮件每日都会发送，需要分析哪些是真正有问题的
 * Created by YScredit on 2018/12/20.
 */
public class SlowSqlAnalysisUtils {

    public static void main(String[] args) {

        SlowSqlAnalysisUtils slowSqlObj = new SlowSqlAnalysisUtils();
        List<SqlItem> youhaoList = slowSqlObj.fileRead("slowsql/slowsql_youhao.txt");
        slowSqlObj.analysisSql("youhao",youhaoList);
        System.out.println("========================================================================");
        List<SqlItem> zxList = slowSqlObj.fileRead("slowsql/slowsql_zx.txt");
        slowSqlObj.analysisSql("zx",zxList);
    }

    private void analysisSql(String dbName,List<SqlItem> dataList){

        int tangjieCount = 0;
        int threeExecCount = 0;
        int twoSecondCount = 0;
        int tjCount = 0;
        int other = 0;
        for(SqlItem sqlItem : dataList){
            if(Integer.valueOf(sqlItem.getMaxExecTime()) <= 2){
                twoSecondCount++;
                continue;
            }
            if(sqlItem.getSqlText().indexOf("date_add ") > 0){
                tangjieCount++;
                continue;
            }
            if(Integer.valueOf(sqlItem.getTotalExecCounts()) <= 3 && Integer.valueOf(sqlItem.getMaxExecTime()) <= 5){
                threeExecCount++;
                continue;
            }
            if(sqlItem.getSqlText().indexOf("count ") > 0){
                tjCount++;
                continue;
            }
            other++;
            System.out.println(sqlItem.getSqlText());
        }

        System.out.println(dbName+"库，总的慢SQL：共"+dataList.size()+"条");
        System.out.println(dbName+"库，执行时长为2秒的SQL：共"+twoSecondCount+"条");
        System.out.println(dbName+"库，唐杰导数据涉及SQL：共"+tangjieCount+"条");
        System.out.println(dbName+"库，仅仅只执行一次SQL,且不超过5s的：共"+threeExecCount+"条");
        System.out.println(dbName+"库，统计类的SQL：共"+tjCount+"条");
        System.out.println(dbName+"库，其余SQL：共"+other+"条");
    }

    private List<SqlItem> fileRead(String filePath){

        List<SqlItem> dataList = new ArrayList<>();

        String path = this.getClass().getResource("/").getPath() + filePath;
        LineIterator it = null;
        try {

            it = FileUtils.lineIterator(new File(path), "utf-8");
            int num = 0;
            while (it.hasNext()) {
                num++;
                String line = it.nextLine();
                String[] lines = line.split("\t");
                if (lines.length < 8) {
                    System.out.println(line + " 格式异常!");
                    continue;
                }
                if(num == 1){
                    continue;
                }
                dataList.add(new SqlItem(lines[1],lines[2],lines[3],lines[4],lines[5],lines[6],lines[7]));
            }
//            System.out.println("数据初始化完成，共"+num+"条");
        } catch (Exception e){
            System.out.println("系统异常："+e.getMessage());
        } finally {
            LineIterator.closeQuietly(it);
        }

        return dataList;
    }

    @AllArgsConstructor
    @Getter
    @Setter
    private class SqlItem{

        /** 数据库名称 */
        private String dbName;
        /** SQL内容 */
        private String sqlText;
        /** 总执行次数 */
        private String totalExecCounts;
        /** 总执行时间 */
        private String totalExecTimes;
        /** 最大执行时间 */
        private String maxExecTime;
        /** 锁表总时长 */
        private String lockTimes;
        /** 锁表时间 */
        private String lockTime;

        public String getDbName() {
            return dbName;
        }

        public void setDbName(String dbName) {
            this.dbName = dbName;
        }

        public String getSqlText() {
            return sqlText;
        }

        public void setSqlText(String sqlText) {
            this.sqlText = sqlText;
        }

        public String getTotalExecCounts() {
            return totalExecCounts;
        }

        public void setTotalExecCounts(String totalExecCounts) {
            this.totalExecCounts = totalExecCounts;
        }

        public String getTotalExecTimes() {
            return totalExecTimes;
        }

        public void setTotalExecTimes(String totalExecTimes) {
            this.totalExecTimes = totalExecTimes;
        }

        public String getMaxExecTime() {
            return maxExecTime;
        }

        public void setMaxExecTime(String maxExecTime) {
            this.maxExecTime = maxExecTime;
        }

        public String getLockTimes() {
            return lockTimes;
        }

        public void setLockTimes(String lockTimes) {
            this.lockTimes = lockTimes;
        }

        public String getLockTime() {
            return lockTime;
        }

        public void setLockTime(String lockTime) {
            this.lockTime = lockTime;
        }
    }
}
