package ua.com.novasolutio.cart.ui.activity.payment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ua.com.novasolutio.cart.R;
import ua.com.novasolutio.cart.model.data.Payment;
import ua.com.novasolutio.cart.presentation.presenter.PresenterManager;
import ua.com.novasolutio.cart.presentation.presenter.payments_report.PaymentReportActivityPresenter;
import ua.com.novasolutio.cart.presentation.view.PaymentReportView;

public class PaymentReportActivity extends AppCompatActivity implements PaymentReportView {
    public static final String TAG = "PaymentReportActivity";
    private PaymentReportActivityPresenter mPresenter;
    public static final String PAYMENT_ID_TAG = "PAYMENT_ID_TAG";

    @BindView(R.id.tv_check_number)
    protected TextView checkNumber;

    @BindView(R.id.tv_check_date)
    protected TextView checkDate;

    @BindView(R.id.tv_total_price_payment_report)
    protected TextView checkTotalPrice;

    @BindView(R.id.tv_user_cash_payment_report)
    protected TextView checkUserCash;

    @BindView(R.id.tv_check_change)
    protected TextView checkChange;



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

        int paymentId = getIntent().getIntExtra(PAYMENT_ID_TAG, -1);
        mPresenter.setPaymentId(paymentId);
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
        checkNumber.setText(String.valueOf(payment.getId()));

        // встановлення дати в текстовому вигляді
        Date time = new Date(payment.getPaymentDate());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getDefault());
        checkDate.setText(dateFormat.format(time));

        checkChange.setText(
                mPresenter.formatPriceOnText(payment.getChange())
        );

        checkTotalPrice.setText(
                mPresenter.formatPriceOnText(payment.getTotalPrice())
        );

        checkUserCash.setText(
                mPresenter.formatPriceOnText(payment.getUserCash())
        );

    }

    @Override
    public void onButtonBackPressed() {
        Log.i(TAG, "onButtonBackPressed: ");
        onBackPressed();
    }

    @OnClick(R.id.ib_back_payment_report_activity)
    public void onBackButtonClick(){
        Log.i(TAG, "onBackButtonClick: ");
        mPresenter.backButtonClicked();
    }


}
