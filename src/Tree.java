import java.util.ArrayList;
import java.util.Collections;

public class Tree {
    public Node root;
    Tree() {
        root = null;
    }
    public boolean insert(int value) {
        if(root == null) {
            root = new Node(value);
            return true;
        }
        Node insertionNode = root.search(value);
        if(!insertionNode.add(value)) { //return if inserting duplicate
            return false;
        }
        if(insertionNode.isFull()) { //check if its full and need to split
            insertionNode.splitNode();
        }
        return true;
    }

    public int size() {
        return root.treeSize;
    }

    public int size(int value) {
        Node searchedNode = root.search(value);
        return (searchedNode.keys.contains(value)) ? searchedNode.treeSize : 0;
    }

    public int get(int x) {
        Node current = root;
        int indexTraversed = 0;
        while(current != null) {
            int i = 0;
            int ithKeyIndex = indexTraversed +
                    ((current.children.get(i) != null) ? current.children.get(i).treeSize : 0);
            // loop through the keys
            while (i < current.keys.size() && x > ithKeyIndex) {
                i++;
                indexTraversed = ithKeyIndex + 1;
                ithKeyIndex = indexTraversed +
                        ((current.children.get(i) != null) ? current.children.get(i).treeSize : 0);
            }
            if(x == ithKeyIndex) {
                return current.keys.get(i);
            }
            current = current.children.get(i);
        }
        return -1;
    }


    // Node Class
    public class Node {
        private ArrayList<Integer> keys = new ArrayList<>(3);
        private ArrayList<Node> children = new ArrayList<>(4);
        public Node parent = null;
        private int treeSize = 0;

        Node(int value) {
            keys.add(value);
            treeSize = 1;
            children.add(0, null);
            children.add(1, null);
            children.add(2, null);
            children.add(3, null);
        }

        /*
        Custom constructor
        create a new node and set the children & parent relation accordingly
        */
        Node(int value, Node leftChild, Node rightChild) {
            this(value);
            children.set(0, leftChild);
            children.set(1, rightChild);
            if(leftChild != null) {
                treeSize += leftChild.treeSize;
                leftChild.parent = this;
            }
            if(rightChild != null) {
                treeSize += rightChild.treeSize;
                rightChild.parent = this;
            }
        }

        private boolean isFull() {
            return keys.size() == 3;
        }

        private boolean isLeaf() {
            return children.get(0) == null && children.get(1) == null;
        }

        private boolean add(int value) { // return false if duplicate
            if (keys.contains(value)) return false;
            keys.add(value);
            Collections.sort(keys);
            treeSize += 1;
            if(!this.isFull()) {
                this.incrementParentsSize();
            }
            return true;
        }

        /*
        recursively adding 1 to parent node's tree size
         */
        private void incrementParentsSize() {
            if(parent != null) {
                parent.treeSize += 1;
                parent.incrementParentsSize();
            }
        }

        /*
        Search Method
        return a node that contains the value or a leaf node where that value should be inserted
        */
        private Node search(int value) {
            if(isLeaf() || keys.contains(value)) {
                return this;
            }
            // go to the child node
            int i = 0;
            while(i < keys.size() && value > keys.get(i)) {
                i++;
            }
            return children.get(i).search(value);
        }

        private void splitNode() {
            Node leftHalf = new Node(keys.get(0), children.get(0), children.get(1));
            Node rightHalf = new Node(keys.get(2), children.get(2), children.get(3));

            if(parent == null) {
                // create and assign a new parent node
                root = new Node(keys.get(1), leftHalf, rightHalf);
            } else {
                parent.receiveSplitNode(keys.get(1), leftHalf, rightHalf);
                if(parent.isFull()) { // recursive if parent now has 3 keys
                    parent.splitNode();
                }
            }
        }

        /*
        Helper function for sending a split node to its parent
         */
        private void receiveSplitNode(int value, Node leftHalfChild, Node rightHalfChild) {
            leftHalfChild.parent = this;
            rightHalfChild.parent = this;
            int i = 0;
            while(i < keys.size() && value > keys.get(i)) {
                i++;
            }
            //shift all children after i to the right
            for(int i2 = children.size() - 1; i2 >= i + 1; i2--) {
                children.set(i2, children.get(i2-1));
            }
            //assign the right half and left half at the insertion index(i)
            children.set(i+1, rightHalfChild);
            children.set(i, leftHalfChild);
            this.add(value);
        }

    }
}