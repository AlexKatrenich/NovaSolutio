package ua.com.novasolutio.cart.presenters;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<M, V>{
    protected M model;
    private WeakReference<V> view;

    public void setModel(M model) {
        resetState();
        this.model = model;
        if (setupDone()){
            updateView();
        }
    }

    protected void resetState() {

    }

    protected V view() {
        if (view == null) {
            return null;
        } else {
            return view.get();
        }
    }

    protected boolean setupDone() {
        return view() != null && model != null ;
    }


    protected abstract void updateView();

}
