/*
 * TCSS 305 Autumn 2022
 * Assignment 2
 */
package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.math.BigDecimal;
import java.util.Objects;
import model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the methods in the Item Class using JUnit test cases.
 * @author Paul Schmidt
 * @version 21 October 2022
 */
class ItemTest {
    
    /**
     * A default price for use in constructing multiple Items.
     */
    private BigDecimal myDefaultPrice;
    /**
     * a default bulk price for use in constructing multiple Items.
     */
    private BigDecimal myDefaultBulkPrice;
    /**
     * A default Item that uses the non-bulk Item constructor.
     */
    private Item myDefaultItem;
    /**
     * a default bulk Item that uses the bulk Item constructor.
     */
    private Item myBulkItem;
    
    /**
     * Method used to initialize the default fields before each new test.
     */
    @BeforeEach
    void setUp() {
        myDefaultPrice = BigDecimal.valueOf(1.94);
        myDefaultBulkPrice = BigDecimal.valueOf(4.00);
        myDefaultItem = new Item("Banana", myDefaultPrice);
        myBulkItem = new Item("Orange", myDefaultPrice, 4, myDefaultBulkPrice);
        
    }

    /**
     * Test method for {@link model.Item#hashCode()}.
     */
    @Test
    void testHashCode() {
        assertEquals(myDefaultItem.hashCode(), Objects.hash("Banana", myDefaultPrice));
        assertEquals(myBulkItem.hashCode(), 
                Objects.hash("Orange", myDefaultPrice, 4, myDefaultBulkPrice));
    }

    /**
     * Test method for {@link model.Item#Item(java.lang.String, java.math.BigDecimal)}.
     */
    @Test
    void testItemStringBigDecimal() {
        assertEquals(myDefaultItem.getName(), "Banana");
        assertEquals(myDefaultItem.getPrice(), myDefaultPrice);
        assertEquals(myDefaultItem.getBulkPrice(), BigDecimal.valueOf(-1.00));
        assertEquals(myDefaultItem.getBulkQuantity(), -1);
    }

    /**
     * Test method for {@link model.Item#Item(java.lang.String, 
     * java.math.BigDecimal, int, java.math.BigDecimal)}.
     */
    @Test
    void testItemStringBigDecimalIntBigDecimal() {
        assertEquals(myBulkItem.getName(), "Orange");
        assertEquals(myBulkItem.getPrice(), myDefaultPrice);
        assertEquals(myBulkItem.getBulkQuantity(), 4);
        assertEquals(myBulkItem.getBulkPrice(), myDefaultBulkPrice);
    }

    /**
     * Test method for {@link model.Item#isBulk()}.
     */
    @Test
    void testIsBulk() {
        assertEquals(myDefaultItem.isBulk(), false);
        assertEquals(myBulkItem.isBulk(), true);
    }

    /**
     * Test method for {@link model.Item#toString()}.
     */
    @Test
    void testToString() {
        final String testDefaultItemName = "Banana, $1.94";
        final String testBulkItemName = "Orange, $1.94 (4 for $4.00)";
        assertEquals(myDefaultItem.toString(), testDefaultItemName);
        assertEquals(myBulkItem.toString(), testBulkItemName);
    }

    /**
     * Test method for {@link model.Item#equals(java.lang.Object)}.
     * This method thoroughly tests the equals method to provide total code coverage
     * as well as testing the Reflexive and Symmetric properties of the equals() method.
     */
    @Test
    void testEqualsObject() {
        //Additional Items needed to provide full code coverage for the equals method
        final Item defaultItem2 = new Item("Banana", myDefaultPrice);
        final Item defaultItem3 = new Item("Orange", myDefaultPrice);
        final Item defaultItem4 = new Item("Banana", BigDecimal.valueOf(1.29));
        final Item nullItem = null;
        final Item bulkItem2 = new Item("Orange", myDefaultPrice, 3, myDefaultBulkPrice);
        final Item bulkItem3 = new Item("Orange", myDefaultPrice, 4, BigDecimal.valueOf(5.00));
        
        //Null test
        assertFalse(myDefaultItem.equals(nullItem));
        //Instance Of test
        assertFalse(myDefaultItem.equals(BigDecimal.valueOf(1.8)));
        //Same name, different defaultPrice test
        assertFalse(myDefaultItem.equals(defaultItem4));
        //Different name test
        assertFalse(myDefaultItem.equals(myBulkItem));
        //Testing Reflexive property
        assertEquals(myDefaultItem.equals(myDefaultItem), true);
        //Testing Symmetry Property
        assertEquals(myDefaultItem.equals(defaultItem2), true);
        assertEquals(defaultItem2.equals(myDefaultItem), true);
        //Testing bulk vs non-bulk Items
        assertFalse(myBulkItem.equals(defaultItem3));
        //Testing different bulk quantities
        assertFalse(myBulkItem.equals(bulkItem2));
        //Testing different bulk pricing
        assertFalse(myBulkItem.equals(bulkItem3));
    }

}
