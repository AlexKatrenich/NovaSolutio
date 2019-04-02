package ua.com.novasolutio.cart.presenters;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ua.com.novasolutio.cart.data.Product;
import ua.com.novasolutio.cart.data.ProductListManager;
import ua.com.novasolutio.cart.views.fragments.CartFragment;

public class CartFragmentPresenter extends BasePresenter<List<Product>, CartFragment> implements ProductListManager.DataChangeListener, LifecycleObserver {
    public static final String TAG = "CartFragmentPresenter";

    @Override
    protected void updateView() {
        CartFragment view = (CartFragment) view();
        if(view != null){
            if (model.size() == 0){
                view.showEmpty();
                Log.i(TAG, "updateView: MODEL EMPTY");
            } else {
                view.showProducts(model);
                Long totalPrice = ProductListManager.getInstance().getTotalPriceSelectedProducts();
                view.setTotalPrice(formatPriceOnText(totalPrice));
                Log.i(TAG, "updateView: SHOW PRODUCTS" + model);
            }
        }
    }

    @Override
    public void bindView(@NonNull CartFragment view) {
        super.bindView(view);
    }

    @Override
    public void onProductListChange() {
        Log.i(TAG, "onProductListChange: ON PRODUCT LIST CHANGE");
        this.setModel(ProductListManager.getInstance().getProductsList());
    }

    @Override
    public void onModelAddProduct(Product product) {
        Log.i(TAG, "onModelAddProduct: " + product);
        this.setModel(ProductListManager.getInstance().getProductsList());
    }

    @Override
    public void onModelProductRemove(Product product) {
        this.setModel(ProductListManager.getInstance().getProductsList());
    }

    @Override
    public void onModelProductChange(Product product) {
        Log.i(TAG, "onModelProductChange:");
        this.setModel(ProductListManager.getInstance().getProductsList());
    }

    @Override
    public void setModel(List<Product> model) {
        ArrayList<Product> list = new ArrayList<>(model);
        for (Iterator<Product> iter = list.iterator(); iter.hasNext(); ){
            Product p = iter.next();
            if(p.getCount() < 1){
                iter.remove();
            }
        }

        Log.i(TAG, "setModel: " + list);
        super.setModel(list);
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void loadModel(){
        Log.i(TAG, "loadModel: ");
        this.setModel(ProductListManager.getInstance().getProductsList());
    }
}
