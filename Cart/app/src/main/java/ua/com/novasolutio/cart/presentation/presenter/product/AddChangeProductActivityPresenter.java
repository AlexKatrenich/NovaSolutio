package ua.com.novasolutio.cart.presentation.presenter.product;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;


import java.util.Observable;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ua.com.novasolutio.cart.CartApplication;
import ua.com.novasolutio.cart.model.dao.CartDatabase;
import ua.com.novasolutio.cart.model.data.Product;
import ua.com.novasolutio.cart.model.data.ProductListManager;
import ua.com.novasolutio.cart.presentation.presenter.BasePresenter;
import ua.com.novasolutio.cart.presentation.view.ProductView;
import ua.com.novasolutio.cart.ui.activity.product.AddChangeProductActivity;

public class AddChangeProductActivityPresenter extends BasePresenter<Product, ProductView> {
    public static final String TAG = "AddChProdActivPresent";
    private boolean isLoadingData = false;

    @Override
    protected void updateView() {
        if(model != null){
            view().setProductCaption(model.getCaption());
            if(model.getPrice() != 0) {
                view().setProductPrice(formatPriceOnText((long) model.getPrice()));
            }
        }
    }

    @Override
    public void bindView(@NonNull ProductView view) {
        super.bindView(view);

        // зчитування Intent та відображення даних в залежності від того чи було щось передано чере Інтент
        Intent intent = ((AddChangeProductActivity)view).getIntent();
        int productId = intent.getIntExtra(AddChangeProductActivity.INTENT_CODE_FOR_GETTING_MODEL, -1);
        loadModel(productId);

        // не потрібно повторно завантажувати дані повторно, якщо вони вже завантажені або в процесі завантаження
        if(model == null && !isLoadingData){
            loadModel(productId);
        }
    }

    @Override
    public String formatPriceOnText(long price) {
        StringBuffer priceString = new StringBuffer(String.valueOf(price));
        switch (priceString.length()){
            case 0 :
                priceString.append("0.00");
                break;
            case 1:
                priceString.insert(0, "0.0");
                break;
            case 2:
                priceString.insert(0, "0.");
                break;
            default:
                priceString.insert(priceString.length() - 2, '.');
        }

        return priceString.toString();
    }

    public void loadModel(int productId) {
        isLoadingData = true;
        Product product;
        if(productId == -1){
            product = new Product();
            product.setCaption("");
            product.setPrice(0);
            product.setDeleted(false);
        } else {
           product = ProductListManager.getInstance().getProductById(productId);
        }

        setModel(product);
        isLoadingData = false;
    }

    public boolean changeProductCaption(String caption){
        if (caption != null && !caption.isEmpty()) {
            model.setCaption(caption);
            return true;
        }
        return false;
    }

    public boolean changeProductPrice(String s){
        if(s!= null && !s.isEmpty()){
            double formattedPrice = Double.valueOf(s);
            int price = (int)Math.round(formattedPrice * new Double(100));
            model.setPrice(price);
            return true;
        } else {
            return false;
        }

    }

    public void OnSaveButtonClicked() {
        if (model.getCaption() != null && !model.getCaption().isEmpty()){
            writeProductToDb(model);

            if(setupDone()) ((AddChangeProductActivity)view()).onBackPressed();

        }

    }

    private void writeProductToDb(Product product){
        CartDatabase db = CartApplication.getInstance().getDatabase();
        Flowable.just(product)
                .subscribeOn(Schedulers.io())
                .subscribe(product1 -> {
                    Long id = db.mProductDao().insert(product1);

                    Flowable.just(id).observeOn(AndroidSchedulers.mainThread())
                            .subscribe(aLong -> {
                                product1.setID(aLong.intValue());
                                ProductListManager.getInstance().addProduct(product1);
                                    }
                                    ,throwable -> Log.e(TAG, "writeProductToDb: ", throwable));
                });

    }

}
