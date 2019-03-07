package ua.com.novasolutio.cart.views.fragments;

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

import java.util.ArrayList;
import java.util.List;

import ua.com.novasolutio.cart.R;
import ua.com.novasolutio.cart.adapters.ProductsListRecyclerAdapter;
import ua.com.novasolutio.cart.data.Product;
import ua.com.novasolutio.cart.presenters.PresenterManager;
import ua.com.novasolutio.cart.presenters.ProductListFragmentPresenter;
import ua.com.novasolutio.cart.views.ProductsListView;


/* Фрагмент для відображення списку товарів */
public class ProductListFragment extends Fragment implements ProductsListView {
    private static final String TAG = "ProductListFragment";
    private ProductListFragmentPresenter mPresenter;
    private TextView tvTotalBalance;
    private RecyclerView rvProductList;
    private ProductsListRecyclerAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view, savedInstanceState);
    }

    private void init(View v, Bundle savedInstanceState) {

        /* Ініціалізація презентера для роботи з фрагментом*/
        if (savedInstanceState == null){
            mPresenter = new ProductListFragmentPresenter();
        } else {
            mPresenter = PresenterManager.getInstance().restorePresenter(savedInstanceState);
        }


        /*test data*/
        ((TextView) v.findViewById(R.id.tv_search_on_list_products)).setText(" SEARCH ON APP ");
        ((TextView) v.findViewById(R.id.tv_total_price_product_list_fragment)).setText("200,00 UAH");

        rvProductList = v.findViewById(R.id.rv_product_list_fragment);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(v.getContext(), LinearLayoutManager.VERTICAL, false);
        rvProductList.setLayoutManager(layoutManager);

        // Тут потрібно задати адаптер відображення даних в списку
        mAdapter = new ProductsListRecyclerAdapter();
        rvProductList.setAdapter(mAdapter);

        Log.i(TAG, "init done");


    }

    @Override
    public void onResume() {
        super.onResume();

        mPresenter.bindView(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        mPresenter.unbindView();
    }

    @Override
    public void onDestroy() {
        /* Видалення посилань на невикористовувані об'єкти при знищенні фрагменту*/
        if(rvProductList != null){
            rvProductList.setLayoutManager(null);
            rvProductList.setAdapter(null);
        }
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        PresenterManager.getInstance().savePresenter(mPresenter, outState);
    }

    // викликається метод showProducts з пустим списком елементів
    @Override
    public void showEmpty() {
        showProducts(new ArrayList<Product>());
    }

    @Override
    public void showProducts(List<Product> products) {
        mAdapter.clearAndAddAll(products);
    }

    @Override
    public void showLoading() {

    }

}
