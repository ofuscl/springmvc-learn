package learn.file.excel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class ExcelSheet<T> {

    private String sheetName;
    private String sheetCode;
    private int firstRow;
    private Class<T> clazz;
    private Map<String,Integer> indexMap;
}
