package cs1302.lists;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the Box interface.
 */
public class BoxTest {

    Box<String> box = null;

    @BeforeEach
    void init() {
	box = new Box<String>();
    } // init

    @AfterEach
    void tearDown() {
	box = null;
    } // tearDown

    @Test
    void testBox() {
	final String TEST_STRING = "test";
	box.set(TEST_STRING);
	assertEquals(box.get(), TEST_STRING, "get() should return what is set(T)");
    } // testBox

    @Test
    void testBoxArray() {
	Box<String>[] boxes = Box.<String>array(2);
	assertTrue(true, "should be able to make box array");
	boxes[0] = box;
	assertTrue(true, "should be able to assign a box to the array");
    } // testBoxArray

} // BoxTest

