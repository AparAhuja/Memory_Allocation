// Class: Implementation of BST in A2
// Implement the following functions according to the specifications provided in Tree.java

public class BSTree extends Tree {

    private BSTree left, right; // Children.
    private BSTree parent; // Parent pointer.

    public BSTree() {
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node!.
        // CONVENTION: Assume right child of the sentinel node holds the actual root!
        // and left child will always be null.
    }

    public BSTree(int address, int size, int key) {
        super(address, size, key);
    }

    private BSTree goToSentinel(BSTree x) {
        BSTree temp = x ;
        while (temp.parent != null) {
            temp = temp.parent;
        }
        return temp;
    }

    public BSTree Insert(int address, int size, int key) {
        BSTree ins = new BSTree(address, size, key), temp = this.goToSentinel(this);
        if (temp.right == null) {
            temp.right = ins;
            ins.parent = temp;
            return ins;
        }
        temp = temp.right; // assert : temp = root
        while (true) {
            if (key > temp.key) { // Insert to right
                if (temp.right == null) {
                    temp.right = ins;
                    ins.parent = temp;
                    return ins;
                }
                temp = temp.right;
            } else if (key < temp.key) {// Insert to right
                if (temp.left == null) {
                    temp.left = ins;
                    ins.parent = temp;
                    return ins;
                }
                temp = temp.left;
            } else {// Equal keys, need to check address
                if (address <= temp.address) {// go left
                    if (temp.left == null) {
                        temp.left = ins;
                        ins.parent = temp;
                        return ins;
                    }
                    temp = temp.left;
                } else {// go right
                    if (temp.right == null) {
                        temp.right = ins;
                        ins.parent = temp;
                        return ins;
                    }
                    temp = temp.right;
                }
            }
        }
    }

    private void copy(BSTree a, BSTree b) {
        a.address = b.address;
        a.key = b.key;
        a.size = b.size;
    }

    private void deleteLeaf(BSTree temp) {
        if (temp.parent.right == temp) {
            temp.parent.right = null;
        } else {
            temp.parent.left = null;
        }
        temp.parent = null;
    }

    private void deleteOneChild(BSTree temp, BSTree child) {
        if (temp.parent.right == temp) {
            temp.parent.right = child;
        } else {
            temp.parent.left = child;
        }
        child.parent = temp.parent;
    }

    private void deleteInternal(BSTree temp) {
        BSTree tempo = temp.getNext();
        copy(temp, tempo);
        temp = tempo;
        if (temp.left == null && temp.right == null) {
            deleteLeaf(temp);
        } else if (temp.left == null) {
            deleteOneChild(temp, temp.right);
        } else {
            deleteOneChild(temp, temp.left);
        }
    }

    public boolean Delete(Dictionary e) {
        if(e==null){return false;}
        BSTree temp = this.goToSentinel(this);
        if (temp.right == null) {// System.out.println("Empty tree");
            return false;
        }
        temp = temp.right; // assert : temp = root
        while (true) {
            if (e.key > temp.key) {
                if (temp.right == null) {// System.out.println("Not Found 1");
                    return false;
                }
                temp = temp.right;
            } else if (e.key < temp.key) {
                if (temp.left == null) {// System.out.println("Not Found 1");
                    return false;
                }
                temp = temp.left;
            } else {
                if (temp.address == e.address && temp.size == e.size) {
                    if (temp.left == null && temp.right == null) {// System.out.println("Found " + temp.key);
                        deleteLeaf(temp);
                        return true;
                    } else if (temp.left == null) {// System.out.println("Found " + temp.key);
                        deleteOneChild(temp, temp.right);
                        return true;
                    } else if (temp.right == null) {// System.out.println("Found " + temp.key);
                        deleteOneChild(temp, temp.left);
                        return true;
                    } else {// System.out.println("Found " + temp.key);
                        deleteInternal(temp);
                        return true;
                    }
                } else if (e.address <= temp.address) {
                    if (temp.left == null) {
                        return false;
                    }
                    temp = temp.left;
                } else {
                    if (temp.right == null) {
                        return false;
                    }
                    temp = temp.right;
                }
            }
        }
    }

    private BSTree findHelper(BSTree root, int key, boolean exact) {
        if (root == null) {
            return null;
        }
        if (key > root.key) {
            return findHelper(root.right, key, exact);
        } else if (key < root.key) {
            BSTree ans = findHelper(root.left, key, exact);
            if (!exact) {
                return (ans == null) ? root : ans;
            } else {
                return ans;
            }
        } else {
            BSTree b = findHelper(root.left, key, exact);
            return (b == null) ? root : b;
        }
    }

    public BSTree Find(int key, boolean exact) {
        BSTree temp = this.goToSentinel(this);
        if (temp.right == null) {// System.out.println("Empty Tree");
            return null;
        }
        temp = temp.right; // assert : temp = root
        return findHelper(temp, key, exact);
    }

    public BSTree getFirst() {
        BSTree temp = this.goToSentinel(this);
        if (temp.right == null) {
            return null;
        }
        temp = temp.right; // assert : temp = root
        while (temp.left != null) {
            temp = temp.left;
        }
        return temp;
    }

    public BSTree getNext() {
        if (this.parent == null) {
            return null;
        } // Sentinel
        if (this.right != null) {
            BSTree ans = this.right;
            while (ans.left != null) {
                ans = ans.left;
            }
            return ans;
        }
        BSTree p = this.parent, temp = this;
        while (p.parent != null && p.left != temp) {
            p = p.parent;
            temp = temp.parent;
        }
        if (p.parent == null) {
            return null;
        }
        return p;
    }

    private boolean cycle(BSTree root) {
        if (root == null) {
            return true;
        }
        if (root.left != null && root.right != null && root.left == root.right) {
            return false;
        }
        if (root.left != null && root.left.parent != root) {
            return false;
        }
        if (root.right != null && root.right.parent != root) {
            return false;
        }
        return cycle(root.left) && cycle(root.right);
    }

    public boolean sanity() {
        try{//cycle from this to sentinel
        BSTree fast = this, slow = this;
            while (fast != null && fast.parent != null) {
                slow = slow.parent;
                fast = fast.parent.parent;
                if (slow == fast) {
                    return false;
                }
            }
        BSTree senti = this.goToSentinel(this);
        //check sentinels
        if(senti.parent!=null){return false;}
        boolean check1 = senti.left==null&&senti.address==-1&&senti.key==-1&&senti.size==-1;
        if(!check1){return false;}
        if(senti.right==null){return true;}//empty tree
        //check cycle and node.left.parent=node and node.right.parent=node
        if(!cycle(this.goToSentinel(this).right)){return false;}
        //check bst property
        BSTree start = this.getFirst(), next = this.getFirst().getNext();
        while(next!=null){
            if(start.key>next.key || (start.key==next.key&&start.address>next.address) ){return false;}
            start=next;
            next=next.getNext();
        }
        return true;}
        catch(Exception e){return true;}
    }
}
