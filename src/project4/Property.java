
package project4;

import java.util.Enumeration;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


public class Property implements StateChangeable {
    
    
    private SimpleStringProperty adress;
    private SimpleIntegerProperty bedrooms;
    private SimpleIntegerProperty square;
    private SimpleDoubleProperty price;
    private String statu = Status.FOR_SALE.toString();
    
    
    public Property(String adress,int bedrooms,int square,double price)
    {
        this.adress = new SimpleStringProperty(adress);
        this.bedrooms = new SimpleIntegerProperty(bedrooms);
        this.square = new SimpleIntegerProperty(square);
        this.price = new SimpleDoubleProperty(price);
    }


    public String getAdress() {
        return adress.get();
    }

    public int getBedrooms() {
        return bedrooms.get();
    }

    public int getSquare() {
        return square.get();
    }

    public double getPrice() {
        return price.get();
    }
    public String getStaut()
    {
        return statu;
    }

    @Override
    public void changeState(Object e) {
        this.statu = (String) e;
    }

    @Override
    public void changeState(Enumeration enumeration) {
        this.statu = enumeration.toString();
    }
    
    
}
