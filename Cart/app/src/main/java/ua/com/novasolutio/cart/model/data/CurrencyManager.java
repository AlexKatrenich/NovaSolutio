package ua.com.novasolutio.cart.model.data;

public class CurrencyManager {
    private static final CurrencyManager ourInstance = new CurrencyManager();

    private String currencyName;

    public static CurrencyManager getInstance() {
        return ourInstance;
    }

    private CurrencyManager() {

    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }
}
