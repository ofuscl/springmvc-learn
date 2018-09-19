package learn.netty.rpc;

public class NettyRpcMain {

    public static void main(String[] args) {

        HelloRpc helloRpc = new HelloRpcImpl();
        HelloRpc echo = RPCProxy.create(helloRpc);
        System.out.println(echo);
        System.out.println(helloRpc.hello("这是我的第一个手写rpc!"));
    }
}
