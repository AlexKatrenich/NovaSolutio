package ua.com.novasolutio.cart.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ua.com.novasolutio.cart.R;
import ua.com.novasolutio.cart.model.data.Payment;
import ua.com.novasolutio.cart.presentation.presenter.payments_report.PaymentReportViewHolderPresenter;
import ua.com.novasolutio.cart.ui.view_holder.PaymentReportViewHolder;

public class PaymentsListAdapter
        extends MvpRecyclerListAdapter<Payment, PaymentReportViewHolderPresenter, PaymentReportViewHolder> {

    private List<Payment> mPaymentList = new ArrayList<>();

    @NonNull
    @Override
    public PaymentReportViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PaymentReportViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_payments_list, viewGroup, false));
    }

    @NonNull
    @Override
    protected Object getModelId(Payment model) {
        return model.getId();
    }

    @NonNull
    @Override
    protected PaymentReportViewHolderPresenter createPresenter(@NonNull Payment model) {
        PaymentReportViewHolderPresenter presenter = new PaymentReportViewHolderPresenter();
        presenter.setModel(model);
        return presenter;
    }


}
