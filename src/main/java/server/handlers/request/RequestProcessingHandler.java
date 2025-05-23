package server.handlers.request;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import server.entity.exceptions.AccessDeniedException;
import server.entity.exceptions.NotLoggedIn;
import server.entity.exceptions.NotUniqueName;

import java.util.NoSuchElementException;

public class RequestProcessingHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        try {
            ctx.write(new CommandMessageHandler().handle((String) msg, ctx.channel().id().toString()));
        }
        catch (NoSuchElementException e) {
            ctx.write("The specified topic or vote was not found");
        }
        catch (NotLoggedIn | NotUniqueName | AccessDeniedException e ) {
            ctx.write(e.getMessage());
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }
}
