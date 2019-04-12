package ua.com.novasolutio.cart.presenters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
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
        if(ProductListManager.getInstance().getTotalPriceSelectedProducts() > 0) {
            view().changeSizePaymentButton(true);
        } else {
            view().changeSizePaymentButton(false);
        }
    }

    public void onCartFragmentClicked() {
        Log.i(TAG, "onCartFragmentClicked: VIEW = " + String.valueOf(view() != null));
        view().bindFragment(new CartFragment());
        view().invalidateOptionsMenu();

        if(ProductListManager.getInstance().getTotalPriceSelectedProducts() > 0) {
            view().changeSizePaymentButton(true);
        } else {
            view().changeSizePaymentButton(false);
        }
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

    public void onPaymentClicked() {
        onCartFragmentClicked();
        view().showPaymentDialog();
        Log.i(TAG, "onPaymentClicked: ");
    }

    /* Опрацювання статусу сортування списку (кнопка сортування на тулбарі)*/

    // інтерфейс для прослуховувача зміни статусу
    public interface ChangeSortingStateListener {
        void sortingStateChanged(SortingState state);
    }

    // конкретний об'єкт слухача зміни статусу сортування
    private ChangeSortingStateListener mChangeSortingStateListener;

    public void setChangeSortingStateListener(ChangeSortingStateListener changeSortingStateListener) {
        mChangeSortingStateListener = changeSortingStateListener;
    }

    // перелік статусів сортування
    public enum SortingState {
        CAPTION_ASCENDING,
        CAPTION_DESCENDING,
        PRICE_ASCENDING,
        PRICE_DESCENDING
    }

    private SortingState mSortingState = SortingState.CAPTION_ASCENDING; // змінна для зберігання статусу сортування списку продуктів

    public SortingState getSortingState() {
        return mSortingState;
    }

    // опрацювання логіки при натисненні на відповідну кнопку сортування
    public void onSortItemClicked(SortingState sortingState) {
        ArrayList<Product> list = new ArrayList<>(ProductListManager.getInstance().getProductsList());
        Log.i(TAG, "onSortItemClicked: LIST: " + list);
        switch (sortingState){

            case CAPTION_ASCENDING:
                view().changeSortIcon(true);
                break;

            case CAPTION_DESCENDING:
                view().changeSortIcon(false);
                break;

            case PRICE_ASCENDING:
                view().changeSortIcon(true);
                break;

            case PRICE_DESCENDING:
                view().changeSortIcon(false);
                break;
        }

        mSortingState = sortingState;
        if (mChangeSortingStateListener != null) mChangeSortingStateListener.sortingStateChanged(sortingState);
    }

}
