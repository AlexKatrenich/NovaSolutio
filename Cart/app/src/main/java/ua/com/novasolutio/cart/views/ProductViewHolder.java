package ua.com.novasolutio.cart.views;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import javax.annotation.Nullable;

import ua.com.novasolutio.cart.R;
import ua.com.novasolutio.cart.presenters.ProductPresenter;

/* Клас для управління заповнення даними View елементу списка ProductsRecyclerView */
public class ProductViewHolder extends MvpViewHolder<ProductPresenter> implements ProductView{
    public static final String TAG = "ProductViewHolder";
    private final TextView productCaption;
    private final TextView productPrice;
    private final ImageView contextMenu;
    private final TextView productCount;
//    @Nullable private OnProductRightSwipeListener rSwipeListener;
//    @Nullable private OnProductLeftSwipeListener lSwipeListener;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        productCaption = (TextView) itemView.findViewById(R.id.tv_product_caption_list_products);
        productPrice = (TextView) itemView.findViewById(R.id.tv_product_price_product_list);
        contextMenu = (ImageView) itemView.findViewById(R.id.iv_context_menu_product_list);
        productCount = (TextView) itemView.findViewById(R.id.tv_count_selected_products_on_list);

        // TODO потрібно задати слухачі для свайпів по віджету та натиснення на кнопку контекстного меню

    }

    @Override
    public void setProductCaption(String caption) {
        productCaption.setText(caption);
    }

    @Override
    public void setProductPrice(int price) {
        String formattedPrice = formatPriceForView(price);
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

    /* метод форматує візуальне представлення ціни для View*/
    private String formatPriceForView(int price) {
        StringBuffer priceString = new StringBuffer(String.valueOf(price));
        Log.i(TAG, "formatPriceForView: price: " + priceString.toString());
        Log.i(TAG, "formatPriceForView: length: " + String.valueOf(priceString.length()));
        switch (priceString.length()){
            case 0 :
                priceString.append("0,00");
                break;
            case 1:
                priceString.insert(0, "0,0");
                break;
            case 2:
                priceString.insert(0, "0,");
                break;
            default:
                priceString.insert(priceString.length() - 2, ',');
        }

        // додавання назви грошових одиниць до відображення ціни на екрані
        String currency = (String) productPrice.getResources().getText(R.string.name_currency);
        priceString.append(' ').append(currency).append(' ');

        return priceString.toString();
    }



}
