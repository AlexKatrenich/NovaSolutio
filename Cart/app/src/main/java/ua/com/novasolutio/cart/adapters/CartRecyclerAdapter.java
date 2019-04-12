package ua.com.novasolutio.cart.adapters;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import ua.com.novasolutio.cart.R;
import ua.com.novasolutio.cart.model.data.Product;
import ua.com.novasolutio.cart.presentation.presenter.product_item.CartItemPresenter;
import ua.com.novasolutio.cart.ui.view_holder.CartItemViewHolder;


public class CartRecyclerAdapter extends MvpRecyclerListAdapter<Product, CartItemPresenter, CartItemViewHolder> {
    public static final String TAG = "CartRecyclerAdapter";


    @NonNull
    @Override
    protected Object getModelId(Product model) {
        return model.getID();
    }

    @NonNull
    @Override
    protected CartItemPresenter createPresenter(@NonNull Product model) {
        CartItemPresenter presenter = new CartItemPresenter();
        presenter.setModel(model);
        return presenter;
    }

    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        Log.i(TAG, "onCreateViewHolder:");
        return new CartItemViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart_recycler_view, parent, false));
    }
}
