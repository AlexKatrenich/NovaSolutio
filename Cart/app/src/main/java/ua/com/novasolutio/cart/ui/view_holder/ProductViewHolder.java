package ua.com.novasolutio.cart.ui.view_holder;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;


import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ua.com.novasolutio.cart.R;
import ua.com.novasolutio.cart.presentation.presenter.product_item.ProductItemPresenter;
import ua.com.novasolutio.cart.presentation.view.ProductItemListView;

/* Клас для управління заповнення даними View елементу списка ProductsRecyclerView */
public class ProductViewHolder extends MvpViewHolder<ProductItemPresenter> implements ProductItemListView {
    public static final String TAG = "ProductViewHolder";

    @BindView(R.id.tv_product_caption_list_products) protected TextView productCaption;
    @BindView(R.id.tv_product_price_product_list) protected TextView productPrice;
    @BindView(R.id.iv_context_menu_product_list) protected ImageView contextMenu;
    @BindView(R.id.tv_count_selected_products_on_list) protected TextView productCount;
    @BindView(R.id.ib_item_product_list_recycler) protected ImageButton btnCancel;

    @Nullable private PopupMenu.OnMenuItemClickListener mItemClickListener;

    public ProductViewHolder(@NonNull final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);


        // встановлення прослуховувача кліку по елементу меню
        mItemClickListener = new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                switch (id){
                    case R.id.product_change_item :
                        presenter.onChangeContextMenuItemClicked(itemView.getContext());
                        return true;

                    case R.id.product_delete_item :
                        presenter.onDeleteContextMenuItemClicked();
                        return true;
                }

                return false;
            }
        };

        // встановлення прослуховувача кліку по загальному елементу RecyclerView
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onItemClick();
            }
        });

    }

    @OnClick(R.id.iv_context_menu_product_list)
    public void contextMenuClick(View view){
        presenter.onContextMenuClicked(view);
    }

    @OnClick(R.id.ib_item_product_list_recycler)
    public void onCancelButtonClicked(View view){
        presenter.onCanceledButtonClicked();
    }

    @Override
    public void bindPresenter(ProductItemPresenter presenter) {
        super.bindPresenter(presenter);
        presenter.setPosition(getAdapterPosition());
    }

    @Override
    public void setProductCaption(String caption) {
        productCaption.setText(caption);
    }

    @Override
    public void setProductPrice(String formattedPrice) {
        productPrice.setText(formattedPrice);
    }

    @Override
    public void setCounterProduct(int count) {
        if (count == 0){
            productCount.setText("");
            productCount.setVisibility(TextView.INVISIBLE);
        } else {
            productCount.setText(String.valueOf(count));
            productCount.setVisibility(TextView.VISIBLE);
        }
    }

    public void showPopupMenu(View v) {
        PopupMenu menu = new PopupMenu(v.getContext(), v);
        menu.inflate(R.menu.product_list_context_menu);
        if(mItemClickListener== null){
            Log.i(TAG, "showPopupMenu: mItemClickListener== null");
        } else {
            menu.setOnMenuItemClickListener(mItemClickListener);
            Log.i(TAG, "showPopupMenu: menu.setOnMenuItemClickListener");
        }
        menu.show();
    }

    public void changeCancelButtonSize(int size) {
        if (btnCancel != null){
            ViewGroup.LayoutParams params =  btnCancel.getLayoutParams();
            params.width = size;
            btnCancel.setLayoutParams(params);
        }
    }
}
