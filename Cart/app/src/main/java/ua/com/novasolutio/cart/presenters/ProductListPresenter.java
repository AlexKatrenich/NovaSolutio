package ua.com.novasolutio.cart.presenters;

import ua.com.novasolutio.cart.activities.ProductListPaymentActivity;


/* Презентер для роботи з активністю ProductListPaymentActivity*/

public class ProductListPresenter {
    private ProductListPaymentActivity mView;
//    private final ProductListModel mModel;


    public ProductListPresenter() {

    }

    public void attachView(ProductListPaymentActivity productListPaymentActivity){
        mView = productListPaymentActivity;
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
