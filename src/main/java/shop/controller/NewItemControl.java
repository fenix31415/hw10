package shop.controller;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import shop.Utils;
import shop.dao.ItemsDao;
import shop.model.Cost;
import shop.model.Item;
import rx.Observable;

import java.util.List;
import java.util.Map;

public class NewItemControl extends ControlBase {
    private final ItemsDao itemsDao;

    public NewItemControl(final ItemsDao itemsDao) {
        this.itemsDao = itemsDao;
    }

    @Override
    public Observable<String> handleRequestImpl(final HttpServerRequest<ByteBuf> request) throws HttpException {
        final Map<String, List<String>> parameters = request.getQueryParameters();
        final String itemName = Utils.getArgument(parameters, "name");
        final double cost = Utils.getCostParameter(parameters);
        final Cost.CurrencyTypes currency = Utils.getCurrencyParameter(parameters);
        return itemsDao.add(new Item(itemName, new Cost(currency, cost)))
                .map(e -> "Item " + itemName + " (price " + currency + " " + cost + ") added."  + System.lineSeparator());
    }
}
