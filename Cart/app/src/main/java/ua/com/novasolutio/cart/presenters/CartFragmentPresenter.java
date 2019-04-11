package ua.com.novasolutio.cart.presenters;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import ua.com.novasolutio.cart.data.Product;
import ua.com.novasolutio.cart.data.ProductListManager;
import ua.com.novasolutio.cart.views.activities.ProductListPaymentActivity;
import ua.com.novasolutio.cart.views.fragments.CartFragment;

public class CartFragmentPresenter extends BasePresenter<List<Product>, CartFragment>
        implements ProductListManager.DataChangeListener,
                        LifecycleObserver,
                        ProductListPaymentActivityPresenter.ChangeSortingStateListener {
    public static final String TAG = "CartFragmentPresenter";

    // статус сортування по замовчуванню
    private ProductListPaymentActivityPresenter.SortingState mSortingState = ProductListPaymentActivityPresenter.SortingState.CAPTION_ASCENDING;

    @Override
    protected void updateView() {
        CartFragment view = view();
        if(view != null){
            if (model.size() == 0){
                view.showEmpty();
                Log.i(TAG, "updateView: MODEL EMPTY");
            } else {
                view.showProducts(model);
                Log.i(TAG, "updateView: SHOW PRODUCTS" + model);
            }
            Long totalPrice = ProductListManager.getInstance().getTotalPriceSelectedProducts();
            view.setTotalPrice(formatPriceOnText(totalPrice));
        }
    }

    @Override
    public void bindView(@NonNull CartFragment view) {
        super.bindView(view);
        ProductListManager.getInstance().addDataChangeListener(this);
        // підписка на зміну статусу сортування по натисненні на кнопку сортування
        if(view().getActivity() != null) {
            ((ProductListPaymentActivity)view().getActivity()).getPresenter().setChangeSortingStateListener(this);
            mSortingState = ((ProductListPaymentActivity)view().getActivity()).getPresenter().getSortingState();
        }
    }

    @Override
    public void unbindView() {
        // видалення підписки на статус
        if(view().getActivity() != null) ((ProductListPaymentActivity)view().getActivity()).getPresenter().setChangeSortingStateListener(null);
        super.unbindView();
        ProductListManager.getInstance().removeDataChangeListener(this);
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
        view().showItemRemove(product); // Відображення на екрані події видалення елементу
        view().setTotalPrice(formatPriceOnText(ProductListManager
                .getInstance()
                .getTotalPriceSelectedProducts())); // перерахунок ціни
    }

    @Override
    public void onModelProductChange(Product product) {
        Log.i(TAG, "onModelProductChange:");
        if(product.getCount() == 0) onModelProductRemove(product);
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
        sortingStateChanged(mSortingState);
    }

    @Override
    public void sortingStateChanged(ProductListPaymentActivityPresenter.SortingState state) {
        ArrayList<Product> list = new ArrayList<>(ProductListManager.getInstance().getProductsList());
        Log.i(TAG, "onSortItemClicked: LIST: " + list);
        switch (state){

            case CAPTION_ASCENDING:
                Collections.sort(list, new Comparator<Product>() {
                    @Override
                    public int compare(Product o1, Product o2) {
                        String s1 = o1.getCaption().toLowerCase();
                        String s2 = o2.getCaption().toLowerCase();
                        return s1.compareTo(s2);
                    }
                });
                break;

            case CAPTION_DESCENDING:
                Collections.sort(list, new Comparator<Product>() {
                    @Override
                    public int compare(Product o1, Product o2) {
                        String s1 = o1.getCaption().toLowerCase();
                        String s2 = o2.getCaption().toLowerCase();
                        return s2.compareTo(s1);
                    }
                });
                break;

            case PRICE_ASCENDING:
                Collections.sort(list, new Comparator<Product>() {
                    @Override
                    public int compare(Product o1, Product o2) {
                        Integer i1 = o1.getPrice();
                        Integer i2 = o2.getPrice();
                        return i1.compareTo(i2);
                    }
                });
                break;

            case PRICE_DESCENDING:
                Collections.sort(list, new Comparator<Product>() {
                    @Override
                    public int compare(Product o1, Product o2) {
                        Integer i1 = o1.getPrice();
                        Integer i2 = o2.getPrice();
                        return i2.compareTo(i1);
                    }
                });
                break;
        }

        mSortingState = state; //змінюємо поточне значення сортування
        setModel(list);
    }
}
