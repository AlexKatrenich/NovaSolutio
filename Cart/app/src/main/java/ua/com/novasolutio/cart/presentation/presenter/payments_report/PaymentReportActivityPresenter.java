package ua.com.novasolutio.cart.presentation.presenter.payments_report;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
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
             CartDatabase db = CartApplication.getInstance().getDatabase();
            Flowable<Payment> paymentFlowable = db.mPaymentDao().getById(paymentId);
            List<ProductPaymentJoin> paymentJoinList = new ArrayList<>();
            db.mProductPaymentDao().getSoldProductsCount(paymentId)
                    .subscribe(paymentJoinList::addAll);

            ArrayList<Product> products = new ArrayList<>();
            Observable.fromArray(paymentJoinList.toArray())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(o -> {
                        int id = ((ProductPaymentJoin)o).productID;
                        int count = ((ProductPaymentJoin)o).productCount;
                        Product product = db.mProductDao().getById(id);
                        product.setCount(count);
                        return product;
                    }).subscribe(new Observer<Product>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Product product) {
                    products.add(product);
                }

                @Override
                public void onError(Throwable e) {
                    Log.e(TAG, "onError: ", e);
                }

                @Override
                public void onComplete() {
                    paymentFlowable.observeOn(AndroidSchedulers.mainThread())
                            .subscribe(p -> {
                        p.setProducts(products);
                        setModel(p);
                    });
                }
            });
        }
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public void backButtonClicked() {
        view().onButtonBackPressed();
    }
}
