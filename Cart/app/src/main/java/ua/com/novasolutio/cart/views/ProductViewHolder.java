package ua.com.novasolutio.cart.views;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;


import javax.annotation.Nullable;

import ua.com.novasolutio.cart.R;
import ua.com.novasolutio.cart.presenters.ProductItemPresenter;

/* Клас для управління заповнення даними View елементу списка ProductsRecyclerView */
public class ProductViewHolder extends MvpViewHolder<ProductItemPresenter> implements ProductView{
    public static final String TAG = "ProductViewHolder";
    private final TextView productCaption;
    private final TextView productPrice;
    private final ImageView contextMenu;
    private final TextView productCount;

//    @Nullable private OnProductRightSwipeListener rSwipeListener;
//    @Nullable private OnProductLeftSwipeListener lSwipeListener;
    @Nullable private PopupMenu.OnMenuItemClickListener mItemClickListener;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        productCaption = (TextView) itemView.findViewById(R.id.tv_product_caption_list_products);
        productPrice = (TextView) itemView.findViewById(R.id.tv_product_price_product_list);
        contextMenu = (ImageView) itemView.findViewById(R.id.iv_context_menu_product_list);
        productCount = (TextView) itemView.findViewById(R.id.tv_count_selected_products_on_list);

        // TODO потрібно задати слухачі для свайпів по віджету та натиснення на кнопку контекстного меню
        contextMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onContextMenuClicked(v);
            }
        });

        // встановлення прослуховувача кліку по елементу меню
        mItemClickListener = new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                switch (id){
                    case R.id.product_change_item :

                        return true;

                    case R.id.product_delete_item :
                        presenter.onDeleteContextMenuItemClicked();
                        return true;
                }

                return false;
            }
        };
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

}
