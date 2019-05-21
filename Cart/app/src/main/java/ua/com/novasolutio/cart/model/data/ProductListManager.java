package ua.com.novasolutio.cart.model.data;


import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Nullable;

import io.reactivex.Observable;

public class ProductListManager {
    public static final String TAG = "ProductListManager";
    protected final ArrayList<Product> mProducts = new ArrayList();
    private static ProductListManager instance;
    private ArrayList<DataChangeListener> mListeners = new ArrayList<>();

    private ProductListManager(){
    }

    public static ProductListManager getInstance() {
        if (instance == null) instance = new ProductListManager();
        return instance;
    }

    public boolean setProductById(Product product, int id){
        for (int i = 0; i < mProducts.size(); i++) {
            Product p = mProducts.get(i);
            if(p.getID() == id){
                mProducts.set(i, product);
                observeModelProductChange(product);
                return true;
            }
        }
        return false;
    }

    public boolean addProducts(Collection<Product> products){
        if (products != null) {
            mProducts.addAll(products);
            observeProductListChange();
            return true;
        }
        return false;
    }

    public boolean setProducts(Collection<Product> products){
        if(products != null){
            mProducts.clear();
            for (Product p: products) {
                mProducts.add(p);
            }

            Log.i(TAG, "Products: " + mProducts);
            observeProductListChange();
            return true;
        }
        return false;
    }

    public List<Product> getProductsList(){
        return mProducts;
    }

    public boolean addProduct(Product product){
        if(product != null) {
            Observable.fromIterable(mProducts)
                    .contains(product)
                    .subscribe((aBoolean, throwable) -> {
                        if (aBoolean) {
                            setProductById(product, product.getID());
                        } else {
                            mProducts.add(product);
                            observeModelAddProduct(product);
                            Log.i(TAG, "addProduct: " + product);
                        }
                    });
            return true;
        }

        return false;
    }

    @Nullable
    public Product getProductById(int id){
        for (Product p : mProducts){
            if(p.getID() == id) return p;
        }
        return null;
    }

    public Long getTotalPriceSelectedProducts(){
        long totalCost = 0L;

        //в циклі перебераються всі елементи та формується загальна ціна по вибраним елементам
        for (Product p : mProducts){
            int selectedCount = p.getCount();
            if(selectedCount > 0) totalCost = totalCost + (long) (p.getPrice() * selectedCount);
        }

        return totalCost;
    }

    public boolean removeProduct(Product product){
        boolean b = mProducts.remove(product);
        if (b) observeModelProductRemove(product);
        Log.i(TAG, "removeProduct: " + product + " " + b);
        return b;
    }

    public interface DataChangeListener{
        void onProductListChange();
        void onModelAddProduct(Product product);
        void onModelProductRemove(Product product);
        void onModelProductChange(Product product);
    }

    public void addDataChangeListener(DataChangeListener listener){
        if(listener != null) mListeners.add(listener);
    }

    public void removeDataChangeListener(DataChangeListener listener){
        if (listener != null) mListeners.remove(listener);
    }

    public void observeProductListChange(){
        for (DataChangeListener d: mListeners){
           if(d != null) {
               d.onProductListChange();
               Log.i(TAG, "observeProductListChange");
           }
        }
    }

    public void observeModelAddProduct(Product product){
        for (DataChangeListener d: mListeners){
            if(d != null) {
                d.onModelAddProduct(product);
                Log.i(TAG, "observeModelAddProduct");
            }
        }
    }

    public void observeModelProductRemove(Product product){
        for (DataChangeListener d: mListeners){
            if(d != null) {
                d.onModelProductRemove(product);
                Log.i(TAG, "observeModelProductRemove");
            }
        }
    }

    public void observeModelProductChange(Product product){
        for (DataChangeListener d: mListeners){
            if(d != null) {
                d.onModelProductChange(product);
                Log.i(TAG, "observeModelProductChange: ");
            }
        }
    }
}
