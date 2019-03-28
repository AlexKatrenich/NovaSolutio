package ua.com.novasolutio.cart.data;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Nullable;

public class ProductListManager {
    public static final String TAG = "ProductListManager";
    protected final ArrayList<Product> mProducts = new ArrayList();
    public static ProductListManager instance;


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
                return true;
            }
        }
        return false;
    }

    public boolean addProducts(Collection<Product> products){
        if (products != null) {
            mProducts.addAll(products);
            return true;
        }
        return false;
    }

    public List<Product> getProductsList(){
        return mProducts;
    }

    public boolean addProduct(Product product){
        if(product != null) {
            mProducts.add(product);
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

    public Long getTotalPriceSelectedproducts(){
        long totalCost = 0L;

        //в циклі перебераються всі елементи та формується загальна ціна по вибраним елементам
        for (Product p : mProducts){
            int selectedCount = p.getCount();
            if(selectedCount > 0) totalCost = totalCost + (long) (p.getPrice() * selectedCount);
        }

        return totalCost;
    }
}
