package ua.com.novasolutio.cart.presentation.presenter.product_list_main;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.v7.widget.SearchView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ua.com.novasolutio.cart.CartApplication;
import ua.com.novasolutio.cart.R;
import ua.com.novasolutio.cart.model.data.CurrencyManager;
import ua.com.novasolutio.cart.model.data.Product;
import ua.com.novasolutio.cart.model.data.ProductListManager;
import ua.com.novasolutio.cart.presentation.presenter.BasePresenter;
import ua.com.novasolutio.cart.presentation.view.ProductsListView;
import ua.com.novasolutio.cart.ui.fragment.ProductListFragment;

/* Презентер для роботи з активністю ProductListPaymentActivity*/
public class ProductListFragmentPresenter extends BasePresenter<List<Product>, ProductsListView> /*<Model, View>*/
        implements SearchView.OnQueryTextListener,
                    ProductListManager.DataChangeListener,
                    CurrencyManager.CurrencyChangeListener {

    /** флажок для відображення завантаження даних
     * @value true - виконується процес завантаження даних, в паралельному потоці
     * @value false - процес завантаження даних не виконується */
    private boolean isLoadingData = false;
    public static final String TAG = "ProdListFragPresenter";

    @Override
    protected void updateView() {
        ProductListFragment view = (ProductListFragment) view();
        if (view != null) {
            if (model.size() == 0) {
                view.showEmpty();
            } else {
                view.showProducts(model);
                Long totalCost = ProductListManager.getInstance().getTotalPriceSelectedProducts();
                view.setTotalProductsPrice(formatPriceOnText(totalCost));
            }
        }
    }

    @Override
    public void bindView(@NonNull ProductsListView view) {
        super.bindView(view);
        ProductListManager.getInstance().addDataChangeListener(this);
        // не потрібно повторно завантажувати дані, якщо вони вже завантажені
        List<Product> list = ProductListManager.getInstance().getProductsList();
        if(model == null && !isLoadingData && list.isEmpty()){
            loadData();
        } else {
            setModel(list);
        }

        CurrencyManager.getInstance().addCurrencyChangeListener(this);
    }

    @Override
    public void unbindView() {
        ProductListManager.getInstance().removeDataChangeListener(this);
        CurrencyManager.getInstance().removeCurrencyChangeListener(this);
        super.unbindView();
    }

    private void loadData() {
        isLoadingData = true;
        new LoadDataTask().execute();
    }

    @Override
    public void currencyNameChanged() {
        updateView();
    }

    // внутрішній клас для асинхронного завантаження даних з БД
    private class LoadDataTask extends AsyncTask<Void, Void, ArrayList>{
        @Override
        protected ArrayList doInBackground(Void... voids) {
            List<Product> list = CartApplication.getInstance().getDatabase().mProductDao().getAllActive();
            return new ArrayList<Product>(list);
        }

        @Override
        protected void onPostExecute(ArrayList list) {
            ProductListManager.getInstance().setProducts(list);
            isLoadingData = false; // зняття флажка про завантаження даних
        }
    }



    @Override
    public boolean onQueryTextSubmit(String s) {
        String userInput = s.toLowerCase();
        List<Product> newProductList = new ArrayList<>();

        for (Product product : model){
            if(product.getCaption().toLowerCase().contains(userInput)){
                newProductList.add(product);
            }
        }

        if (view() != null){
            view().showProducts(newProductList);
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (!setupDone()) return false;

        String userInput = newText.toLowerCase();
        List<Product> newProductList = new ArrayList<>();

        for (Product product : model){
            if(product.getCaption().toLowerCase().contains(userInput)){
                newProductList.add(product);
            }
        }
        if (view() != null){
            view().showProducts(newProductList);
        }
        return false;
    }

    // обробка логіки кліку по елементу розпізнавання голос
    public void onVoiceSearchClick() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);

        try {
            ((ProductListFragment)view()).startActivityForResult(intent, ProductListFragment.REQUEST_CODE_GET_VOICE_SPEECH);
        } catch (ActivityNotFoundException e){
            try {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,   Uri.parse("https://market.android.com/details?id=" + ((ProductListFragment)view()).getActivity().getPackageManager()));
                ((ProductListFragment)view()).startActivity(browserIntent);
            } catch (ActivityNotFoundException er){
                String message = ((ProductListFragment)view()).getResources().getString(R.string.speech_voice_search_unable_support);
                view().showMessage(message);
            }
        }

    }


    @Override
    public void onProductListChange() {
        setModel(ProductListManager.getInstance().getProductsList());
    }

    @Override
    public void onModelAddProduct(Product product) {
        if (setupDone()){
            ((ProductListFragment) view()).showProductAdd(product);
        }
    }

    @Override
    public void onModelProductRemove(Product product) {
        model.remove(product);
        ProductListFragment view = (ProductListFragment)view();
        if (view != null) {
            view.showProductRemove(product);
            Long totalCost = ProductListManager.getInstance().getTotalPriceSelectedProducts();
            view.setTotalProductsPrice(formatPriceOnText(totalCost));
        }
    }

    @Override
    public void onModelProductChange(Product product) {
        if(setupDone()){
            Long totalPrice = ProductListManager.getInstance().getTotalPriceSelectedProducts();
            ((ProductListFragment)view()).setTotalProductsPrice(formatPriceOnText(totalPrice));
        }
    }

}
