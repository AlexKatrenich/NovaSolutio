package ua.com.novasolutio.cart.presenters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import ua.com.novasolutio.cart.data.Product;
import ua.com.novasolutio.cart.data.ProductListManager;
import ua.com.novasolutio.cart.views.activities.AddChangeProductActivity;
import ua.com.novasolutio.cart.views.activities.ProductListPaymentActivity;
import ua.com.novasolutio.cart.views.fragments.CartFragment;
import ua.com.novasolutio.cart.views.fragments.ProductListFragment;

public class ProductListPaymentActivityPresenter extends BasePresenter<Void , ProductListPaymentActivity> implements ProductListManager.DataChangeListener {

    private static final String TAG = "ProdListPayActivPres";

    @Override
    protected void updateView() {
        Log.i(TAG, "updateView: UPDATE ProductListPaymentActivity");
    }

    @Override
    public void bindView(@NonNull ProductListPaymentActivity view) {
        super.bindView(view);
        ProductListManager.getInstance().addDataChangeListener(this);
    }


    @Override
    public void unbindView() {
        super.unbindView();
        ProductListManager.getInstance().removeDataChangeListener(this);
    }

    public void onProductListFragmentClicked() {
        Log.i(TAG, "onProductListFragmentClicked: VIEW = " + String.valueOf(view() != null));
        view().bindFragment(new ProductListFragment());
        view().invalidateOptionsMenu();
        view().changeSizePaymentButton(false);
    }

    public void onCartFragmentClicked() {
        Log.i(TAG, "onCartFragmentClicked: VIEW = " + String.valueOf(view() != null));
        view().bindFragment(new CartFragment());
        view().invalidateOptionsMenu();
        view().changeSizePaymentButton(true);
    }

    public void addNewProductMenuClicked() {
        Intent intent = new Intent(view().getBaseContext(), AddChangeProductActivity.class);
        view().startActivity(intent);
    }

    @Override
    public void onProductListChange() {
        // реагування на зміну даних в корзині - зміна вигляду кнопки для відкриття оплати
        view().changeSizePaymentButton(cartHaveAnyGoods());
        Log.i(TAG, "onProductListChange: ");
    }

    @Override
    public void onModelAddProduct(Product product) {
        // NOTHING TODO
    }

    @Override
    public void onModelProductRemove(Product product) {
        // реагування на зміну даних в корзині - зміна вигляду кнопки для відкриття оплати
        view().changeSizePaymentButton(cartHaveAnyGoods());
        Log.i(TAG, "onModelProductRemove: ");
    }

    @Override
    public void onModelProductChange(Product product) {
        // реагування на зміну даних в корзині - зміна вигляду кнопки для відкриття оплати
        view().changeSizePaymentButton(cartHaveAnyGoods());
        Log.i(TAG, "onModelProductChange: ");
    }

    // метод для перевірки наявності продуктів в корзині
    private boolean cartHaveAnyGoods(){
        List<Product> list = ProductListManager.getInstance().getProductsList();

        for (Product p : list) {
            if(p.getCount() > 0) return true;
        }

        return false;
    }
}
