package shop.controller;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import rx.Observable;

import java.io.PrintWriter;
import java.io.StringWriter;

public abstract class ControlBase implements IController {
    private String getErrorString(final Throwable e) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return pw.toString();
    }

    public Observable<Void> handleRequest(final HttpServerRequest<ByteBuf> request,
                                          final HttpServerResponse<ByteBuf> response) {
        Observable<String> result;
        try {
            result = this.handleRequestImpl(request)
                    .onErrorReturn(error -> {
                        if (error instanceof HttpException) {
                            final HttpException httpException = (HttpException) error;
                            response.setStatus(HttpResponseStatus.valueOf(httpException.getErrCode()));
                            return httpException.getMessage() + System.lineSeparator();
                        } else {
                            response.setStatus(HttpResponseStatus.INTERNAL_SERVER_ERROR);
                            return getErrorString(error) + System.lineSeparator();
                        }
                    });
        } catch (final HttpException httpException) {
            response.setStatus(HttpResponseStatus.valueOf(httpException.getErrCode()));
            result = Observable.just(httpException.getMessage() + System.lineSeparator());
        } catch (final Exception e) {
            response.setStatus(HttpResponseStatus.INTERNAL_SERVER_ERROR);
            result = Observable.just(getErrorString(e) + System.lineSeparator());
        }
        return response.writeString(result);
    }

    public abstract Observable<String> handleRequestImpl(final HttpServerRequest<ByteBuf> request) throws HttpException;
}
