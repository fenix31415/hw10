package shop.controller;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import shop.Utils;
import shop.dao.UsersDao;
import shop.model.Cost;
import shop.model.User;
import rx.Observable;

import java.util.List;
import java.util.Map;

public class NewUserControl extends ControlBase {
    private final UsersDao usersDao;

    public NewUserControl(final UsersDao usersDao) {
        this.usersDao = usersDao;
    }

    @Override
    public Observable<String> handleRequestImpl(final HttpServerRequest<ByteBuf> request) throws HttpException {
        final Map<String, List<String>> parameters = request.getQueryParameters();
        final String userName = Utils.getArgument(parameters, "user");
        final Cost.CurrencyTypes currency = Utils.getCurrencyParameter(parameters);
        return usersDao.add(new User(userName, currency))
                .map(u -> "User " + userName + " added." + System.lineSeparator());
    }
}
