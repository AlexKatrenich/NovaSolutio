package ua.com.novasolutio.cart.presenters;

import android.content.Intent;

import ua.com.novasolutio.cart.views.activities.AddChangeProductActivity;
import ua.com.novasolutio.cart.views.activities.ProductListPaymentActivity;
import ua.com.novasolutio.cart.views.fragments.CartFragment;
import ua.com.novasolutio.cart.views.fragments.ProductListFragment;

public class ProductListPaymentActivityPresenter extends BasePresenter<Void , ProductListPaymentActivity> {


    @Override
    protected void updateView() {

    }


    public void onProductListFragmentClicked() {
        view().bindFragment(new ProductListFragment());
    }

    public void onCartFragmentClicked() {
        view().bindFragment(new CartFragment());
    }

    public void addNewProductMenuClicked() {
        Intent intent = new Intent(view().getBaseContext(), AddChangeProductActivity.class);
        view().startActivity(intent);
    }
}
