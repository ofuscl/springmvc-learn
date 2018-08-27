package learn.file.excel;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
public class Staff {

    public static Map<String,Integer> ITEM_MAP = new HashMap<>();
    static {
        ITEM_MAP.put("birthDay",0);
        ITEM_MAP.put("name",1);
        ITEM_MAP.put("payment",2);
        ITEM_MAP.put("bonus",3);
        ITEM_MAP.put("sex",4);
    }

    public Staff(){

    }
    /**
     * 年龄
     */
    private String birthDay;

    /**
     * 名称
     */
    private String name;

    /**
     * 薪资
     */
    private String payment;

    /**
     * 年终奖
     */
    private String bonus;

    /**
     * 性别
     */
    private String sex;
}

