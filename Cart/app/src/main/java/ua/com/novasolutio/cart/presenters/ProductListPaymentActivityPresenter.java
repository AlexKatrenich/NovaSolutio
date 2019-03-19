package ua.com.novasolutio.cart.presenters;

import ua.com.novasolutio.cart.views.activities.ProductListPaymentActivity;
import ua.com.novasolutio.cart.views.fragments.CartFragment;
import ua.com.novasolutio.cart.views.fragments.ProductListFragment;

public class ProductListPaymentActivityPresenter extends BasePresenter<Void , ProductListPaymentActivity> {


    @Override
    protected void updateView() {

    }

    /* метод реагує на */
    public void onProductListFragmentClicked() {
        view().bindFragment(new ProductListFragment());
    }

    public void onCartFragmentClicked() {
        view().bindFragment(new CartFragment());
    }

}
