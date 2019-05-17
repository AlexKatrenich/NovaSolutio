package ua.com.novasolutio.cart.presentation.presenter.payments_report;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ua.com.novasolutio.cart.model.data.Payment;
import ua.com.novasolutio.cart.presentation.presenter.BasePresenter;
import ua.com.novasolutio.cart.presentation.view.PaymentsListReport;

public class PaymentsReportFragmentPresenter extends BasePresenter<List<Payment>, PaymentsListReport> {

    @Override
    protected void updateView() {
        if (setupDone()) view().showPaymentsList(model);
    }

    @Override
    public void bindView(@NonNull PaymentsListReport view) {
        super.bindView(view);
        showModel();
    }

    private void showModel() {
        ArrayList<Payment> list = new ArrayList<>();

        list.add(new Payment(153, Calendar.getInstance().getTimeInMillis(), 35000, 1203));
        list.add(new Payment(154, Calendar.getInstance().getTimeInMillis(), 35000, 1203));
        list.add(new Payment(155, Calendar.getInstance().getTimeInMillis(), 35000, 1203));
        list.add(new Payment(156, Calendar.getInstance().getTimeInMillis(), 35000, 1203));
        list.add(new Payment(157, Calendar.getInstance().getTimeInMillis(), 35000, 1203));
        list.add(new Payment(158, Calendar.getInstance().getTimeInMillis(), 35000, 1203));
        list.add(new Payment(159, Calendar.getInstance().getTimeInMillis(), 35000, 1203));
        list.add(new Payment(160, Calendar.getInstance().getTimeInMillis(), 35000, 1203));

        setModel(list);
    }

}
