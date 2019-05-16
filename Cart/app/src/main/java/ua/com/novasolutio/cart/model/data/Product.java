package ua.com.novasolutio.cart.model.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Objects;


/* Клас, що описує дані про сутність продукт */
@Entity(tableName = "products")
public class Product {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mID; // ІД об'єкту в БД

    @ColumnInfo(name = "caption")
    private String mCaption; // Назва продукту

    @ColumnInfo(name = "price")
    private int mPrice; // Ціна продукту, зберігається в копійках, наприклад 2000 = 20,00 грн.

    @ColumnInfo(name = "rate")
    private int mRate = 1; // Популярність вибору продукту, чим частіше його додають в корзину - тим вище значення змінної.

    @Ignore
    private int mCount; // кількість одиниць продукту вибраного на View, НЕ зберігати в БД!

    @ColumnInfo(name = "deleted")
    private boolean mDeleted = false; // прапорець для визначення, чи був продукт видалений зі списку для відображення користувачеві

    @Ignore
    public Product() {

    }

    public Product(int mID, String mCaption, int mPrice, int mRate, boolean mDeleted) {
        this.mID = mID;
        this.mCaption = mCaption;
        this.mPrice = mPrice;
        this.mRate = mRate;
        this.mDeleted = mDeleted;
    }

    @Ignore
    public Product(int ID, String caption, int price, int rate, int count, boolean deleted) {
        mID = ID;
        mCaption = caption;
        mPrice = price;
        mRate = rate;
        mCount = count;
        mDeleted = deleted;
    }

    public int getID() {
        return mID;
    }

    public void setID(int mID) {
        this.mID = mID;
    }

    public String getCaption() {
        return mCaption;
    }

    public void setCaption(String mCaption) {
        this.mCaption = mCaption;
    }

    public int getPrice() {
        return mPrice;
    }

    public void setPrice(int mPrice) {
        this.mPrice = mPrice;
    }

    public int getRate() {
        return mRate;
    }

    public void setRate(int mRate) {
        this.mRate = mRate;
    }

    public int getCount() {
        return mCount;
    }

    public void setCount(int mCount) {
        this.mCount = mCount;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Product){
            return ((Product) obj).getCaption().equals(this.getCaption()) && ((Product) obj).getPrice() == this.getPrice();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mID, mCaption);
    }

    @Override
    public String toString() {
        return "Product{" +
                "mID=" + mID +
                ", mCaption='" + mCaption + '\'' +
                ", mPrice=" + mPrice +
                ", mRate=" + mRate +
                ", deleted=" + mDeleted +
                ", count=" + mCount +
                '}';
    }

    public boolean isDeleted() {
        return mDeleted;
    }

    public void setDeleted(boolean deleted) {
        mDeleted = deleted;
    }
}
