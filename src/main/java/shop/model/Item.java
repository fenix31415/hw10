package shop.model;

import org.bson.Document;

public class Item implements IDaoItem {
    public static final String DAO_NAME = "Item";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_ID = "_id";
    public static final String FIELD_COST = "cost";

    public final Cost cost;
    public final String name;
    private final String id;

    private Item(final String name, final Cost cost, final String id) {
        this.name = name;
        this.cost = cost;
        this.id = id;
    }

    public Item(final String name, final Cost cost) {
        this(name, cost, "");
    }

    public static Item fromDocument(final Document document) {
        return new Item(
                document.getString(FIELD_NAME),
                new Cost(document.getDouble(FIELD_COST)),
                document.getObjectId(FIELD_ID).toString()
        );
    }

    public Document toDocument() {
        final Document document = new Document();
        if (!id.isEmpty()) {
            document.append(FIELD_ID, id);
        }
        document.append(FIELD_NAME, name);
        document.append(FIELD_COST, cost.getBaseValue());
        return document;
    }
}
