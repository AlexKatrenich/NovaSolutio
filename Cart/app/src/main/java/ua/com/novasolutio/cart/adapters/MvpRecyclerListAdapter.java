package ua.com.novasolutio.cart.adapters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ua.com.novasolutio.cart.presenters.BasePresenter;
import ua.com.novasolutio.cart.views.MvpViewHolder;

/* Абстрактний клас для інкапсуляції типових дій відображення елементів списку в RecyclerView*/
public abstract class MvpRecyclerListAdapter <M, P extends BasePresenter, VH extends MvpViewHolder<P>>
        extends MvpRecyclerAdapter<M, P, VH>{

    private final List<M> models;

    public MvpRecyclerListAdapter() {
        models = new ArrayList<>();
    }

    public void clearAndAddAll(Collection<M> data){
        models.clear();
        presenters.clear();

        for (M item: data) {
            addInternal(item);
        }

        notifyDataSetChanged();
    }

    public void addAll(Collection<M> data){
        for (M item :data) {
            addInternal(item);
        }

        int addedSize = data.size();
        int oldSize = models.size() - data.size();
        notifyItemRangeInserted(oldSize, addedSize);
    }

    public void addItem(M item){
        addInternal(item);
        notifyItemInserted(models.size());
    }

    public void updateItem(M item){
        Object modelId = getModelId(item);

        // Заміна елемента моделі
        int position = getItemPosition(item);
        if(position >= 0){
            models.remove(position);
            models.add(position, item);
        }

        // заміна елемента презентера
        P existingPresenter = presenters.get(modelId);
        if (existingPresenter != null){
            existingPresenter.setModel(item);
        }

        if(position >= 0){
            notifyItemChanged(position);
        }

    }

    private int getItemPosition(M item) {
        Object modelId = getModelId(item);

        int position = -1;

        for (int i = 0; i < models.size(); i++) {
            M model = models.get(i);
            if(getModelId(model).equals(modelId)){
                position = i;
                break;
            }
        }

        return position;
    }

    private void addInternal(M item){
        models.add(item);
        presenters.put(getModelId(item), createPresenter(item));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    @Override
    protected M getItem(int position) {
        return models.get(position);
    }
}
