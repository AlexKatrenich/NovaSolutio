package ua.com.novasolutio.cart.presenters;

import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<M, V>{
    protected M model;
    private WeakReference<V> view;
    protected int position;

    public void setPosition(int pos){
        position = pos;
    }

    /* отримання та фіксація посилання на модель(об'єкт, список об'єктів), яку повинна відображати View*/
    public void setModel(M model) {
        this.model = model;
        if (setupDone()){
            updateView();
        }
    }

    /* метод для фіксації полсилання(WeakReference) на View, перевірка актуальності View/Model та перевідображення даних на екрані*/
    public void bindView(@NonNull V view){
        this.view = new WeakReference<>(view);
        if(setupDone()){
            updateView();
        }
    }

    /* метод для відкріплення посилання на View*/
    public void unbindView(){
        this.view = null;
    }

    /* метод для отримання посилання на View якщо вона не знищена GarbageCollector*/
    protected V view() {
        if (view == null) {
            return null;
        } else {
            return view.get();
        }
    }

    /* метод форматує візуальне представлення ціни для View*/
    public String formatPriceOnText(int price) {
        StringBuffer priceString = new StringBuffer(String.valueOf(price));
        switch (priceString.length()){
            case 0 :
                priceString.append("0,00");
                break;
            case 1:
                priceString.insert(0, "0,0");
                break;
            case 2:
                priceString.insert(0, "0,");
                break;
            default:
                priceString.insert(priceString.length() - 2, ',');
        }

        // додавання назви грошових одиниць до відображення ціни на екрані
        //TODO додати можливість задавати валюту через екран, зберігати через SharedPreference.
        String currency = "UAH";
        priceString.append(' ').append(currency).append(' ');

        return priceString.toString();
    }

    /* метод для перевірки актуальності посилань на View(Екран) та модель*/
    protected boolean setupDone() {
        return view() != null && model != null ;
    }

    /* бізнес-логіка в презентері, наприклад виклик методів для "пере-відображення" списку об'єктів на View*/
    protected abstract void updateView();

}
