package ua.com.novasolutio.cart.presenters;

import ua.com.novasolutio.cart.data.Product;
import ua.com.novasolutio.cart.views.viewHolders.CartItemViewHolder;

public class CartItemPresenter extends BasePresenter<Product, CartItemViewHolder> {
    public static final String TAG = "CartItemPresenter";

    protected int position;

    @Override
    protected void updateView() {
        view().setProductCaption(model.getCaption());
        view().setCounterProduct(model.getCount());

        String formattedPrice = formatPriceOnText((long) model.getPrice());
        view().setProductPrice(formattedPrice);
    }

    public void setPosition(int pos){
        position = pos;
    }

}
