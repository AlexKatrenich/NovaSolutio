package ua.com.novasolutio.cart.presenters;

import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<M, V>{
    protected M model;
    private WeakReference<V> view;

    /**/
    public void setModel(M model) {
        resetState();
        this.model = model;
        if (setupDone()){
            updateView();
        }
    }

    protected void resetState() {

    }

    /* метод для отримання полсилання на View, перевірка актуальності View/Model та перевідображення даних на екрані*/
    public void bindView(@NonNull V view){
        this.view = new WeakReference<>(view);
        if(setupDone()){
            updateView();
        }
    }

    /* метод для відкріплення посилання на View*/
    public void unbindView(){
        this.view = null;
    }

    /* метод для отримання посилання на View якщо вона не знищена GarbageCollector*/
    protected V view() {
        if (view == null) {
            return null;
        } else {
            return view.get();
        }
    }

    /* метод для перевірки актуальності посилань на View(Екран) та модель*/
    protected boolean setupDone() {
        return view() != null && model != null ;
    }


    protected abstract void updateView();

}
