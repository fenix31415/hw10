package shop.dao;

import com.mongodb.rx.client.MongoCollection;
import org.bson.Document;
import rx.Observable;
import shop.model.Item;

public class ItemsDao extends MongoDaoBase {
    public ItemsDao() {
        super();
    }

    protected MongoCollection<Document> getCollection() {
        return getCollection(Item.DAO_NAME);
    }

    public Observable<Item> getAll() {
        return getCollection().find().toObservable().map(Item::fromDocument);
    }
}
