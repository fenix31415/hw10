package shop;

import shop.dao.ItemsDao;
import shop.dao.UsersDao;

public class Main {
    public static void main(final String[] args) {
        new Server(new UsersDao(), new ItemsDao()).start();
    }
}
