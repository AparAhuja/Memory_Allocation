// Class: A2DynamicMem
// Implements Degragment in A2. No other changes should be needed for other functions.

public class A2DynamicMem extends A1DynamicMem {
      
    public A2DynamicMem() {  super(); }

    public A2DynamicMem(int size) { super(size); }

    public A2DynamicMem(int size, int dict_type) { super(size, dict_type); }

    
public void Defragment() {
    if(this.type==3){
        Dictionary temp = this.freeBlk.getFirst();
    AVLTree newbst = new AVLTree(), tempnew;
    while(temp!=null){
        newbst.Insert(temp.address, temp.size, temp.address);
        temp=temp.getNext();
    }
    tempnew = newbst.getFirst();
    while(tempnew!=null){
        AVLTree next = tempnew.getNext();
        if(next!=null && tempnew.address+tempnew.size == next.address){
            AVLTree temp1 = new AVLTree(tempnew.address,tempnew.size,tempnew.key);
            AVLTree temp2 = new AVLTree(next.address,next.size,next.key);
            newbst.Delete(temp1);
            newbst.Delete(temp2);
            tempnew = newbst.Insert(temp1.address, temp1.size+temp2.size, temp1.address);
        }
        else{tempnew = tempnew.getNext();}}
        this.freeBlk=new AVLTree();
        BSTree xyz = newbst.getFirst();
        while(xyz!=null){
            freeBlk.Insert(xyz.address, xyz.size, xyz.size);
            xyz=xyz.getNext();
        }
    return;
    }
    Dictionary temp = this.freeBlk.getFirst();
    BSTree newbst = new BSTree(), tempnew;
    while(temp!=null){
        newbst.Insert(temp.address, temp.size, temp.address);
        temp=temp.getNext();
    }
    tempnew = newbst.getFirst();
    while(tempnew!=null){
        BSTree next = tempnew.getNext();
        if(next!=null && tempnew.address+tempnew.size == next.address){
            BSTree temp1 = new BSTree(tempnew.address,tempnew.size,tempnew.key);
            BSTree temp2 = new BSTree(next.address,next.size,next.key);
            newbst.Delete(temp1); newbst.Delete(temp2);
            tempnew = newbst.Insert(temp1.address, temp1.size+temp2.size, temp1.address);
        }
        else{tempnew = next;}}
        this.freeBlk=new BSTree();
        BSTree xyz = newbst.getFirst();
        while(xyz!=null){
            freeBlk.Insert(xyz.address, xyz.size, xyz.size);
            xyz=xyz.getNext();
        }
}
}