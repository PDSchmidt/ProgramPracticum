/*
 * TCSS 305 Autumn 2022
 * Assignment 2
 */

package model;
/**
 * This program represents Item objects that are used in the ShoppingCart Class to
 * simulate a virtual store front.
 * 
 * @author Paul Schmidt
 * @version 21 October 2022
*/
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

public final class Item {
    //Static fields
    
    /**
     * NumberFormatter for all Items to format prices for use in the toString() method.
     */
    private static final NumberFormat NF = NumberFormat.getCurrencyInstance(Locale.US);
    
    //Instance fields.
    
    /**
     * Represents the name of the Item.
     */
    private final String myName;
    /**
     * Represents the regular price of the Item.
     */
    private final BigDecimal myPrice;
    /**
     * Represents the quantity required of the Item to obtain the bulk discount.
     */
    private final int myBulkQuantity;
    /**
     * Represents the price of the Item when the myBulkQuantity is met.
     */
    private final BigDecimal myBulkPrice;
    /**
     * Constructor used when the Item has no bulk discount.
     * Sets the myBulkQuantity to -1 and myBulkPrice to -1.00 in order to 
     * initialize the fields for use in other methods that perform different
     * functions based on whether an Item has a valid bulk pricing or not.
     * @param theName name of the Item
     * @param thePrice price per unit of the Item
     */
    public Item(final String theName, final BigDecimal thePrice) {
        myName = theName;
        myPrice = thePrice;
        myBulkQuantity = -1;
        myBulkPrice = BigDecimal.valueOf(-1.00);

    }

    /**
     * Constructor used when the Item has a bulk discount.
     * @param theName name of the Item
     * @param thePrice price per unit of the Item
     * @param theBulkQuantity the amount of units required to receive the bulk discount price
     * @param theBulkPrice the bulk discount price of the Item
     */
    public Item(final String theName, final BigDecimal thePrice,
                final int theBulkQuantity, final BigDecimal theBulkPrice) {
        myName = theName;
        myPrice = thePrice;
        myBulkPrice = theBulkPrice;
        myBulkQuantity = theBulkQuantity;

    }

    /**
     * Returns the name of the Item as a String.
     * @return the name of the Item
     */
    public String getName() {
        
        return myName;
    }
    /**
     * Returns the price per unit of the Item as a BigDecimal.
     * @return the price of the Item
     */
    public BigDecimal getPrice() {
        
        return myPrice;
    }
    
    /**
     * Returns the amount of units required to receive the bulk discount as a primitive int.
     * @return the bulk quantity
     */
    public int getBulkQuantity() {
        
        return myBulkQuantity;
    }
    
    /**
     * Returns the price per bulk quantity of the Item as a BigDecimal.
     * @return the bulk price
     */
    public BigDecimal getBulkPrice() {
        
        return myBulkPrice;
    }

    /**
     * Returns the boolean value that tracks if this Item has a bulk discount or not.
     * If this Item used the (String, BigDecimal) constructor, the bulkPrice would 
     * be set to null, otherwise it would be set to a BigDecimal.
     * @return whether myBulkQuantity is greater than 0.
     */
    public boolean isBulk() {
        return myBulkQuantity > 0;
    }
    /**
     * Overrides the default toString() method by using a StringBuilder object
     * and the instance fields of Item to provide a String description of the Item.
     * If the Item has a bulk discount, additional information will be included.
     * Formats any BigDecimal price to USD using the static NumberFormat object 
     * available to all Item instances.
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(128);
        builder.append(getName());
        builder.append(", ");
        builder.append(NF.format(getPrice()));
        if (isBulk()) {
            builder.append(" (");
            builder.append(getBulkQuantity());
            builder.append(" for ");
            builder.append(NF.format(getBulkPrice()) + ")");
        }
        return builder.toString();
    }

    /**
     * Overrides the equals() method to test if an Item object is equal to another object.
     * @return boolean value for equality of this Item to another object
     */
    @Override
    public boolean equals(final Object theOther) {
        //Check if both objects refer to the same memory address
        if (this == theOther) {
            return true;
        }
        //Check if theOther object is null
        if (theOther == null) {
            return false;
        }
        //Check if theOther is an instance of class Item
        if (!(theOther instanceof Item)) {
            return false;
        }
        //Cast theOther into class Item
        final Item otherItem = (Item) theOther;
        //Check if both Items have the same name
        if (!(myName.equals(otherItem.getName()))) {
            return false;
        }
        //Check if both Items have the same price
        if (!(myPrice.equals(otherItem.getPrice()))) {
            return false;
        }
        //Check if one Item has a bulk discount and the other doesn't
        if (isBulk() != otherItem.isBulk()) {
            return false;
        }
        //Check if they have the same bulk price
        if (!(getBulkPrice().equals(otherItem.getBulkPrice()))) {
            return false;
        }
        //Check if they have the same bulk quantity
        if (!(getBulkQuantity() == otherItem.getBulkQuantity())) {
            return false;
        }
        //Both Items have the same values for all fields at this point, 
        //so they are considered equal
        return true;
    }

    /**
     * Uses the Objects.hash() method to override the default hashcode() method.
     * If an object has a bulk discount, includes myBulkQuantity and myBulkPrice fields into
     * the Objects.hash() method. Otherwise, only uses the myName and MyPrice fields.
     */
    @Override
    public int hashCode() {
        if (isBulk()) {
            return Objects.hash(myName, myPrice, myBulkQuantity, myBulkPrice);
        }
        return Objects.hash(myName, myPrice);
    }

}
