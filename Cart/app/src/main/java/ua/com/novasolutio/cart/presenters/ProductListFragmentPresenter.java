package ua.com.novasolutio.cart.presenters;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.util.ArrayMap;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ua.com.novasolutio.cart.data.Product;
import ua.com.novasolutio.cart.mock.MockDB;
import ua.com.novasolutio.cart.views.ProductsListView;
import ua.com.novasolutio.cart.views.fragments.ProductListFragment;

import static android.support.constraint.Constraints.TAG;


/* Презентер для роботи з активністю ProductListPaymentActivity*/
public class ProductListFragmentPresenter extends BasePresenter<List<Product>, ProductsListView> implements MockDB.OnDataChangedListener/*<Model, View>*/{
    /** флажок для відображення завантаження даних
     * @value true - виконується процес завантаження даних, в паралельному потоці
     * @value false - процес завантаження даних не виконується */
    private boolean isLoadingData = false;

    @Override
    protected void updateView() {
        if (model.size() == 0){
            view().showEmpty();
            Log.i(TAG, "updateView: view().showEmpty();");
        } else {
            view().showProducts(model);
            Log.i(TAG, "updateView: view().showProducts(model)");
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

    private class LoadDataTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            SystemClock.sleep(1000); //емуляція завантаження з БД
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            MockDB mDb = MockDB.getInstance();
            ArrayMap<Integer, Product> map = mDb.getProductMap(); // завантаження мапи об'єктів із заглушки
            setModel(new ArrayList<Product>(map.values())); // передача списку об'єктів Product в модель(передача посилання на список)
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
        ((ProductListFragment)view()).showProductRemove(product);
    }

    @Override
    public void onDbProductChange() {

    }

}
