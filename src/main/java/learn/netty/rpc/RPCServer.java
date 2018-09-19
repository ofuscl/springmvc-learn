package learn.netty.rpc;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 *
 * EventLoop 这个相当于一个处理线程，是Netty接收请求和处理IO请求的线程
 * EventLoopGroup 可以理解为将多个EventLoop进行分组管理的一个类，是EventLoop的一个组
 * ServerBootstrap 从命名上看就可以知道，这是一个对服务端做配置和启动的类
 * ChannelPipeline 这是Netty处理请求的责任链，这是一个ChannelHandler的链表，而ChannelHandler就是用来处理网络请求的内容的
 * ChannelHandler 用来处理网络请求内容，有ChannelInboundHandler和ChannelOutboundHandler两种
 * ChannlPipeline会从头到尾顺序调用ChannelInboundHandler处理网络请求内容，从尾到头调用ChannelOutboundHandler处理网络请求内容
 */
public class RPCServer {

    private int port;

    public RPCServer(int port){
        this.port = port;
    }

    public void start(){

        // 首先是设置EverLoopGroup，parentGroup一般用来接收accpt请求，childGroup用来处理各个连接的请求。
        // 不过根据开发的不同需求也可以用同一个group同时作为parentGroup和childGroup同时处理accpt请求和其他io请求
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap()
                    .group(parentGroup, childGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(port)
                    .option(ChannelOption.SO_BACKLOG, 128) // 标识当服务器请求处理线程全满时，用于临时存放已完成三次握手的请求的队列的最大长度
                    .childOption(ChannelOption.SO_KEEPALIVE, true)//是否启用心跳保活机制
                    .childHandler(new ChannelInitializer() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                            pipeline.addLast(new LengthFieldPrepender(4));
                            pipeline.addLast("encoder", new ObjectEncoder());
                            pipeline.addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                            pipeline.addLast(new InvokerHandler());
                        }});

                        ChannelFuture future = serverBootstrap.bind(port).sync();
                        System.out.println("Server start listen at " + port );
                        future.channel().closeFuture().sync();
            } catch (Exception e) {
                parentGroup.shutdownGracefully();
                childGroup.shutdownGracefully();
            }

    }

    public static void main(String[] args) throws Exception {

        int port;

        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        new RPCServer(port).start();
    }
}
