package shop.model;

import org.bson.Document;

public class User implements IDaoItem {
    public static final String DAO_NAME = "User";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_ID = "_id";
    public static final String FIELD_CURRENCY = "currency";

    public final String name;
    public final Cost.CurrencyTypes currency;
    private final String id;

    private User(final String name, final Cost.CurrencyTypes currency, final String id) {
        this.name = name;
        this.currency = currency;
        this.id = id;
    }

    public User(final String name, final Cost.CurrencyTypes currency) {
        this(name, currency, "");
    }

    public static User fromDocument(final Document document) {
        return new User(
                document.getString(FIELD_NAME),
                Cost.CurrencyTypes.valueOf(document.getString(FIELD_CURRENCY)),
                document.getObjectId(FIELD_ID).toString()
        );
    }

    public Document toDocument() {
        final Document document = new Document();
        if (!id.isEmpty()) {
            document.append(FIELD_ID, id);
        }
        document.append(FIELD_NAME, name);
        document.append(FIELD_CURRENCY, currency.toString());
        return document;
    }
}
