package ua.com.novasolutio.cart.presenters;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.widget.SearchView;
import android.util.ArrayMap;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ua.com.novasolutio.cart.data.Product;
import ua.com.novasolutio.cart.mock.MockDB;
import ua.com.novasolutio.cart.views.ProductsListView;
import ua.com.novasolutio.cart.views.fragments.ProductListFragment;

/* Презентер для роботи з активністю ProductListPaymentActivity*/
public class ProductListFragmentPresenter extends BasePresenter<List<Product>, ProductsListView> /*<Model, View>*/
        implements MockDB.OnDataChangedListener, SearchView.OnQueryTextListener {
    /** флажок для відображення завантаження даних
     * @value true - виконується процес завантаження даних, в паралельному потоці
     * @value false - процес завантаження даних не виконується */
    private boolean isLoadingData = false;
    public static final String TAG = "ProdListFragPresenter";

    @Override
    protected void updateView() {
        ProductsListView view = view();
        if (view != null) {
            if (model.size() == 0) {
                view.showEmpty();
                Log.i(TAG, "updateView: view().showEmpty();");
            } else {
                Log.i(TAG, "updateView: view == null " + String.valueOf(view == null));
                view.showProducts(model);
                Long totalCost = MockDB.getInstance().getTotalPriceSelectedProducts();
                ((ProductListFragment)view).setTotalProductsPrice(formatPriceOnText(totalCost));
                Log.i(TAG, "updateView: view().showProducts(model)");
            }
        }
    }

    @Override
    public void bindView(@NonNull ProductsListView view) {
        super.bindView(view);
        // не потрібно повторно завантажувати дані, якщо вони вже завантажені
        if(model == null && !isLoadingData){
            loadData();
        }
    }

    private void loadData() {
        isLoadingData = true;
        new LoadDataTask().execute();
    }

    /** метод для зберігання та відображення на екрані інформації про додавання додаткової кількості продуктів одного виду
    * наприклад користувач декілька разів натискає на додавання кави, на екрані відображається 1,2 .... N кількість чашок кави в списку продуктів*/
//    public void onAddProductClicked()

    private class LoadDataTask extends AsyncTask<Void, Void, ArrayList>{
        @Override
        protected ArrayList doInBackground(Void... voids) {
            SystemClock.sleep(1000); //емуляція завантаження з БД
            MockDB mDb = MockDB.getInstance();
            ArrayMap<Integer, Product> map = mDb.getProductMap();
            return new ArrayList<Product>(map.values());
        }

        @Override
        protected void onPostExecute(ArrayList list) {
             // завантаження мапи об'єктів із заглушки
            setModel(list); // передача списку об'єктів Product в модель(передача посилання на список)
            Log.i(TAG, "onPostExecute: DATA LOAD, VIEW UPDATE" + list);
            updateView();
            isLoadingData = false; // зняття флажка про завантаження даних
        }
    }



    @Override
    public void productListWasChanged() {
        loadData();
    }

    @Override
    public void onDbProductAdd(Product product) {
        ((ProductListFragment) view()).showProductAdd(product);
    }

    @Override
    public void onDbProductRemove(Product product) {
        model.remove(product);
        ProductListFragment view = (ProductListFragment)view();
        if (view != null) {
            view.showProductRemove(product);
            Long totalCost = MockDB.getInstance().getTotalPriceSelectedProducts();
            view.setTotalProductsPrice(formatPriceOnText(totalCost));
        }

    }

    @Override
    public void onDbProductChange() {
        if(setupDone()){
            Long totalPrice = MockDB.getInstance().getTotalPriceSelectedProducts();
            ((ProductListFragment)view()).setTotalProductsPrice(formatPriceOnText(totalPrice));
        }
    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String userInput = newText.toLowerCase();
        List<Product> newProductList = new ArrayList<>();

        for (Product product : model){
            if(product.getCaption().toLowerCase().contains(userInput)){
                newProductList.add(product);
            }
        }

        view().showProducts(newProductList);
        return false;
    }

}
