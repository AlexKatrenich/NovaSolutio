package ua.com.novasolutio.cart.presenters;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import ua.com.novasolutio.cart.views.activities.AddChangeProductActivity;
import ua.com.novasolutio.cart.views.activities.ProductListPaymentActivity;
import ua.com.novasolutio.cart.views.fragments.CartFragment;
import ua.com.novasolutio.cart.views.fragments.ProductListFragment;

public class ProductListPaymentActivityPresenter extends BasePresenter<Void , ProductListPaymentActivity> {
    public static final String FLAG_SAVE_INSTANCE_PRESENTER = "SAVE_INSTANCE_ProductListPaymentActivityPresenter";

    private static final String TAG = "ProdListPayActivPres";

    @Override
    protected void updateView() {
        Log.i(TAG, "updateView: UPDATE ProductListPaymentActivity");
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
