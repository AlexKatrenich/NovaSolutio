package ua.com.novasolutio.cart.views.viewHolders;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageButton;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ua.com.novasolutio.cart.R;
import ua.com.novasolutio.cart.presenters.CartItemPresenter;
import ua.com.novasolutio.cart.views.ProductItemListView;

public class CartItemViewHolder extends MvpViewHolder<CartItemPresenter> implements ProductItemListView {
    public static final String TAG = "CartItemViewHolder";

    @BindView(R.id.ib_delete_item_from_cart_list)
    AppCompatImageButton ibDeleteItem;

    @BindView(R.id.tv_item_name_cart_list)
    TextView tvItemName;

    @BindView(R.id.tv_item_count_cart_list)
    TextView tvItemCount;

    @BindView(R.id.tv_item_price_cart_list)
    TextView tvItemPrice;

    public CartItemViewHolder(@NonNull final View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    @OnClick(R.id.ib_delete_item_from_cart_list)
    public void onDeleteButtonClicked(View view){
        presenter.onDeleteButtonClicked();
        Log.i(TAG, "onDeleteButtonClicked: ");
    }

    @Override
    public void setCounterProduct(int count) {
        if (count == 0){
            tvItemCount.setText("");
        } else {
            tvItemCount.setText(String.valueOf(count));
        }
    }

    @Override
    public void setProductCaption(String caption) {
        tvItemName.setText(caption);
    }

    @Override
    public void setProductPrice(String formattedPrice) {
        tvItemPrice.setText(formattedPrice);
    }
}
