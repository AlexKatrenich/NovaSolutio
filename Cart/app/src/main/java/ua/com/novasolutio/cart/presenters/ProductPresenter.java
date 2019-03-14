package ua.com.novasolutio.cart.presenters;


import android.util.Log;
import android.view.View;

import ua.com.novasolutio.cart.data.Product;
import ua.com.novasolutio.cart.views.ProductView;
import ua.com.novasolutio.cart.views.ProductViewHolder;


public class ProductPresenter extends BasePresenter<Product, ProductView>{
    public static final String TAG = "ProductPresenter";

    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 99;


    @Override
    protected void updateView() {
        view().setProductCaption(model.getCaption());
        int price = model.getPrice();
        view().setProductPrice(price);
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
        //TODO реалізація відображення контектсного меню в recyclerView
        Log.i(TAG, "onContextMenuClicked: ");
        ProductViewHolder viewHolder = (ProductViewHolder) view();
        viewHolder.showPopupMenu(v);
    }

}
