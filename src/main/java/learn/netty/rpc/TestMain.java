package learn.netty.rpc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TestMain {

    static class RpcHandler implements InvocationHandler{
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            ClassInfo classInfo = new ClassInfo();
            classInfo.setClassName(proxy.getClass().getName());
            classInfo.setMethodName(method.getName());
            classInfo.setObjects(args);
            classInfo.setTypes(method.getParameterTypes());
            return classInfo;
        }
    }


    public static <T> T create(final Object target){
        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces(), new RpcHandler());
    }
}
