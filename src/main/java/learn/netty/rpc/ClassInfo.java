package learn.netty.rpc;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ClassInfo implements Serializable {

    private static final long serialVersionUID = -1L;

    private String className;//类名     

    private String methodName;//函数名称      

    private Class[] types;//参数类型        

    private Object[] objects;//参数列表

}
