// Class: Height balanced AVL Tree
// Binary Search Tree

public class AVLTree extends BSTree {
    
    private AVLTree left, right;     // Children. 
    private AVLTree parent;          // Parent pointer. 
    private int height;  // The height of the subtree
        
    public AVLTree() { 
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node !.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.

    }

    private AVLTree goToSentinel(AVLTree x) {
        AVLTree temp = x ;
        while (temp.parent != null) {
            temp = temp.parent;
        }
        return temp;
    }

    public AVLTree(int address, int size, int key) { 
        super(address, size, key);
        this.height = 0;
    }

    private int height(AVLTree a){
        return (a==null)?-1:a.height;
    }
    private int max(int x, int y){return (x>y)?x:y;}
    private void balanceleft(AVLTree z, AVLTree y){
        y.parent=z.parent; if(y.parent.left==z){y.parent.left=y;}else{y.parent.right=y;}
        z.left=y.right; if(z.left!=null){z.left.parent = z;}
        z.parent=y; y.right = z;
        z.height = max(height(z.left),height(z.right))+1;
        y.height = max(height(y.left),height(y.right))+1;
    }
    private void balanceright(AVLTree z, AVLTree y){
        y.parent=z.parent; if(y.parent.left==z){y.parent.left=y;}else{y.parent.right=y;}
        z.right=y.left; if(z.right!=null){z.right.parent = z;}
        z.parent=y; y.left = z;
        z.height = max(height(z.left),height(z.right))+1;
        y.height = max(height(y.left),height(y.right))+1;
    }
    // Implement the following functions for AVL Trees.
    // You need not implement all the functions.
    // Some of the functions may be directly inherited from the BSTree class and nothing needs to be done for those.
    // Remove the functions, to not override the inherited functions.

    private void insertHelper(AVLTree root, AVLTree node){
        while(root!=null){
        if(root.key>node.key||(root.key==node.key&&root.address>node.address)){
            if(root.left==null){
                root.left=node;
                node.parent=root;
                return;
            }
            else{root = root.left;}
        }
        else {
            if(root.right==null){
                root.right=node;
                node.parent=root;
                return;
            }
            else{root = root.right;}
        }}

    }
    private void updateHeight(AVLTree  a){
        a.height = max(height(a.left),height(a.right))+1;
    }

    public AVLTree Insert(int address, int size, int key) 
    {   AVLTree node = new AVLTree(address,size,key), senti = this.goToSentinel(this),ins;
        ins=node;
        if(senti.right==null){senti.right = node; node.parent = senti; return node;}
        insertHelper(senti.right, node);
        while(node.parent!=null){
            AVLTree l = node.left, r=node.right;
            if(height(l)-height(r)>1){
                if(key>l.key||(key==l.key&&address>l.address)){
                    balanceright(l,l.right);l=l.parent;}
                balanceleft(node, l);}
            if(height(l)-height(r)<-1){
                if(key<r.key||(key==r.key&&address<=r.address)){
                    balanceleft(r,r.left);r=r.parent;}
                balanceright(node, r);}
            updateHeight(node);
            node=node.parent;}
            //if(!sanity()){System.out.println("blah blah blah");}
        return ins;
    }

    private void copy(AVLTree a, AVLTree b) {
        a.address = b.address;
        a.key = b.key;
        a.size = b.size;
    }

    private AVLTree deleteLeaf(AVLTree temp) {
        if (temp.parent.right == temp) {
            temp.parent.right = null;
        } else {
            temp.parent.left = null;
        }
        return temp.parent;
    }

    private AVLTree deleteOneChild(AVLTree temp, AVLTree child) {
        if (temp.parent.right == temp) {
            temp.parent.right = child;
        } else {
            temp.parent.left = child;
        }
        child.parent = temp.parent;
        return child.parent;
    }

    private AVLTree deleteInternal(AVLTree temp) {
        AVLTree tempo = temp.getNext();
        copy(temp, tempo);
        temp = tempo;
        if (temp.left == null && temp.right == null) {
            return deleteLeaf(temp);
        } else if (temp.left == null) {
            return deleteOneChild(temp, temp.right);
        } else {
            return deleteOneChild(temp, temp.left);
        }
    }

    private AVLTree deleteHelper(AVLTree root, Dictionary e){
        while(root!=null){
            if(root.key==e.key&&root.address==e.address&&root.size==e.size){
                if (root.left == null && root.right == null) {// System.out.println("Found " + temp.key);
                        return deleteLeaf(root);
                    } else if (root.left == null) {// System.out.println("Found " + temp.key);
                        return deleteOneChild(root, root.right);
                    } else if (root.right == null) {// System.out.println("Found " + temp.key);
                        return deleteOneChild(root, root.left);
                    } else {// System.out.println("Found " + temp.key);
                        return deleteInternal(root);
                    }
            }
            else if(root.key>e.key||(root.key==e.key&&root.address>e.address)){
                root = root.left;
            }
            else {
                root = root.right;
            }
        }
        return null;
    }
    public boolean Delete(Dictionary e)
    {   AVLTree senti = this.goToSentinel(this);
        if(e==null||senti.right==null){return false;}
        //senti.right.print();
        AVLTree node = deleteHelper(senti.right, e);
        //senti.right.print();
        if(node==null){return false;}
        //senti.right.print();
        while(node.parent!=null){
            AVLTree l = node.left, r=node.right;
            if(height(l)-height(r)>1){
                if(height(l.right)>height(l.left)){//System.out.println("balance right" + l.address + "-> " + l.address+l.size);
                    balanceright(l,l.right);l=l.parent;}
                balanceleft(node, l);}
            else if(height(l)-height(r)<-1){
                if(height(r.left)>height(r.right)){
                        //System.out.println("balance left  " + (node.left));
                    balanceleft(r,r.left);r=r.parent;}
                balanceright(node, r);}
            updateHeight(node);
            node=node.parent;}
        //if(!sanity()){System.out.println("blah blah blah");}
        return true;
    }

    private AVLTree findHelper(AVLTree root, int key, boolean exact) {
        if (root == null) {
            return null;
        }
        if (key > root.key) {
            return findHelper(root.right, key, exact);
        } else if (key < root.key) {
            AVLTree ans = findHelper(root.left, key, exact);
            if (!exact) {
                return (ans == null) ? root : ans;
            } else {
                return ans;
            }
        } else {
            AVLTree b = findHelper(root.left, key, exact);
            return (b == null) ? root : b;
        }
    }

    public AVLTree Find(int k, boolean exact)
    {   AVLTree temp = this.goToSentinel(this);
        if (temp.right == null) {// System.out.println("Empty Tree");
            return null;
        }
        temp = temp.right; // assert : temp = root
        return findHelper(temp, k, exact);
    }

    public AVLTree getFirst()
    {   AVLTree temp = this.goToSentinel(this);
        if (temp.right == null) {
            return null;
        }
        temp = temp.right; // assert : temp = root
        while (temp.left != null) {
            temp = temp.left;
        }
        return temp;
    }

    public AVLTree getNext()
    {   if (this.parent == null) {
        return null;
    } // Sentinel
    if (this.right != null) {
        AVLTree ans = this.right;
        while (ans.left != null) {
            ans = ans.left;
        }
        return ans;
    }
    AVLTree p = this.parent, temp = this;
    while (p.parent != null && p.left != temp) {
        p = p.parent;
        temp = temp.parent;
    }
    if (p.parent == null) {
        return null;
    }
    return p;
    }

    
    private boolean cycle(AVLTree root) {
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
        try{
            //cycle from this to sentinel
            AVLTree fast = this, slow = this;
            while (fast != null && fast.parent != null) {
                slow = slow.parent;
                fast = fast.parent.parent;
                if (slow == fast) {
                    return false;
                }
            }
            AVLTree senti = this.goToSentinel(this);
        //check sentinels
        if(senti.parent!=null){return false;}
        if(senti.left!=null){return false;}
        boolean check1 = senti.left==null&&senti.address==-1&&senti.key==-1&&senti.size==-1;
        if(!check1){return false;}
        if(senti.right==null){return true;}//empty tree
        //check cycle and node.left.parent=node and node.right.parent=node
        if(!cycle(this.goToSentinel(this).right)){return false;}
        //check bst property
        AVLTree start = this.getFirst(), next = this.getFirst().getNext();
        if(height(start.left)-height(start.right)>1||height(start.left)-height(start.right)<-1){return false;}
        while(next!=null){
            if(start.key>next.key || (start.key==next.key&&start.address>next.address) ){return false;}
            if(height(next.left)-height(next.right)>1||height(next.left)-height(next.right)<-1){return false;}
            start=next;
            next=next.getNext();
        }
        return true;}
        catch(Exception e){return true;}
    }

}


