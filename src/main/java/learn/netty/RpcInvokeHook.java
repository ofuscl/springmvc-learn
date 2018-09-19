package learn.netty;

public interface RpcInvokeHook {

    public void beforeInvoke(String methodName, Object[] args);
    public void afterInvoke(String methodName, Object[] args);
}
