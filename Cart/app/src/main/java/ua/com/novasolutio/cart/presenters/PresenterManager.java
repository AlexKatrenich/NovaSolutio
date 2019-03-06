package ua.com.novasolutio.cart.presenters;


import android.os.Bundle;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/* Singleton для управлінням списком презентерів*/
public class PresenterManager {
    private static final String SIS_KEY_PRESENTER_ID = "presenter_id";
    private static PresenterManager instance;

    private final AtomicLong currentId;

    private final Cache<Long, BasePresenter<?, ?>> presenters;


    PresenterManager(long maxSize, long expirationValue, TimeUnit expirationUnit){
        currentId = new AtomicLong();

        presenters = CacheBuilder.newBuilder()
                .maximumSize(maxSize)
                .expireAfterWrite(expirationValue, expirationUnit)
                .build();
    }

    public static PresenterManager getInstance(){
        if (instance == null) {
            instance = new PresenterManager(20, 30, TimeUnit.SECONDS);
        }

        return instance;
    }

    /* метод для відновлення посилання на презентера, наприклад при втраті на нього посилання(поворот екрану, скрол в RecyclerView)*/
    public <P extends BasePresenter<?, ?>> P restorePresenter(Bundle savedInstanceState){
        Long presenterId = savedInstanceState.getLong(SIS_KEY_PRESENTER_ID); // отримання Ід презентера
        P presenter = (P) presenters.getIfPresent(presenterId); // запис посилання на об'єкт презентера
        presenters.invalidate(presenterId); // оновлення Кеш-у по відповідному значенню ІД презентера, так як не використовуваний кеш з часом чититься
        return presenter;
    }

    /* метод для зберігання посилання на презентера*/
    public void savePresenter(BasePresenter<?, ?> presenter, Bundle outState){
        long presenterId = currentId.incrementAndGet(); // генерація ІД презентера
        presenters.put(presenterId, presenter); // запис посилання на об'єкт презентера в Кеш
        outState.putLong(SIS_KEY_PRESENTER_ID, presenterId); // зберігання посилання на ІД презентера в Кеші
    }

}
