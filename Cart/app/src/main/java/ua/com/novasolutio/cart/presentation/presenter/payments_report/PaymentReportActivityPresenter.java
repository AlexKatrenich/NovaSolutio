package ua.com.novasolutio.cart.presentation.presenter.payments_report;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;
import ua.com.novasolutio.cart.CartApplication;
import ua.com.novasolutio.cart.model.dao.CartDatabase;
import ua.com.novasolutio.cart.model.data.Payment;
import ua.com.novasolutio.cart.model.data.Product;
import ua.com.novasolutio.cart.model.data.ProductPaymentJoin;
import ua.com.novasolutio.cart.presentation.presenter.BasePresenter;
import ua.com.novasolutio.cart.presentation.view.PaymentReportView;

public class PaymentReportActivityPresenter extends BasePresenter<Payment, PaymentReportView> {
    private Integer paymentId;
    public static final String TAG = "PaymentReportActivityP";

    @Override
    protected void updateView() {
        Log.i(TAG, "updateView: " + model);
        if (setupDone()) view().showPayment(model);
    }

    @Override
    public void bindView(@NonNull PaymentReportView view) {
        super.bindView(view);
        getModelFromDB();
    }

    @Override
    public void setModel(Payment model) {
        Log.i(TAG, "setModel: " + model);
        super.setModel(model);
    }

    private void getModelFromDB() {
        if (paymentId != null && paymentId != -1){
            Flowable<Payment> paymentFlowable = getPaymentFromDb(paymentId);
//            paymentFlowable.subscribe(this::setModel);
            Flowable<List<ProductPaymentJoin>> productPaymentList = getProductsPaymentListByPaymentId(paymentId);


            List<Product> productList = new ArrayList<>();
            paymentFlowable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .zip(productPaymentList, paymentFlowable, (productPaymentJoins, payment) -> {
                        List<Product> products = new ArrayList<>();
                        for (ProductPaymentJoin ppj : productPaymentJoins) {
                            int id = ppj.productID;
                            int count = ppj.productCount;
                            Product product = CartApplication.getInstance().getDatabase().mProductDao().getById(id);
                            product.setCount(count);
                            products.add(product);
                        }
                        payment.setProducts(products);
                        return payment;
                    }).subscribe(this::setModel, throwable -> Log.i(TAG, "getModelFromDB: "));

//            productPaymentList.subscribe(productPaymentJoins -> Observable.fromArray(productPaymentJoins.toArray())
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .zipWith()
//

//                    .map(o -> {
//                        int id = ((ProductPaymentJoin)o).productID;
//                        int count = ((ProductPaymentJoin)o).productCount;
//                        Log.i(TAG, "getModelFromDB: ProductID: " + id);
//                        Single<Product> product = CartApplication.getInstance().getDatabase().mProductDao().getById(id);
//                        product.subscribe((p, throwable) -> p.setCount(count));
//                        return null;
//                    }).subscribe(productList::add
//                        , throwable -> {
//                                Log.e(TAG, "getModelFromDB: Exception", throwable);
//                            }
//                        , () -> {
//                                if (setupDone()) model.setProducts(productList);
//                                updateView();
//                                Log.i(TAG, "getModelFromDB: productList: " + productList);
//                            }));

        }
    }

    // метод для отримання чеку з БД по ІД
    private Flowable<Payment> getPaymentFromDb(int id){
        CartDatabase db = CartApplication.getInstance().getDatabase();
        return db.mPaymentDao().getById(paymentId);
    }

    // метод для отримання списку об'єктів з таблиці ProductPaymentJoin
    private Flowable<List<ProductPaymentJoin>> getProductsPaymentListByPaymentId(int id){
        CartDatabase db = CartApplication.getInstance().getDatabase();
        return db.mProductPaymentDao().getSoldProductsCount(id);
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public void backButtonClicked() {
        view().onButtonBackPressed();
    }
}
