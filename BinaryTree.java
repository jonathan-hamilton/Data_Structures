package C189_Project;

/**
 * Created by Jonathan Hamilton on 6/8/2017.
 */
public class BinaryTree {
    public static BinaryNode root;
    private String result;
    private int index;

    public BinaryTree() {
        root = null;
    }
    public void setRoot(BinaryNode bn) {
        root = bn;
    }
    public void addEntry(BinaryNode nodeToAdd) {
        BinaryNode currentNode = root; // JH: cursor
        if (currentNode == null) { // JH: if BinaryTree is empty, creates new tree and adds nodeToAdd to root
            setRoot(nodeToAdd);
            System.out.println(nodeToAdd.toString() + ", INSERTED as the root");
        } else { //JH: if tree is not empty
            boolean foundNext = false;
            while (!foundNext) {
                if (currentNode.isLeaf()) {
                    if (nodeToAdd.getKey() > currentNode.getKey()) { // JH: if nodeToAdd.key > currentNode.key, insert as right child
                        currentNode.setRightChild(nodeToAdd);
                        foundNext = true;
                        System.out.println(nodeToAdd.toString() + ", INSERTED to right as a child of: " + currentNode.getFirstName());
                    } else if (nodeToAdd.getKey() < currentNode.getKey()) { // JH: if nodeToAdd.key < currentNode.key, insert as left child
                        currentNode.setLeftChild(nodeToAdd);
                        foundNext = true;
                        System.out.println(nodeToAdd.toString() + ", INSERTED to left as a child of: " + currentNode.getFirstName());
                    } else if (nodeToAdd.getKey() == currentNode.getKey()) {// JH: if nodeToAdd.key == currentNode.key, insert as right
                        currentNode.setRightChild(nodeToAdd);
                        foundNext = true;
                        System.out.println(nodeToAdd.toString() + ", key was equal, INSERTED to right of: " + currentNode.getFirstName());
                    }
                } else  //JH: if currentNode is not a leaf
                    if (currentNode.hasLeftChild() & !currentNode.hasRightChild()) {// JH: if node is not a leaf and has a left child
                        if (nodeToAdd.getKey() > currentNode.getKey()) { // JH: insert node if right, foundNext = true
                            currentNode.setRightChild(nodeToAdd);
                            System.out.println(nodeToAdd.toString() + " INSERTED to right child of: " + currentNode.getFirstName());
                            foundNext = true;
                        } else {
                            currentNode = currentNode.getLeftChild(); // JH: keep searching, move currentNode to left child
                        }
                    } else if (currentNode.hasRightChild() & !currentNode.hasLeftChild()) { // JH: if node is not a leaf and has a right child
                        if (nodeToAdd.getKey() >= currentNode.getKey()) {  // JH: keep searching, move to next node if right
                            currentNode = currentNode.getRightChild();
                        } else if (nodeToAdd.getKey() < currentNode.getKey()) {
                            currentNode.setLeftChild(nodeToAdd); // JH: insert node if left, foundNext = true
                            System.out.println(nodeToAdd.toString() + " INSERTED to left child of: " + currentNode.getFirstName());
                            foundNext = true;
                        }
                    } else if (currentNode.hasLeftChild() & currentNode.hasRightChild()) { // JH: if node has both a right and left child
                        if (nodeToAdd.getKey() > currentNode.getKey()) {
                            currentNode = currentNode.getRightChild();
                        } else {
                            currentNode = currentNode.getLeftChild();
                        }
                    }
            }
        }
    }
    public boolean deleteEntry(String fn, String ln){
        BinaryNode parentNode = root;
        BinaryNode currentNode = root;
        boolean isLeftChild = false;
        while(!currentNode.getFirstName().equals(fn) || !currentNode.getLastName().equals(ln)){ // JH: search tree for match to delete, return false if not found
            parentNode = currentNode;
            if(currentNode.getKey() > BinaryNode.generateKey(fn,ln)){
                isLeftChild = true;
                currentNode = currentNode.getLeftChild();
            }
            else{
                isLeftChild = false;
                currentNode = currentNode.getRightChild();
            }
            if(currentNode == null){
                System.out.println(fn + " " + ln + " not in the tree to delete");
                return false;
            }
        }
        if(currentNode.getLeftChild() == null && currentNode.getRightChild() == null){ // JH: node found, no children
            if(currentNode == root){
                root = null;
            }
            if(isLeftChild == true){
                parentNode.setLeftChild(null);
            }
            else{
                parentNode.setRightChild(null);
            }
        }                                                                                   //JH: node found, has one child,
        else if(currentNode.getRightChild() == null && currentNode.getLeftChild() != null){  //has left child only
            if(currentNode == root){
                root = currentNode.getLeftChild();
            }
            else if(isLeftChild){
                parentNode.setLeftChild(currentNode.getLeftChild());
            }
            else{
                parentNode.setRightChild(currentNode.getLeftChild());
            }
        }
        else if(currentNode.getRightChild() != null && currentNode.getLeftChild() == null) { //JH: has right child only
            if (currentNode == root) {
                root = currentNode.getRightChild();
            } else if (isLeftChild) {
                parentNode.setLeftChild(currentNode.getRightChild());
            } else {
                parentNode.setRightChild(currentNode.getRightChild());
            }
        }
        else if(currentNode.getLeftChild() != null && currentNode.getRightChild() != null){ //JH: has two child nodes
                BinaryNode successorNode = nodeToSucceed(currentNode);
                if(currentNode == root){
                    root = successorNode;
                }
                else if(isLeftChild){
                    parentNode.setLeftChild(successorNode);
                }
                else{
                    parentNode.setRightChild(successorNode);
                }
                successorNode.setLeftChild(currentNode.getLeftChild());
            }
            System.out.println(fn + " " + ln + " deleted from tree");
            return true;
    }
    public BinaryNode nodeToSucceed(BinaryNode deleteNode){ //JH: get successor node
        BinaryNode successor = null;
        BinaryNode successorParent = null;
        BinaryNode currentNode = deleteNode.getRightChild();
        while(currentNode != null){
            successorParent = successor;
            successor = currentNode;
            currentNode = currentNode.getLeftChild();
        }
        if (successor != deleteNode.getRightChild()){
            successorParent.setLeftChild(successor.getRightChild());
            successor.setRightChild(deleteNode.getRightChild());
        }
        return successor;
    }
    public void preOrder() {
        preOrderTraverse(root);
        index = 0;
    }
    private void preOrderTraverse(BinaryNode bn) {
        if (bn == null) {
            return;
        }
        index++;
        System.out.println("Test Case: " + index + ": " + bn.toString() + " ");
        preOrderTraverse(bn.getLeftChild());
        preOrderTraverse(bn.getRightChild());
    }
    public String lookUp(String fn, String ln) {
        BinaryNode currentNode = root;  // JH: cursor
        while (currentNode != null) {
            if(currentNode.getFirstName() == fn & currentNode.getLastName() == ln) {
                System.out.println("Found: " + currentNode.toString());
                return currentNode.toString();
            }
            else if(currentNode.getKey() > BinaryNode.generateKey(fn,ln)){ // JH: go left child if not found and less than
                currentNode = currentNode.getLeftChild();
            }
            else {
                currentNode = currentNode.getRightChild(); // JH: go right child if not found and greater than or equal to
            }
        }
        System.out.println(fn + " " + ln + " " + "isn't in the tree");
        return null;
    }

//    public void printTree(BinaryNode bn, int numOfLeadingSpaces) {
//
//        if (bn == null) {
//
//            for (int i = 0; i < numOfLeadingSpaces; i++)
//
//                System.out.print(" ");
//
//            System.out.println("null");
//
//            return;
//
//        }
//
//        bn.printKey(numOfLeadingSpaces);
//
//        printTree(bn.getLeftChild(), numOfLeadingSpaces + 1);
//
//        printTree(bn.getRightChild(), numOfLeadingSpaces + 1);
//
//    }
}
class BinaryNode{
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private int key;
    private BinaryNode left;
    private BinaryNode right;

    public BinaryNode(String fn,String ln,String ph,String em){
        this(fn,ln,ph,em,null,null);
    }
    public BinaryNode(String fn,String ln,String ph,String em,BinaryNode leftChild,BinaryNode rightChild){
        this.firstName = fn;
        this.lastName = ln;
        this.phone = ph;
        this.email = em;
        this.key = generateKey(fn,ln);
        left = leftChild;
        right = rightChild;
    }
    public static int generateKey(String fn, String ln) {
        int key = (fn+ln).hashCode();
        if(key < 0){
            key = Math.abs(key);
        }
        return key;
    }
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public String getPhone(){
        return phone;
    }
    public String getEmail(){
        return email;
    }
    public BinaryNode getLeftChild(){
        return left;
    }
    public BinaryNode getRightChild(){
        return right;
    }
    public int getKey(){
        return key;
    }
    public Boolean hasLeftChild(){
        if (left != null){
            return true;
        }
        else{return false;}
    }
    public Boolean hasRightChild(){
        if(right != null){
            return true;
        }
        else {
            return false;
        }
    }
    public String toString(){
        return (getFirstName() + " " +
                getLastName() + " " +
                getPhone() + " " +
                getEmail())+ " " +
                "key: " + this.key;
    }
    public boolean isLeaf(){
        if(left == null & right == null){
            return true;
        }
        else{
            return false;
        }
    }

//    public void setKey(int newKeyValue){
//        this.key = newKeyValue;
//        System.out.println("New keyValue for " + this.getFirstName() + " " + this.getLastName() + " is " + newKeyValue);
//    }
//    public void setFirstName(String fName){
//        this.firstName = fName;
//    }
//    public void setLastName(String lName){
//        this.lastName = lName;
//    }
//    public void setPhone(String ph){
//        this.phone = ph;
//    }
//    public void setEmail(String em){
//        this.email = em;
//    }

    public void setLeftChild(BinaryNode leftCh){
        this.left = leftCh;
    }
    public void setRightChild(BinaryNode rightCh){
        this.right = rightCh;
    }

//    public void printKey(int spaces){
//        while(spaces > 0) {
//            System.out.print(" ");
//            spaces--;
//        }
//        System.out.println(this.getFirstName() + " " + this.getLastName());
//    }
}
class TestClass{
    public static void main(String[] args){
        BinaryTree myBiTree = new BinaryTree();
        BinaryNode bn1 = new BinaryNode("Bob", "Smith","555-235-1111","bsmith@somewhere.com");
        BinaryNode bn2 = new BinaryNode("Jane", "Williams","555-235-11112","jw@something.com");
        BinaryNode bn3 = new BinaryNode("Mohammed", "al-Salam","555-235-1113","mas@someplace.com");
        BinaryNode bn4 = new BinaryNode("Pat", "Jones","555-235-1114","pjones@homesweethome.com");
        BinaryNode bn5 = new BinaryNode("Billy", "Kidd","555-235-1115","billy_the_kid@nowhere.com");
        BinaryNode bn6 = new BinaryNode("H.", "Houdini","555-235-1116","houdini@noplace.com");
        BinaryNode bn7 = new BinaryNode("Jack", "Jones","555-235-1117","jjones@hill.com");
        BinaryNode bn8 = new BinaryNode("Jill", "Jones","555-235-1118","jillj@hill.com");
        BinaryNode bn9 = new BinaryNode("John", "Doe","555-235-1119","jdoe@somedomain.com");
        BinaryNode bn10 = new BinaryNode("Jane", "Doe","555-235-1120","jdoe@somedomain.com");

        System.out.println();
        myBiTree.addEntry(bn1); // JH: add method
        myBiTree.addEntry(bn2);
        myBiTree.addEntry(bn3);
        myBiTree.addEntry(bn4);
        myBiTree.addEntry(bn5);
        myBiTree.addEntry(bn6);
        myBiTree.addEntry(bn7);
        myBiTree.addEntry(bn8);
        myBiTree.addEntry(bn9);
        myBiTree.addEntry(bn10);

//        System.out.println();
//        myBiTree.preOrder();

        System.out.println();
        myBiTree.lookUp("Pat", "Jones");
        myBiTree.lookUp("Billy", "Kidd");

        System.out.println();
        myBiTree.deleteEntry("John", "Doe");

        System.out.println();
        BinaryNode bn11 = new BinaryNode("Test", "Case", "555-235-1121", "Test_Case@testcase.com");
        myBiTree.addEntry(bn11);

        BinaryNode bn12 = new BinaryNode("Nadezhda", "Kanachekhovskaya", "555-235-1122", "dr.nadezhda.kanacheckovskaya@somehospital.moscow.ci.ru");
        myBiTree.addEntry(bn12);

        BinaryNode bn13 = new BinaryNode("Jo", "Wu", "555-235-1123", "Twu@h.com");
        myBiTree.addEntry(bn13);

        BinaryNode bn14 = new BinaryNode("Millard", "Fillmore", "555-235-1124", "millard@theactualwhitehouse.us");
        myBiTree.addEntry(bn14);

        BinaryNode bn15 = new BinaryNode("Bob", "vanDyke", "555-235-1125", "vandyke@nodomain.com");
        myBiTree.addEntry(bn15);

        BinaryNode bn16 = new BinaryNode("Upside", "Down", "555-235-1126", "upsidedown@rightsideup.com");
        myBiTree.addEntry(bn16);

//        System.out.println();
//        myBiTree.preOrder();

        System.out.println();
        myBiTree.lookUp("Jack", "Jones");
        myBiTree.lookUp("Nadezhda", "Kanachekhovskaya");

        System.out.println();
        myBiTree.deleteEntry("Jill", "Jones");
        myBiTree.deleteEntry("John", "Doe");

        System.out.println();
        myBiTree.lookUp("Jill", "Jones");
        myBiTree.lookUp("John", "Doe");

//        System.out.println();
//        myBiTree.preOrder();
//
//        myBiTree.printTree(BinaryTree.root, 0);


    }
}