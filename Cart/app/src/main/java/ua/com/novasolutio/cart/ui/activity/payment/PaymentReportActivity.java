package ua.com.novasolutio.cart.ui.activity.payment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import butterknife.ButterKnife;
import ua.com.novasolutio.cart.R;
import ua.com.novasolutio.cart.model.data.Payment;
import ua.com.novasolutio.cart.presentation.presenter.PresenterManager;
import ua.com.novasolutio.cart.presentation.presenter.payments_report.PaymentReportActivityPresenter;
import ua.com.novasolutio.cart.presentation.view.PaymentReportView;

public class PaymentReportActivity extends AppCompatActivity implements PaymentReportView {
    public static final String TAG = "PaymentReportActivity";
    private PaymentReportActivityPresenter mPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_report);
        ButterKnife.bind(this);
        init(savedInstanceState);
    }

    private void init(Bundle savedInstanceState) {
        if (savedInstanceState == null){
            mPresenter = new PaymentReportActivityPresenter();
        } else {
            mPresenter = PresenterManager.getInstance().restorePresenter(savedInstanceState);
        }


    }

    @Override
    protected void onStart() {
        Log.i(TAG, "onStart: ");
        mPresenter.bindView(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "onStop: ");
        mPresenter.unbindView();
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        PresenterManager.getInstance().savePresenter(mPresenter, outState); // збереження презентеру
        super.onSaveInstanceState(outState);
    }

    @Override
    public void showPayment(Payment payment) {
        Log.i(TAG, "showPayment: ");
    }
}
