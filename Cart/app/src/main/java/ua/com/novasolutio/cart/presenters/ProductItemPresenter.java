package ua.com.novasolutio.cart.presenters;


import android.util.Log;
import android.view.View;

import ua.com.novasolutio.cart.data.Product;
import ua.com.novasolutio.cart.views.ProductViewHolder;


public class ProductItemPresenter extends BasePresenter<Product, ProductViewHolder>{
    public static final String TAG = "ProductItemPresenter";

    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 99;


    @Override
    protected void updateView() {
        view().setProductCaption(model.getCaption());
        int price = model.getPrice();
        String formattedPrice = formatPriceOnText(price);
        view().setProductPrice(formattedPrice);
        view().setCounterProduct(model.getCount());
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

}
