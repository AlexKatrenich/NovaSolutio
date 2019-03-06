package ua.com.novasolutio.cart.views;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import ua.com.novasolutio.cart.presenters.BasePresenter;

/* Абстрактний клас як загальна модель презентера для ViewHolder*/
public abstract class MvpViewHolder<P extends BasePresenter> extends RecyclerView.ViewHolder {
    protected P presenter;

    public MvpViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void bindPresenter(P presenter){
        this.presenter = presenter;
        presenter.bindView(this);
    }

    public void unbindPresenter(){
        presenter = null;
    }
}
