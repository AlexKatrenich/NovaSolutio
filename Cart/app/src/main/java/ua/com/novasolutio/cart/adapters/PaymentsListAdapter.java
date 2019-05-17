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
import ua.com.novasolutio.cart.ui.view_holder.PaymentReportViewHolder;

public class PaymentsListAdapter extends RecyclerView.Adapter<PaymentReportViewHolder> {
    private List<Payment> mPaymentList = new ArrayList<>();

    @NonNull
    @Override
    public PaymentReportViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PaymentReportViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_payments_list, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentReportViewHolder holder, int i) {
        holder.bind(mPaymentList.get(i));
    }

    @Override
    public int getItemCount() {
        return mPaymentList.size();
    }

    public void setPayments(Collection<Payment> payments){
        mPaymentList.addAll(payments);
        notifyDataSetChanged();
    }

    public void clearItems(){
        mPaymentList.clear();
        notifyDataSetChanged();
    }

    public void addPaymentReportRecently(Payment payment){
        mPaymentList.add(payment);
        notifyItemInserted(mPaymentList.size() - 1);
    }


}
