//Apar
// Implements Dictionary using Doubly Linked List (DLL)
// Implement the following functions using the specifications provided in the class List

public class A1List extends List {

    private A1List next; // Next Node
    private A1List prev; // Previous Node

    public A1List(int address, int size, int key) {
        super(address, size, key);
    }

    public A1List() {
        super(-1, -1, -1);
        // This acts as a head Sentinel

        A1List tailSentinel = new A1List(-1, -1, -1); // Intiate the tail sentinel

        this.next = tailSentinel;
        tailSentinel.prev = this;
    }

    public A1List Insert(int address, int size, int key) { // Create Node
        A1List ins = new A1List(address, size, key);
        // Assign Links of ins
        ins.next = this.next;
        ins.next.prev = ins;
        // Renew Links of this
        this.next = ins;
        this.next.prev = this;
        // Return New Node
        return ins;
    }

    public boolean Delete(Dictionary d) { // Get Head of List
        A1List temp = this.getFirst();
        // temp!=null [ empty list ] AND temp.next!=null [ tailSentinel ]
        while (temp != null && temp.next != null) {
            // check key THEN pointer-address
            if (temp.key == d.key && temp.address == d.address && temp.size == d.size) {
                // Renew Links of temp.prev
                temp.prev.next = temp.next;
                // Renew Links of temp.next
                temp.next.prev = temp.prev;
                return true;
            }
            // move ahead in list
            temp = temp.next;
        }
        // If d not found in list
        return false;
    }

    public A1List Find(int k, boolean exact) { // Get Head of List
        A1List temp = this.getFirst();
        // temp!=null [ empty list ] AND temp.next!=null [ tailSentinel ]
        while (temp != null && temp.next != null) {
            if (temp.key >= k && !exact) {
                return temp;
            }
            if (temp.key == k && exact) {
                return temp;
            }
            temp = temp.next;
        }
        return null;
    }

    public A1List getFirst() {
        A1List temp = this;
        // Get headSentinel
        while (temp.prev != null) {
            temp = temp.prev;
        }
        // Return head
        return temp.getNext();
    }

    public A1List getNext() {
        if (this.next == null || this.next.next == null) {
            return null;
        }
        return this.next;
    }

    public boolean sanity() {
        try {
            // Circular List
            A1List fast = this, slow = this, tempo = this;
            while (fast != null && fast.next != null) {
                slow = slow.next;
                fast = fast.next.next;
                if (slow == fast) {
                    return false;
                }
            }
            fast = this;
            slow = this;
            while (fast != null && fast.prev != null) {
                slow = slow.prev;
                fast = fast.prev.prev;
                if (slow == fast) {
                    return false;
                }
            }
            // prev of head and next of tail
            while (tempo.prev != null) {
                tempo = tempo.prev;
            }
            if (tempo.address != -1 || tempo.key != -1 || tempo.size != -1) {
                return false;
            }
            while (tempo.next != null) {
                tempo = tempo.next;
            }
            if (tempo.address != -1 || tempo.key != -1 || tempo.size != -1) {
                return false;
            }
            // node.next.prev != node
            A1List temp = getFirst().prev;
            while (temp != null && temp.next != null) {
                if (temp.next.prev != temp) {
                    return false;
                }
                temp = temp.next;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}