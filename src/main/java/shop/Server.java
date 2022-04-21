package shop;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServer;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import rx.Observable;
import shop.controller.*;
import shop.dao.ItemsDao;
import shop.dao.UsersDao;

public class Server {
    private static class UnkCommand implements IController{
        @Override
        public Observable<Void> handleRequest(HttpServerRequest<ByteBuf> request, HttpServerResponse<ByteBuf> response) {
            return response.writeString(Observable.just("Unknown command" + System.lineSeparator()));
        }
    }

    private static class Dropper extends ControlBase {
        private final ItemsDao itemsDao;
        private final UsersDao usersDao;

        public Dropper(final ItemsDao itemsDao, final UsersDao usersDao) {
            this.itemsDao = itemsDao;
            this.usersDao = usersDao;
        }

        @Override
        public Observable<String> handleRequestImpl(HttpServerRequest<ByteBuf> request) {
            return Observable.merge(
                    itemsDao.drop().map(r -> "Drop items." + System.lineSeparator()),
                    usersDao.drop().map(r -> "Drop users." + System.lineSeparator())
            );
        }
    }

    private final NewUserControl newUserControl;
    private final NewItemControl newItemControl;
    private final ListControl listControl;
    private final UnkCommand unkCommand;
    private final Dropper dropper;

    private IController getController(final String command) {
        return switch (command) {
            case "new-user" -> newUserControl;
            case "new-item" -> newItemControl;
            case "list-items" -> listControl;
            case "drop" -> dropper;
            default -> unkCommand;
        };
    }

    public Server(final UsersDao usersDao, final ItemsDao itemsDao) {
        newUserControl = new NewUserControl(usersDao);
        newItemControl = new NewItemControl(itemsDao);
        listControl = new ListControl(itemsDao, usersDao);
        unkCommand = new UnkCommand();
        dropper = new Dropper(itemsDao, usersDao);
    }

    public void start() {
        HttpServer.newServer(8080)
                .start((req, resp) -> getController(req.getDecodedPath().substring(1)).handleRequest(req, resp))
                .awaitShutdown();
    }
}
