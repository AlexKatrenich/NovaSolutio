package ua.com.novasolutio.cart.adapters;


import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import ua.com.novasolutio.cart.R;
import ua.com.novasolutio.cart.model.data.Product;
import ua.com.novasolutio.cart.presentation.presenter.product_item.ProductItemPresenter;
import ua.com.novasolutio.cart.ui.view_holder.ProductViewHolder;

/* Адаптер для управління відображення даних в RecyclerView фрагмента ProductListFragment*/
public class ProductsListRecyclerAdapter extends MvpRecyclerListAdapter<Product, ProductItemPresenter, ProductViewHolder>{

    private static final String TAG = "ProdListRecyclerAdapter";

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new ProductViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_list_recycler_view, parent, false));
    }

    @NonNull
    @Override
    protected Object getModelId(Product model) {
        return model.getID();
    }

    @NonNull
    @Override
    protected ProductItemPresenter createPresenter(@NonNull Product product) {
        ProductItemPresenter presenter = new ProductItemPresenter();
        presenter.setModel(product);
        return presenter;
    }

}
