// Class: A1DynamicMem
// Implements DynamicMem
// Does not implement defragment (which is for A2).

public class A1DynamicMem extends DynamicMem {

    public A1DynamicMem() {
        super();
    }

    public A1DynamicMem(int size) {
        super(size);
    }

    public A1DynamicMem(int size, int dict_type) {
        super(size, dict_type);
    }

    public void Defragment() {
        return;
    }

    // In A1, you need to implement the Allocate and Free functions for the class
    // A1DynamicMem
    // Test your memory allocator thoroughly using Doubly Linked lists only
    // (A1List.java).

    public int Allocate(int blockSize) {
        Dictionary temp = this.freeBlk.Find(blockSize, false);
        if (blockSize <= 0 || temp == null) {
            return -1;
        }
        boolean split = false;
        int st_adr = temp.address;
        if (temp.size > blockSize) {
            split = true;
        }
        if (split) {
            this.allocBlk.Insert(st_adr, blockSize, st_adr);
            this.freeBlk.Insert(st_adr + blockSize, temp.size - blockSize, temp.key - blockSize);
            this.freeBlk.Delete(temp);
        } else {
            this.allocBlk.Insert(st_adr, blockSize, st_adr);
            this.freeBlk.Delete(temp);
        }
        return st_adr;
    }

    public int Free(int startAddr) {
        Dictionary temp = this.allocBlk.Find(startAddr, true);
        if (temp == null) {
            return -1;
        }
        this.freeBlk.Insert(temp.address, temp.size, temp.size);
        this.allocBlk.Delete(temp);
        return 0;
    }
}
