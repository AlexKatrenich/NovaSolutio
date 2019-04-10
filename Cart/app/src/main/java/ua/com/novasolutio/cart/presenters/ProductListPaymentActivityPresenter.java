package ua.com.novasolutio.cart.presenters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ua.com.novasolutio.cart.data.Product;
import ua.com.novasolutio.cart.data.ProductListManager;
import ua.com.novasolutio.cart.views.activities.AddChangeProductActivity;
import ua.com.novasolutio.cart.views.activities.ProductListPaymentActivity;
import ua.com.novasolutio.cart.views.fragments.CartFragment;
import ua.com.novasolutio.cart.views.fragments.ProductListFragment;

public class ProductListPaymentActivityPresenter extends BasePresenter<Void , ProductListPaymentActivity> implements ProductListManager.DataChangeListener {

    private static final String TAG = "ProdListPayActivPres";
    private SortingState currentSortingState = SortingState.CAPTION_ASCENDING;

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

    public enum SortingState {
        CAPTION_ASCENDING,
        CAPTION_DESCENDING,
        PRICE_ASCENDING,
        PRICE_DESCENDING
    }

    // метод сортування списку продуктів в залежності від обраного виду сортування
    public void onSortItemClicked(SortingState sortingState) {
        ArrayList<Product> list = new ArrayList<>(ProductListManager.getInstance().getProductsList());
        Log.i(TAG, "onSortItemClicked: LIST: " + list);
        switch (sortingState){

            case CAPTION_ASCENDING:
                Collections.sort(list, new Comparator<Product>() {
                    @Override
                    public int compare(Product o1, Product o2) {
                        String s1 = o1.getCaption().toLowerCase();
                        String s2 = o2.getCaption().toLowerCase();
                        return s1.compareTo(s2);
                    }
                });
                view().changeSortIcon(true);
                break;

            case CAPTION_DESCENDING:
                Collections.sort(list, new Comparator<Product>() {
                    @Override
                    public int compare(Product o1, Product o2) {
                        String s1 = o1.getCaption().toLowerCase();
                        String s2 = o2.getCaption().toLowerCase();
                        return s2.compareTo(s1);
                    }
                });
                view().changeSortIcon(false);
                break;

            case PRICE_ASCENDING:
                Collections.sort(list, new Comparator<Product>() {
                    @Override
                    public int compare(Product o1, Product o2) {
                        Integer i1 = o1.getPrice();
                        Integer i2 = o2.getPrice();
                        return i1.compareTo(i2);
                    }
                });
                view().changeSortIcon(true);
                break;

            case PRICE_DESCENDING:
                Collections.sort(list, new Comparator<Product>() {
                    @Override
                    public int compare(Product o1, Product o2) {
                        Integer i1 = o1.getPrice();
                        Integer i2 = o2.getPrice();
                        return i2.compareTo(i1);
                    }
                });
                view().changeSortIcon(false);
                break;
        }
        Log.i(TAG, "onSortItemClicked: LIST AFTER SORT: " + list);
        ProductListManager.getInstance().setProducts(list);
    }
}
