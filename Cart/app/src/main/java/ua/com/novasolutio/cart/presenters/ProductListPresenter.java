package ua.com.novasolutio.cart.presenters;

import ua.com.novasolutio.cart.activities.ProductListActivity;


/* Презентер для роботи з активністю ProductListActivity*/

public class ProductListPresenter {
    private ProductListActivity mView;
//    private final ProductListModel mModel;


    public ProductListPresenter() {

    }

    public void attachView(ProductListActivity productListActivity){
        mView = productListActivity;
    }

    public void detachView(){
        mView = null;
    }

    public void viewIsReady(){
        loadProducts();
    }

    public void loadProducts() {

    }




}
