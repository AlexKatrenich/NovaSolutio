package ua.com.novasolutio.cart.presenters;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import ua.com.novasolutio.cart.data.Product;
import ua.com.novasolutio.cart.data.ProductListManager;
import ua.com.novasolutio.cart.views.fragments.CartFragment;

public class CartFragmentPresenter extends BasePresenter<List<Product>, CartFragment> implements ProductListManager.DataChangeListener{
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

        List<Product> list = ProductListManager.getInstance().getProductsList();
        for (int i = 0; i < list.size(); i++) {
            Product product = list.get(i);
            if (product.getCount() < 1){
                list.remove(product);
            }
        }
        setModel(list);
    }

    @Override
    public void onProductListChange() {
        Log.i(TAG, "onProductListChange: ON PRODUCT LIST CHANGE");
        setModel(ProductListManager.getInstance().getProductsList());
    }

    @Override
    public void onModelAddProduct(Product product) {
        Log.i(TAG, "onModelAddProduct: " + product);
    }

    @Override
    public void onModelProductRemove(Product product) {
        model.remove(product);
        CartFragment view = (CartFragment) view();
        if(view != null){
            view.showItemRemove(product);
        }

        long totalPrice = ProductListManager.getInstance().getTotalPriceSelectedProducts();
        view.setTotalPrice(formatPriceOnText(totalPrice));
    }

    @Override
    public void onModelProductChange(Product product) {
        Log.i(TAG, "onModelProductChange:");
    }
}
