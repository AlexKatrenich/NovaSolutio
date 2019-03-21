package ua.com.novasolutio.cart.presenters;


import android.util.Log;
import android.view.View;

import ua.com.novasolutio.cart.R;
import ua.com.novasolutio.cart.data.Product;
import ua.com.novasolutio.cart.views.ProductView;
import ua.com.novasolutio.cart.views.ProductViewHolder;


public class ProductItemPresenter extends BasePresenter<Product, ProductViewHolder>{
    public static final String TAG = "ProductItemPresenter";

    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 99;


    @Override
    protected void updateView() {
        view().setProductCaption(model.getCaption());
        int price = model.getPrice();
        String formatedPrice = formatPriceForView(price);
        view().setProductPrice(formatedPrice);
        ((ProductViewHolder)view()).setCounterProduct(model.getCount());
    }

    public void onLeftSwipeMinusProduct(){
        if(setupDone() && model.getCount() > MIN_VALUE){
            model.setCount(model.getCount() - 1);
            updateView();
        }
    }

    public void onRightSwipePlusProduct(){
        if(setupDone() && model.getCount() < MAX_VALUE){
            model.setCount(model.getCount() + 1);
            updateView();
        }
    }

    public void onContextMenuClicked(View v) {
        Log.i(TAG, "onContextMenuClicked: ");
        view().showPopupMenu(v);
    }

    /* метод форматує візуальне представлення ціни для View*/
    public String formatPriceForView(int price) {
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
}
