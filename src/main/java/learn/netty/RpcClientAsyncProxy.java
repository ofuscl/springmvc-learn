package learn.netty;

public class RpcClientAsyncProxy {


    private RpcClient rpcClient;

    public RpcClientAsyncProxy(RpcClient rpcClient)
    {
        this.rpcClient = rpcClient;
    }

    public RpcFuture call(String methodName, Object ... args) {
        return (RpcFuture) rpcClient.call(methodName, args);
    }
}
