/*
 * TCSS 305 Autumn 2022
 * Assignment 2
 */
package model;
/**
 * This program utilizes Class Item objects to represent a virtual shopping cart.
 * Provides structure to reference Item objects and perform different functions
 * on them, calculating total values for the active cart.
 * 
 * @author Paul Schmidt
 * @version 21 October 2022
*/
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    
    /**
     * A Map to be instantiated as a HashMap to hold Item objects and updated unit totals.
     */
    private final Map<Item, Integer> myCart;
    /**
     * Represents the boolean value of membership status of the user, which will be used to
     * adjust the shopping cart total under certain conditions.
     */
    private boolean myMembership;

    /**
     * Default constructor for ShoppingCart.
     * Initializes myCart to a HashMap and defaults membership status to false.
     */
    public ShoppingCart() {
        myCart = new HashMap<Item, Integer>();
        myMembership = false;

    }

    /**
     * Uses the add() method from Collections to store a (key, value) pair of an Item
     * and a quantity of that Item currently in the shopping cart.
     * Note: If the key is already in the myCart HashMap, the add() method will 
     * replace the previous value to the new theQuantity value.
     * 
     * @param theItem the Item to be added as the key
     * @param theQuantity the quantity to be added as the value
     */
    public void add(final Item theItem, final int theQuantity) {
        myCart.put(theItem, theQuantity);     
    }

    /**
     * Updates the membership status of the ShoppingCart.
     * @param theMembership new boolean value of myMembership
     */
    public void setMembership(final boolean theMembership) {
        myMembership = theMembership;
    }

    /**
     * Uses the myCart HashMap and its (key, value) pairs to calculate the total value
     * of the shopping cart. Also checks to see if the membership is true and the cart
     * value is over $15.00, in which case it implements a 10% discount.
     * @return the total value of the shopping cart as a formatted BigDecimal.
     */
    public BigDecimal calculateTotal() {
        BigDecimal cartTotal = BigDecimal.ZERO;
        final BigDecimal discountThreshold = BigDecimal.valueOf(15.00);
        final BigDecimal discountModifier = BigDecimal.valueOf(0.9);
        //For each Item in the myCart map, perform the following:
        for (final Item currentItem : myCart.keySet()) {
            //Get the quantity of the Item
            final int itemQuantity = myCart.get(currentItem);
            //Get the regular price of the Item
            final BigDecimal itemPrice = currentItem.getPrice();
            //If the Item has a valid bulk quantity and discount
            if (currentItem.isBulk()) {
                //Get bulk price of the Item
                final BigDecimal bulkPrice = currentItem.getBulkPrice();
                //Get quantity required to receive the bulk discount
                final int bulkQuantity = currentItem.getBulkQuantity();
                //Calculate number of times bulk discount can be applied
                final int bulkModifier = itemQuantity / bulkQuantity;
                //Calculate remainder of Items to be charged at regular price
                final int remainder = itemQuantity % bulkQuantity;
                //Calculate and update cartTotal
                cartTotal = 
                        cartTotal.add(bulkPrice.multiply(BigDecimal.valueOf(bulkModifier)));
                cartTotal = 
                        cartTotal.add(itemPrice.multiply(BigDecimal.valueOf(remainder)));
            } else {
                cartTotal = 
                        cartTotal.add(itemPrice.multiply(BigDecimal.valueOf(itemQuantity)));
            }
        }
        //Check to see if membership is enabled and if the shopping cart total
        //is greater than $15.00. If so, apply the discount to the shopping cart total.
        if (myMembership && cartTotal.compareTo(discountThreshold) > 0) {
            cartTotal = cartTotal.multiply(discountModifier);
        }
        //Format the total using HALF_EVEN and scale of 2
        cartTotal = cartTotal.setScale(2, RoundingMode.HALF_EVEN);
        //Return formated total
        return cartTotal;
    }
    /**
     * Clears the myCart HashMap of all Key/Value pairs.
     */
    public void clear() {
        myCart.clear();
    }

}
