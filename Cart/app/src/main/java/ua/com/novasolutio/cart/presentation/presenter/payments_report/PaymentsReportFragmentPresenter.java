package ua.com.novasolutio.cart.presentation.presenter.payments_report;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import ua.com.novasolutio.cart.CartApplication;
import ua.com.novasolutio.cart.model.data.CurrencyManager;
import ua.com.novasolutio.cart.model.data.Payment;
import ua.com.novasolutio.cart.presentation.presenter.BasePresenter;
import ua.com.novasolutio.cart.presentation.view.PaymentsListReport;

public class PaymentsReportFragmentPresenter extends BasePresenter<List<Payment>, PaymentsListReport>
        implements CurrencyManager.CurrencyChangeListener {
    public static final String TAG = "PaymentsReportFragmentP";
    @Override
    protected void updateView() {
        if (setupDone()) view().showPaymentsList(model);
    }

    @Override
    public void bindView(@NonNull PaymentsListReport view) {
        super.bindView(view);
        if (model == null) getPaymantListFromDB();
        CurrencyManager.getInstance().addCurrencyChangeListener(this);
    }

    @Override
    public void unbindView() {
        CurrencyManager.getInstance().removeCurrencyChangeListener(this);
        super.unbindView();
    }

    // метод для асинхронного отримання з бази даних списку платежів
    private void getPaymantListFromDB() {
        CartApplication
                .getInstance()
                .getDatabase()
                .mPaymentDao()
                .getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(payments -> setModel(payments),
                        throwable -> Log.i(TAG, "getPaymantListFromDB: " + throwable.getMessage()));
    }


    @Override
    public void currencyNameChanged() {
        updateView();
    }
}
