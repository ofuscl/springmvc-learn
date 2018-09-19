package learn.netty.rpc;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.apache.poi.ss.formula.functions.T;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class RPCProxy {
    @SuppressWarnings("unchecked")

    static class RpcHandler implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            ClassInfo classInfo = new ClassInfo();
            classInfo.setClassName(proxy.getClass().getName());
            classInfo.setMethodName(method.getName());
            classInfo.setObjects(args);
            classInfo.setTypes(method.getParameterTypes());

            final ResultHandler resultHandler = new ResultHandler();

            EventLoopGroup group = new NioEventLoopGroup();
            try {
                Bootstrap b = new Bootstrap()
                        .group(group)
                        .channel(NioSocketChannel.class)
                        .option(ChannelOption.TCP_NODELAY, true)
                        .handler(new ChannelInitializer() {
                    @Override
                    public void initChannel(Channel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                        pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
                        pipeline.addLast("encoder", new ObjectEncoder());
                        pipeline.addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                        pipeline.addLast("handler",resultHandler);
                    }
                });

                ChannelFuture future = b.connect("localhost", 8080).sync();
                future.channel().writeAndFlush(classInfo).sync();
                future.channel().closeFuture().sync();
            } finally {
                group.shutdownGracefully();
            }
            return resultHandler.getResponse();
        }
    }

    public static HelloRpc create(HelloRpc target){
        return (HelloRpc) Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces(), new RpcHandler());
    }

}

