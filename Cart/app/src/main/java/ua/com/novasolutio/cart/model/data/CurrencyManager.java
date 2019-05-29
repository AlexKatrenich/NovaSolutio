package ua.com.novasolutio.cart.model.data;

import java.util.ArrayList;
import java.util.List;

public class CurrencyManager {
    private static final CurrencyManager ourInstance = new CurrencyManager();
    private List<CurrencyChangeListener> mListeners = new ArrayList<>();

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
        notifyListeners();
    }


    public interface CurrencyChangeListener{
        void currencyNameChanged();
    }

    public void addCurrencyChangeListener(CurrencyChangeListener listener) {
        mListeners.add(listener);
    }

    public void removeCurrencyChangeListener(CurrencyChangeListener listener){
        mListeners.remove(listener);
    }

    private void notifyListeners(){
        for (CurrencyChangeListener listener : mListeners) {
            if(listener != null) listener.currencyNameChanged();
        }
    }
}
