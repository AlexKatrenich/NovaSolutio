package ua.com.novasolutio.cart.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import java.util.HashMap;
import java.util.Map;

import ua.com.novasolutio.cart.presenters.BasePresenter;
import ua.com.novasolutio.cart.views.MvpViewHolder;

public abstract class MvpRecyclerAdapter <M, P extends BasePresenter, VH extends MvpViewHolder> extends RecyclerView.Adapter<VH> {
    protected final Map<Object, P> presenters;

    public MvpRecyclerAdapter(){
        presenters = new HashMap<>();
    }

    @NonNull protected P getPresenter(@NonNull M model){
        return presenters.get(getModelId(model));
    }

    @NonNull protected abstract Object getModelId(M model);

    @NonNull protected abstract P createPresenter(@NonNull M model);

    @Override
    public void onViewRecycled(@NonNull VH holder) {
        super.onViewRecycled(holder);
        holder.unbindPresenter();
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.bindPresenter(getPresenter(getItem(position)));
    }

    protected abstract M getItem(int position);

    public void removePresenter(M model){
        presenters.remove(getModelId(model));
    }


}
