package shop.controller;

public class HttpException extends Exception {
    private final int errCode;

    public HttpException(final int errCode, final String message) {
        super(message);
        this.errCode = errCode;
    }

    public int getErrCode() {
        return errCode;
    }
}
