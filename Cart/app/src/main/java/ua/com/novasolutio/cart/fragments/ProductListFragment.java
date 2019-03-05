package ua.com.novasolutio.cart.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ua.com.novasolutio.cart.R;
import ua.com.novasolutio.cart.presenters.ProductListPresenter;

/* Фрагмент для відображення списку товарів */
public class ProductListFragment extends Fragment {
    private ProductListPresenter mPresenter;


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
        /*test data*/
        ((TextView) v.findViewById(R.id.tv_search_on_list_products)).setText(" SEARCH ON APP ");
        ((TextView) v.findViewById(R.id.tv_total_price_product_list_fragment)).setText("200,00 UAH");
    }


}
