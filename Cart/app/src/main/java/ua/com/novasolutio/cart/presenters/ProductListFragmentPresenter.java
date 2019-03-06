package ua.com.novasolutio.cart.presenters;

import java.util.List;

import ua.com.novasolutio.cart.data.Product;
import ua.com.novasolutio.cart.views.ProductsView;
import ua.com.novasolutio.cart.views.fragments.ProductListFragment;


/* Презентер для роботи з активністю ProductListPaymentActivity*/
public class ProductListFragmentPresenter extends BasePresenter<List<Product>, ProductsView>{
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


    @Override
    protected void updateView() {
        if (model.size() == 0){
            view().showEmpty();
        } else {
            view().showProducts(model);
        }
    }
}
