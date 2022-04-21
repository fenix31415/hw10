package shop;

import shop.controller.HttpException;
import shop.model.Cost;

import java.util.List;
import java.util.Map;

public class Utils {
    public static String getArgument(final Map<String, List<String>> parameters, final String name) throws HttpException {
        final List<String> list = parameters.get(name);
        //assert (list && list.size() == 1);
        if (list == null || list.size() < 1) {
            throw new HttpException(400, "Parameter " + name + " not found.");
        }
        return list.get(0);
    }

    public static Cost.CurrencyTypes getCurrencyParameter(final Map<String, List<String>> parameters) throws HttpException {
        return Cost.CurrencyTypes.valueOf(getArgument(parameters, "currency"));
    }

    public static Double getCostParameter(final Map<String, List<String>> parameters) throws HttpException {
        try {
            return Double.parseDouble(getArgument(parameters, "cost"));
        } catch (final NumberFormatException e) {
            throw new HttpException(400, "Invalid cost.");
        }
    }
}
