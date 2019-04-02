package ua.com.novasolutio.cart.views.fragments;

import android.arch.lifecycle.Lifecycle;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ua.com.novasolutio.cart.R;
import ua.com.novasolutio.cart.adapters.CartRecyclerAdapter;
import ua.com.novasolutio.cart.data.Product;
import ua.com.novasolutio.cart.presenters.CartFragmentPresenter;
import ua.com.novasolutio.cart.presenters.PresenterManager;
import ua.com.novasolutio.cart.views.ProductsListView;

public class CartFragment extends Fragment implements ProductsListView {
    private static final String TAG = "CartFragment";
    @BindView(R.id.tv_cart_total_price_products)
    TextView tvTotalPrice; // для відображення загальної ціни обраних продуктів в списку

    @BindView(R.id.rv_cart_selected_product_list)
    RecyclerView mRecyclerView; // для відображення списку обраних продуктів в корзині

    private CartRecyclerAdapter mAdapter;

    private CartFragmentPresenter mPresenter; // клас для обробки логіки по цьому екрану

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // ініціалізація вкладених елементів в фрагменті
        ButterKnife.bind(this, view);
        init(view, savedInstanceState);
    }

    private void init(View view, Bundle saveInstanceState) {
        if (saveInstanceState == null){
            mPresenter = new CartFragmentPresenter();
        } else {
            mPresenter = PresenterManager.getInstance().restorePresenter(saveInstanceState);
        }

        getLifecycle().addObserver(mPresenter);

        mAdapter = new CartRecyclerAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onStart() {
        Log.i(TAG, "onStart");
        mPresenter.bindView(this);
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop:");
        mPresenter.unbindView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mRecyclerView !=null){
            mRecyclerView.setLayoutManager(null);
            mRecyclerView.setAdapter(null);
        }
        getLifecycle().removeObserver(mPresenter);
        Log.i(TAG, "onDestroy: ");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState:");
        PresenterManager.getInstance().savePresenter(mPresenter, outState);
    }

    public void setTotalPrice(String formattedPrice){
        tvTotalPrice.setText(formattedPrice);
    }

    @Override
    public void showEmpty() {
        showProducts(new ArrayList<Product>());
    }

    @Override
    public void showProducts(List<Product> products) {
        mAdapter.clearAndAddAll(products);
    }

    @Override
    public void showMessage(String message) {
        if (message != null && !message.equals("")) {
            Toast.makeText(this.getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    public void showItemRemove(Product product){
        mAdapter.removeItem(product);
    }
}
