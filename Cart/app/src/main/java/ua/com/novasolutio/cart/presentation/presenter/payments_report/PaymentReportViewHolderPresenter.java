package ua.com.novasolutio.cart.presentation.presenter.payments_report;

import android.view.View;

import ua.com.novasolutio.cart.model.data.Payment;
import ua.com.novasolutio.cart.presentation.presenter.BasePresenter;
import ua.com.novasolutio.cart.presentation.view.PaymentsListItem;
import ua.com.novasolutio.cart.ui.activity.payment.PaymentReportActivity;

public class PaymentReportViewHolderPresenter extends BasePresenter<Payment, PaymentsListItem> {

    @Override
    protected void updateView() {
        view().showPayment(model);
    }

    public void clickOnItem() {
        int id = model.getId();
        if(setupDone()) view().startPaymentReportActivity(PaymentReportActivity.class, id);
    }
}
