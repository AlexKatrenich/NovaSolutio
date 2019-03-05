package ua.com.novasolutio.cart.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ua.com.novasolutio.cart.data.Product;

/* Адаптер для управління відображення даних в RecyclerView фрагмента ProductListFragment*/
public class ProductsListRecyclerAdapter extends RecyclerView.Adapter<ProductsListRecyclerAdapter.ProductsViewHolder> {
    private List<Product> mList;



    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder productsViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ProductsViewHolder extends RecyclerView.ViewHolder {

        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(Product product){

        }
    }
}
