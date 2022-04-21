package shop.dao;

import com.mongodb.rx.client.MongoCollection;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.Document;
import rx.Observable;
import shop.model.User;

public class UsersDao extends MongoDaoBase {
    public UsersDao() {
        super();
    }

    protected MongoCollection<Document> getCollection() {
        return getCollection(User.DAO_NAME);
    }

    public Observable<User> get(final String name) {
        return getCollection()
                .find(new BsonDocument(User.FIELD_NAME, new BsonString(name)))
                .toObservable().map(User::fromDocument);
    }
}
