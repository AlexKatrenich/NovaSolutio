package ua.com.novasolutio.cart.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ua.com.novasolutio.cart.R;
import ua.com.novasolutio.cart.adapters.PaymentsListAdapter;
import ua.com.novasolutio.cart.model.data.Payment;
import ua.com.novasolutio.cart.presentation.presenter.PresenterManager;
import ua.com.novasolutio.cart.presentation.presenter.payments_report.PaymentsReportFragmentPresenter;
import ua.com.novasolutio.cart.presentation.view.PaymentsListReport;

public class PaymentsReportFragment extends Fragment implements PaymentsListReport {
    public static final String TAG = "PaymentsReportFragment";

    private PaymentsReportFragmentPresenter mPresenter;

    @BindView(R.id.rv_report_checks_list)
    protected RecyclerView mRecyclerView;
    private PaymentsListAdapter mPaymentsListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reports, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        init(view, savedInstanceState);
    }

    private void init(View view, Bundle savedInstanceState) {
        mPaymentsListAdapter = new PaymentsListAdapter();
        LinearLayoutManager rvManager = new LinearLayoutManager(view.getContext(),
                LinearLayoutManager.VERTICAL,
                false);

        mRecyclerView.setAdapter(mPaymentsListAdapter);
        mRecyclerView.setLayoutManager(rvManager);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null) {
            mPresenter = PresenterManager.getInstance().restorePresenter(savedInstanceState);
        } else {
            mPresenter = new PaymentsReportFragmentPresenter();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        PresenterManager.getInstance().savePresenter(mPresenter, outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: " + String.valueOf(mPresenter==null));
        mPresenter.bindView(this);
    }

    @Override
    public void onStop() {
        mPresenter.unbindView();
        super.onStop();
    }

    @Override
    public void showPaymentsList(List<Payment> payments) {
        Log.i(TAG, "showPaymentsList: " + payments);
        mPaymentsListAdapter.clearAndAddAll(payments);
    }

    @Override
    public void onDestroy() {
        if(mRecyclerView != null) mRecyclerView.setAdapter(null);
        if(mRecyclerView != null) mRecyclerView.setLayoutManager(null);
        super.onDestroy();
    }

}
