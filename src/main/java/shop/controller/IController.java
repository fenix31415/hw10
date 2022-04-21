package shop.controller;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import rx.Observable;

public interface IController {
    Observable<Void> handleRequest(final HttpServerRequest<ByteBuf> request, final HttpServerResponse<ByteBuf> response);
}
