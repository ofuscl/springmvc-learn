package biz.filter;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.io.Serializable;

/**
 * Created by yunfan on 2018/8/3.
 */
@Data
public class TesteeVo extends TestVo implements Serializable{

    private static final long serialVersionUID = 1L;

    /** 和基类相比，删除的字段  @JsonIgnore：在json序列化时将java bean中的一些属性忽略掉，序列化和反序列化都受影响*/
    @JsonIgnore
    private String desc;

    /** 和基类相比，新增的字段 */
    private String phone;
}
