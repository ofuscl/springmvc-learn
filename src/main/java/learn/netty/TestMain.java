package learn.netty;

import learn.netty.rpc.HelloRpc;
import learn.netty.rpc.HelloRpcImpl;

public class TestMain {

    public static void main(String[] args) {

        testone();

//        testAsync();
    }

    private static void testone(){

        RpcInvokeHook hook = new RpcInvokeHook() {
            public void beforeInvoke(String method, Object[] args) {
                System.out.println("before invoke " + method);
            }

            public void afterInvoke(String method, Object[] args) {
                System.out.println("after invoke " + method);
            }
        };

        HelloRpc helloRpc = new HelloRpcImpl();
        HelloRpc testInterface = RpcClientProxyBuilder.create(HelloRpc.class).timeout(0)
                .hook(hook).connect("localhost", 8080).build();

        System.out.println("invoke result = " + helloRpc.hello("qwerty"));

    }

    private static void testAsync(){
        RpcInvokeHook hook = new RpcInvokeHook() {
            public void beforeInvoke(String method, Object[] args) {
                System.out.println("before invoke " + method);
            }

            public void afterInvoke(String method, Object[] args) {
                System.out.println("after invoke " + method);
            }
        };

        RpcClientAsyncProxy rpcClientAsyncProxy = RpcClientProxyBuilder.create(TestInterface.class)
                .timeout(0).hook(hook).connect("127.0.0.1", 3721).buildAsyncProxy();
        RpcFuture rpcFuture = rpcClientAsyncProxy.call("testMethod01", "qwerty");
        rpcFuture.setRpcFutureListener(new RpcFutureListener() {
            public void onResult(Object result) {
                System.out.println("RpcFutureListener result = " + result.toString());
            }

            public void onException(Throwable throwable) {
                System.out.println("RpcFutureListener onException");
            }
        });

        System.out.println("RpcFuture isDone = " + rpcFuture.isDone());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("RpcFuture isDone = " + rpcFuture.isDone());
        try {
            System.out.println("result = " + rpcFuture.get());
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }
}
