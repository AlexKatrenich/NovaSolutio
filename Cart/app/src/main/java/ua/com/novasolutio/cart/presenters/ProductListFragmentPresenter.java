package ua.com.novasolutio.cart.presenters;

import ua.com.novasolutio.cart.activities.ProductListPaymentActivity;
import ua.com.novasolutio.cart.fragments.ProductListFragment;


/* Презентер для роботи з активністю ProductListPaymentActivity*/
public class ProductListFragmentPresenter {
    private ProductListFragment mView;
//    private final ProductListModel mModel;


    public ProductListFragmentPresenter() {

    }

    public void attachView(ProductListFragment productListFragment){
        mView = productListFragment;
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
