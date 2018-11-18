package cs1302.lists;

import java.util.Iterator;

public class ArrayGenList<T> implements GenList<T> {

    protected Box<T>[] boxes;
    
    /**
     * Constructor for the ArrayGenList.
     * initializes boxes as a Box<T> array with 15 spaces
     */
    public ArrayGenList() {
	boxes = Box.<T>array(15);
    }


    /**
     * Adds the specified element to the list at an implementation-specific
     * position.
     * @param elem element to be inserted
     * @throws NullPointerException if elem is null
     */
    public void add(T elem) throws NullPointerException {
	if(elem == null) {
	    throw new NullPointerException("Element cannot be null");
	}
	expandArrayIfNeeded(5);

	for(int i = 0; i < boxes.length; i++) {
	    if(boxes[i] == null) {
		Box<T> newElemBox = new Box<T>(elem); //stores elem inside a box
		boxes[i] = newElemBox;
		break;
	    }
	}
    }

    /**
     * Inserts the specified element at the specified position in this list. 
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).
     * @param index index at which the specified element is to be inserted
     * @param elem  element to be inserted
     * @throws NullPointerException if elem is null
     * @throws IndexOutOfBoundsException if the index is out of range 
     *         {@code (index < 0 || index > size())}
     */
    public void add(int index, T elem) throws NullPointerException, IndexOutOfBoundsException {

	if(elem == null) {
	    throw new NullPointerException("Element cannot be null");
	}

	if(index < 0 || index > size()) {
	    throw new IndexOutOfBoundsException("Index out of bounds");
	}
	
	int currentSize = size();
	for(int i = currentSize - 1; i > index; i--) {
	    boxes[i] = boxes[i-1];
	}
	
	Box<T> newElemBox = new Box<T>(elem);
	boxes[index] = newElemBox;
    }

    /** 
     * Removes all of the elements from this list. The list will be empty after 
     * this call returns.
     */
    public void clear() {
	int currentSize = size();
	for(int i = 0; i < currentSize; i++) {
	    boxes[i] = null;
	}
    }

    /** 
     * Compares the specified list with this list for equality. Returns 
     * <code>true</code> if and only if the specified object is also a 
     * <code>GenList</code>, both lists have the same size, and all corresponding 
     * pairs of elements in the two lists are equal.
     * If <code>list</code> is a list of the same list type, then students
     * may safely cast the object to the appropriat list type when implementing
     * this method.
     * @param list the object to be compared for equality with this list
     * @return <code>true</code> if the specified object is equal to this list
     */
    public boolean equals(Object list) {
	
	if(!(list instanceof GenList)) {
	    System.out.println("type 1");
	    return false;
	}
	GenList newCasted = (GenList) list;
      	if(newCasted.size() != size()) {
	    System.out.println("type 2");
	    return false;
	}
	
	for(int i = 0; i < size(); i++) {
	    if(!(newCasted.get(i).equals(get(i)))) {
		System.out.println("type 3. " + newCasted.get(i) + " does not equal " + get(i));
		return false;
	    }	    
	}
	return true;
    }

    /**
     * Returns the element at the specified position in this list.
     * @param index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of range
     *         {@code (index < 0 || index >= size())}
     */
    public T get(int index) throws IndexOutOfBoundsException {
	if(index < 0 || index >= size()) {
	    throw new IndexOutOfBoundsException("Index is out of bounds");
	}
	return boxes[index].get();
    }

    /**
     * Replaces the element at the specified position in this list with the 
     * specified element
     * @param index index of the element to replace
     * @param elem element to be stored at the specified position
     * @return the element previously at the specified position
     * @throws NullPointerException if the specified element is null
     * @throws IndexOutOfBoundsException if the index is out of range
     *         {@code (index < 0 || index >= size())}
     */
    public T set(int index, T elem) throws NullPointerException, IndexOutOfBoundsException {

	if(elem == null) {
	    throw new NullPointerException("Element cannot be null");
	}
	if(index < 0 || index >= size()) {
	    throw new IndexOutOfBoundsException("Index is out of bounds");
	}

	T previousElem = boxes[index].get();   
	Box<T> newBox = new Box<T>(elem);   // instantiate Box object with elem inside
	boxes[index] = newBox;              // add Box object to array at index
	return previousElem;
    }

    /**
     * Returns the number of elements in this list. If this list contains more 
     * than <code>Integer.MAX_VALUE</code> elements, return 
     * <code>Integer.MAX_VALUE</code>.
     * @return the number of elements in this list
     */
    public int size() {
	int counter = 0;
	for(int i = 0; i < boxes.length; i++) {
	    if(boxes[i] != null) {
		counter++;
	    }
	    else { break; }  //can stop counting after first null element 
	}                    //because rest of elements are null
	if(counter > Integer.MAX_VALUE) {
	    return Integer.MAX_VALUE;
	}
	return counter;
    }

    /**
     * Returns true if and only if this list contains no elements.
     * @return <code>true</code> if this list contains no elements
     */ 
    public boolean isEmpty() {
	for(int i = 0; i < boxes.length; i++) {
	    if(boxes[i] != null) {
		return false;
	    }
	}
	return true;
    }

    /**
     * Returns <code>true</code> if this list contains the specified element. 
     * More formally, returns <code>true</code> if and only if this list 
     * contains at least one element <code>o</code> such that 
     * <code>o.equals(elem)</code>.
     * @param elem element whose presence in this list is to be tested
     * @return <code>true</code> if this list contains the specified element
     * @throws NullPointerException if elem is null
     */
    public boolean contains(T elem) throws NullPointerException {
	if(elem == null) {
	    throw new NullPointerException("element cannot be null");
	}
	int currentSize = size();
	for(int i = 0; i < currentSize; i++) {
	    if(elem.equals(boxes[i].get())) {
		return true;      //can go ahead and return once the first match is found
	    }
	}
	return false;
    }
	
    /** 
     * Removes the first occurrence of the specified element from this list, if 
     * it is present. If this list does not contain the element, it is 
     * unchanged. More formally, removes the element with the lowest index 
     * <code>i</code> such that <code>elem.equals(get(i))</code> (if such an 
     * element exists). Returns <code>true</code> if this list contained the 
     * specified element (or equivalently, if this list changed as a result of 
     * the call).
     * @param elem element to be removed from this list, if present
     * @return <code>true</code> if this list contained the specified element
     * @throws NullPointerException if elem is null
     */
    public boolean remove(T elem) throws NullPointerException {
	if(elem == null) {
	    throw new NullPointerException("Element can't be null");
	}

	int removeIndex = -1;
	int currentSize = size();
	for(int i = 0; i < currentSize; i++) {    //find index of first match
	    if(elem.equals(boxes[i].get())) {
		removeIndex = i;
		break;
	    }
	} //for

	if(removeIndex == -1) {   //if no match found, stop now. Return false.
	    return false;
	} //if
	
	for(int i = removeIndex; i < currentSize - 1; i++) {
	    boxes[i] = boxes[i+1];  //shift everything left starting at the removed index
	}                           //thereby overwriting that index
	boxes[currentSize - 1] = null;
	return true;    
    }

    /**
     * Returns the index of the first occurrence of the specified element in 
     * this list, or -1 if this list does not contain the element.
     * @param elem element to search for
     * @return the index of the first occurrence of the specified element in 
     *         this list, or -1 if this list does not contain the element
     * @throws NullPointerException if elem is null
     */
    public int indexOf(T elem) throws NullPointerException {

	if(elem == null) {
	    throw new NullPointerException("element cannot be null");
	}
	for(int i = 0; i < boxes.length; i++) {
	    if(boxes[i] != null && elem.equals(boxes[i].get())) {
		return i;
	    }  //if first part of the condition is false, short-circuit evaluation
	}      //prevents evaluation of second part, which would throw a NullPointerException
	return -1;
    }
    
    /**
     * Method that checks if the internal array boxes is full
     * and adds {int byHowMuch} spaces if it is full.
     */
    public void expandArrayIfNeeded(int byHowMuch) {
	if(size() == boxes.length) {
	    Box<T>[] temp = Box.<T>array(boxes.length + byHowMuch);
	              //make new temp Box<T>[] that is {byHowMuch} spaces bigger  
	    for(int i = 0; i < boxes.length; i++) {
		temp[i] = boxes[i];
	    }        //copy all elements into that array
	    boxes = temp;
                  //assign that temp array to boxes instance variable
	}
    }

} // ArrayGenList<T>