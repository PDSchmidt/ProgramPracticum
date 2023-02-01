/*
 * TCSS 305 Autumn 2022
 * Assignment 2
 */
package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import model.Item;
import model.ShoppingCart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This class performs JUnit tests to provide code coverage of the ShoppingCart class.
 * @author Paul Schmidt
 * @version 21 October 2022
 */
class ShoppingCartTest {

    /**
     * A Default ShoppingCart for use in testing.
     */
    private ShoppingCart myTestCart;
    /**
     * Item needed to test functionality of myTestCart.
     */
    private Item myItem1;
    /**
     * Item needed to test functionality of myTestCart.
     */
    private Item myItem2;
    /**
     * Freshly instantiates the myTestCart before each test.
     */
    @BeforeEach
    void setUp() {
        myTestCart = new ShoppingCart();
        myItem1 = new Item("Banana", BigDecimal.valueOf(1.25));
        myItem2 = new Item("Orange", BigDecimal.valueOf(1.49), 4, BigDecimal.valueOf(3.99));
    }
    /**
     * Test method for {@link model.ShoppingCart#calculateTotal()}.
     * Tests a Shopping Cart total with a single item added, then clear the cart
     * and test that the total becomes 0.00.
     */
    @Test
    void testCalculateTotalSingleItem() {
        myTestCart.add(myItem1, 1);
        assertEquals(myTestCart.calculateTotal(), BigDecimal.valueOf(1.25));
        myTestCart.clear();
        assertEquals(myTestCart.calculateTotal(), BigDecimal.ZERO.setScale(2));
    }
    /**
     * Test method for {@link model.ShoppingCart#calculateTotal()}.
     * Tests a Shopping Cart total with bulk items added and valid bulk quantities.
     * Coincidentally tests the default state of membership equal to False.
     */
    @Test
    void testCalculateTotalBulkItem() {
        myTestCart.add(myItem1, 1);
        myTestCart.add(myItem2, 3);
        assertEquals(myTestCart.calculateTotal(), BigDecimal.valueOf(5.72).setScale(2));
        myTestCart.add(myItem2, 20);
        assertEquals(myTestCart.calculateTotal(), BigDecimal.valueOf(21.20).setScale(2));
    }
    /**
     * Test method for {@link model.ShoppingCart#calculateTotal()}.
     * Tests a Shopping Cart total with membership being True. Tests that discount is applied
     * for totals over $15.00 but not $15.00 and under.
     */
    @Test
    void testCalculateTotalBulkMembershipTrue() {
        myTestCart.setMembership(true);
        myTestCart.add(myItem1, 1);
        myTestCart.add(myItem2, 3);
        assertEquals(myTestCart.calculateTotal(), BigDecimal.valueOf(5.72).setScale(2));
        myTestCart.add(myItem2, 20);
        assertEquals(myTestCart.calculateTotal(), BigDecimal.valueOf(19.08).setScale(2));
    }
}
