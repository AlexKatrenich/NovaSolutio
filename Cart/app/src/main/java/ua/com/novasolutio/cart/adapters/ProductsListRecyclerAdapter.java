package ua.com.novasolutio.cart.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ua.com.novasolutio.cart.R;
import ua.com.novasolutio.cart.data.Product;
import ua.com.novasolutio.cart.presenters.ProductListFragmentPresenter;

/* Адаптер для управління відображення даних в RecyclerView фрагмента ProductListFragment*/
public class ProductsListRecyclerAdapter extends RecyclerView.Adapter<ProductsListRecyclerAdapter.ProductsViewHolder> {
    private List<Product> mList;
    private ProductListFragmentPresenter mPresenter;

    public ProductsListRecyclerAdapter(ProductListFragmentPresenter presenter){
        mPresenter = presenter;
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_list_recycler_view, parent, false);
        return new ProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, int i) {
        holder.bind(mList.get(i));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ProductsViewHolder extends RecyclerView.ViewHolder {
        private TextView mProductCaption, mProductPrice, mCountSelectedProductType;
        private ImageView mContextMenuButton;

        public ProductsViewHolder(@NonNull View v) {
            super(v);
            mCountSelectedProductType = v.findViewById(R.id.tv_count_selected_products_on_list);
            mProductCaption = v.findViewById(R.id.tv_product_caption_list_products);
            mProductPrice = v.findViewById(R.id.tv_product_price_product_list);
            mContextMenuButton = v.findViewById(R.id.iv_context_menu_product_list);
        }

        public void bind(Product product){
            mProductCaption.setText(product.getmCaption());
            mProductPrice.setText(product.getmPrice());
        }
    }
}
