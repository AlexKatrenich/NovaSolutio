package ua.com.novasolutio.cart.presentation.presenter.payments_report;

import ua.com.novasolutio.cart.model.data.Payment;
import ua.com.novasolutio.cart.presentation.presenter.BasePresenter;
import ua.com.novasolutio.cart.presentation.view.PaymentReportView;

public class PaymentReportActivityPresenter extends BasePresenter<Payment, PaymentReportView> {

    @Override
    protected void updateView() {
        if (setupDone()) view().showPayment(model);
    }

}
