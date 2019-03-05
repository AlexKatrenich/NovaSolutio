package ua.com.novasolutio.cart.data;

import java.util.Objects;


/* Клас, що описує дані про сутність продукт */

public class Product {
    private int mID;
    private String mCaption;
    private int mPrice;
    private int mRate;

    public Product() {

    }

    public Product(int mID, String mCaption, int mPrice, int mRate) {
        this.mID = mID;
        this.mCaption = mCaption;
        this.mPrice = mPrice;
        this.mRate = mRate;
    }

    public Product(int mID, String mCaption) {
        this.mID = mID;
        this.mCaption = mCaption;
    }

    public int getmID() {
        return mID;
    }

    public void setmID(int mID) {
        this.mID = mID;
    }

    public String getmCaption() {
        return mCaption;
    }

    public void setmCaption(String mCaption) {
        this.mCaption = mCaption;
    }

    public int getmPrice() {
        return mPrice;
    }

    public void setmPrice(int mPrice) {
        this.mPrice = mPrice;
    }

    public int getmRate() {
        return mRate;
    }

    public void setmRate(int mRate) {
        this.mRate = mRate;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Product)
            return this.getmID() == ((Product) obj).getmID();

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mID, mCaption);
    }
}
