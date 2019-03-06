package ua.com.novasolutio.cart.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ua.com.novasolutio.cart.R;
import ua.com.novasolutio.cart.presenters.ProductListFragmentPresenter;

/* Фрагмент для відображення списку товарів */
public class ProductListFragment extends Fragment {
    private ProductListFragmentPresenter mPresenter;
    private TextView tvTotalBalance;
    private RecyclerView rvProductList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);
    }

    private void init(View v) {

        /* Ініціалізація презентера для роботи з фрагментом*/
        mPresenter = new ProductListFragmentPresenter();

        /*test data*/
        ((TextView) v.findViewById(R.id.tv_search_on_list_products)).setText(" SEARCH ON APP ");
        ((TextView) v.findViewById(R.id.tv_total_price_product_list_fragment)).setText("200,00 UAH");

        rvProductList = v.findViewById(R.id.rv_product_list_fragment);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        rvProductList.setLayoutManager(layoutManager);

        // Тут потрібно задати адаптер відображення даних в списку
//        ProductsListRecyclerAdapter adapter = new ProductsListRecyclerAdapter()

        mPresenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        /* Видалення посилань на невикористовувані об'єкти при знищенні фрагменту*/
        if (mPresenter != null) mPresenter.detachView();
        if(rvProductList != null){
            rvProductList.setLayoutManager(null);
            rvProductList.setAdapter(null);
        }
        super.onDestroy();
    }
}
