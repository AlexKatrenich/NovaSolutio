package ua.com.novasolutio.cart.presenters;

import ua.com.novasolutio.cart.data.Product;
import ua.com.novasolutio.cart.views.ProductView;

public class AddChangeProductActivityPresenter extends BasePresenter<Product, ProductView> {


    @Override
    protected void updateView() {
        if(setupDone()){
            view().setProductCaption(model.getCaption());
            view().setProductPrice(formatPriceOnText(model.getPrice()));
        }
    }

    @Override
    public String formatPriceOnText(int price) {
        StringBuffer priceString = new StringBuffer(String.valueOf(price));
        switch (priceString.length()){
            case 0 :
                priceString.append("0.00");
                break;
            case 1:
                priceString.insert(0, "0.0");
                break;
            case 2:
                priceString.insert(0, "0.");
                break;
            default:
                priceString.insert(priceString.length() - 2, '.');
        }

        return priceString.toString();
    }
}
