package shop.model;

import java.util.Map;

public class Cost {
    private final static Map<CurrencyTypes, Double> coeffsBase =
            Map.of(CurrencyTypes.RUB, 0.01, CurrencyTypes.EUR, 0.5, CurrencyTypes.USD, 1.0);

    private final double val;

    public Cost(final double baseValue) {
        this.val = baseValue;
    }

    public Cost(final CurrencyTypes type, final double value) {
        this.val = value / coeffsBase.get(type);
    }

    public double getValue(CurrencyTypes type) {
        return val * coeffsBase.get(type);
    }

    public double getBaseValue() {
        return val;
    }

    public enum CurrencyTypes {
        RUB,
        EUR,
        USD
    }
}

