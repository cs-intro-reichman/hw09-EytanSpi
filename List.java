/** A linked list of character data objects.
 *  (Actually, a list of Node objects, each holding a reference to a character data object.
 *  However, users of this class are not aware of the Node objects. As far as they are concerned,
 *  the class represents a list of CharData objects. Likwise, the API of the class does not
 *  mention the existence of the Node objects). */
public class List {

    // Points to the first node in this list
    Node first;

    // The number of elements in this list
    private int size;
	
    /** Constructs an empty list. */
    public List() {
        first = null;
        size = 0;
    }

    /** Returns the number of elements in this list. */
    public int getSize() {
 	      return size;
    }

    /** Returns the first element in the list */
    public CharData getFirst() {
        return first.cd;
    }

    /** GIVE Adds a CharData object with the given character to the beginning of this list. */
    public void addFirst(char chr) {
        Node newNode = new Node(new CharData(chr), first);
        first = newNode;
        size++;
    }
    public void add(char chr) {
        if (first == null) {
            addFirst(chr);
        }
        Node newNode = new Node(new CharData(chr), first);
        Node pointer = first;
        while (pointer.next != null) {
            pointer = pointer.next;
        }
        pointer.next = newNode;
        size++;
    }
    
    /** GIVE Textual representation of this list. */
    public String toString() {
        ListIterator list = new ListIterator(first);
        String s = "";
        while (list.hasNext()) {
            s = list.next().toString() + " " + s;
        }
        if (s!=null) {
            s = s.substring(0,s.length()-1); // take off final space ' '
        }
        s = "(" + s + ")";
        return s;
    }

    /** Returns the index of the first CharData object in this list
     *  that has the same chr value as the given char,
     *  or -1 if there is no such object in this list. */
    public int indexOf(char chr) {
        Node pointer = first;
        int counter = 0;
        while (pointer != null) {
            if (pointer.cd.equals(chr)) return counter;
            pointer = pointer.next;
            counter++;
        }
        return -1;
    }

    /** If the given character exists in one of the CharData objects in this list,
     *  increments its counter. Otherwise, adds a new CharData object with the
     *  given chr to the beginning of this list. */
    public void update(char chr) {
        int index = indexOf(chr);
        if (indexOf(chr) == -1) {
            add(chr);
        } else {
            CharData chrD = get(index);
            chrD.count++;
        }

    }

    /** GIVE If the given character exists in one of the CharData objects
     *  in this list, removes this CharData object from the list and returns
     *  true. Otherwise, returns false. */
    public boolean remove(char chr) {
        int index = this.indexOf(chr);
        if (index == -1) return false;
        if (index == 0) {
            this.first = this.first.next;
            size--;
            return true;
        }
        Node pointer = first;
        for (int i = 0; i < index - 1; i++) { // will reach before our wanted index
            pointer = pointer.next;
        }
        pointer.next = pointer.next.next;
        size--;
        return true;
    }

    /** Returns the CharData object at the specified index in this list. 
     *  If the index is negative or is greater than the size of this list, 
     *  throws an IndexOutOfBoundsException. */
    public CharData get(int index) {
        if (index < 0 || size <= index) {
            throw new IndexOutOfBoundsException(index + " is out of bounds."); 
        }
        Node pointer = first;
        for (int i = 0; i < index; i++) { // will reach our wanted index
            pointer = pointer.next;
        }
        return pointer.cd;

    }

    /** Returns an array of CharData objects, containing all the CharData objects in this list. */
    public CharData[] toArray() {
	    CharData[] arr = new CharData[size];
	    Node current = first;
	    int i = 0;
        while (current != null) {
    	    arr[i++]  = current.cd;
    	    current = current.next;
        }
        return arr;
    }

    /** Returns an iterator over the elements in this list, starting at the given index. */
    public ListIterator listIterator(int index) {
	    // If the list is empty, there is nothing to iterate   
	    if (size == 0) return null;
	    // Gets the element in position index of this list
	    Node current = first;
	    int i = 0;
        while (i < index) {
            current = current.next;
            i++;
        }
        // Returns an iterator that starts in that element
	    return new ListIterator(current);
    }
}
