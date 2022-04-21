package shop.dao;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.rx.client.*;
import org.bson.Document;
import rx.Observable;
import shop.model.IDaoItem;

public abstract class MongoDaoBase {
    protected final MongoDatabase mongoDatabase;
    protected MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");

    public MongoDaoBase() {
        this.mongoDatabase = mongoClient.getDatabase("default");
    }

    protected MongoCollection<Document> getCollection(final String name) {
        return mongoDatabase.getCollection(name);
    }

    public Observable<DeleteResult> drop() {
        return getCollection().deleteMany(new Document());
    }

    protected abstract MongoCollection<Document> getCollection();

    public Observable<Success> add(final IDaoItem item) {
        return getCollection().insertOne(item.toDocument());
    }
}
