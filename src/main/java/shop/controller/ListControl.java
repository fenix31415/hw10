package shop.controller;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import shop.Utils;
import shop.dao.ItemsDao;
import shop.dao.UsersDao;
import rx.Observable;

import java.util.List;
import java.util.Map;

public class ListControl extends ControlBase {
    private final ItemsDao itemsDao;
    private final UsersDao usersDao;

    public ListControl(final ItemsDao itemsDao, final UsersDao usersDao) {
        this.itemsDao = itemsDao;
        this.usersDao = usersDao;
    }

    @Override
    public Observable<String> handleRequestImpl(final HttpServerRequest<ByteBuf> request) throws HttpException {
        final Map<String, List<String>> parameters = request.getQueryParameters();
        final String userName = Utils.getArgument(parameters, "user");
        return usersDao.get(userName)
                .flatMap(user -> Observable.merge(Observable.just(
                        "Hello, " + userName + "! Costs in " + user.currency.name() + ":" + System.lineSeparator()),
                        itemsDao.getAll().map(item -> item.name + ": " + item.cost.getValue(user.currency) + System.lineSeparator()))
                );
    }
}
