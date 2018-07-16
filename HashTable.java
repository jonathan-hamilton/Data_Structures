package C189_Project;

/**
 * Created by Jonathan Hamilton on 6/6/2017.
 */
public class HashTable {
    public static Node[] hashTable = new Node[13];
    public static int getHashIndex(String fn, String ln) {
        int hashIndex = (fn + ln).hashCode() % hashTable.length;
        if (hashIndex < 0) {
            hashIndex = hashIndex + hashTable.length;
        }
        return hashIndex;
    }
    public void add(String fName, String lName, String phone, String email) {
        Node newNode = new Node(fName, lName, phone, email);
        int index = getHashIndex(fName, lName);
        if (hashTable[index] == null) {
            hashTable[index] = newNode;
        } else {
            Node lastNode = hashTable[index];
            while (lastNode.next != null) {
                lastNode = lastNode.next;
            }
            lastNode.next = new Node(fName, lName, phone, email);
        }
        System.out.println(fName + " " + lName + " added to the HashTable");
    }
    public void displayTable() {
        System.out.println("Display of HashTable:");
        System.out.println();
        for (int i = 0; i < 13; i++) {
            Node n = hashTable[i];
            System.out.print("" + i + ": ");
            while (n != null) {
                System.out.print(n.getFirstName() + " " + n.getLastName() + "-> ");
                n = n.getNextNode();
            }
            System.out.println();
        }
    }
    public boolean delete(String fName, String lName) {
        int index = getHashIndex(fName, lName);
        Node firstNode = hashTable[index];
        boolean result = false;
        if (hashTable[index] == null) { //JH: if there is no value to delete in the hashTable index
            System.out.println(fName + " " + lName + " wasn't in the Hashtable, cannot delete!");
            return false;
        }
        else { //JH: If there is one or more value in the index
            Node currentNode = firstNode;
            Node previousNode = null;
            while (currentNode != null) {
                if (currentNode.getFirstName().equals(fName) && currentNode.getLastName().equals(lName)) {
                    if (previousNode == null) { //JH: remove first node from chain of nodes
                        hashTable[index] = currentNode.next;
                    }
                    else { //JH: removing a non-first node and linking previous and next (if not the last node)
                        previousNode.next = currentNode.next;
                    }
                    System.out.println(fName + " " + lName + " deleted.");
                    return true;
                }
                previousNode = currentNode; //JH: move to next node if match not found in currentNode
                currentNode = currentNode.next;
            }
            System.out.println(fName + " " + lName + " is not found, can't delete");
        }
        return result;
    }
    public Node lookUp(String fName, String lName) {
        Node currentNode;
        String result = fName + " " + lName + " not found in HashTable.";
        int index = getHashIndex(fName, lName);
        currentNode = hashTable[index];
        L1:
        while ((currentNode != null)) {
            if (fName.equals(currentNode.getFirstName()) & (lName.equals(currentNode.getLastName()))) {
                result =
                        currentNode.getFirstName() + " " +
                                currentNode.getLastName() + " " +
                                currentNode.getPhone() + " " +
                                currentNode.getEmail();
                break L1;
            } else {
                currentNode = currentNode.next;
            }
        }
        System.out.println(result);
        return currentNode;
    }
}
class Node {
        private String firstName;
        private String lastName;
        private String phone;
        private String email;
        Node next;

        //C189_Project.Node constructors
        public Node(String fn, String ln, String ph, String em) {
            this(fn, ln, ph, em, null);
        }

        private Node(String fn, String ln, String ph, String em, Node nextNode) {
            this.firstName = fn;
            this.lastName = ln;
            this.phone = ph;
            this.email = em;
            this.next = nextNode;
        }

        //getters
        public String getFirstName() { // getters
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getPhone() {
            return phone;
        }

        public String getEmail() {
            return email;
        }

        public Node getNextNode() {
            return next;
        }

        //setters
        public void setFirstName(String fn) { // setters
            this.firstName = fn;
        }

    public void setLastName(String ln) {
        this.lastName = ln;
    }

    public void setPhone(String ph) {
        this.phone = ph;
    }

    public void setEmail(String em) {
        this.email = em;
    }

    public void setNextNode(Node nextNode) {
        next = nextNode;
    }
    }
class TestClass1 {
        public static void main(String[] args) {
            HashTable ht = new HashTable();
            ht.add("Bob", "Smith","555-235-1111","bsmith@somewhere.com");
            ht.add("Jane", "Williams","555-235-11112","jw@something.com");
            ht.add("Mohammed", "al-Salam","555-235-1113","mas@someplace.com");
            ht.add("Pat", "Jones","555-235-1114","pjones@homesweethome.com");
            ht.add("Billy", "Kidd","555-235-1115","billy_the_kid@nowhere.com");
            ht.add("H.", "Houdini","555-235-1116","houdini@noplace.com");
            ht.add("Jack", "Jones","555-235-1117","jjones@hill.com");
            ht.add("Jill", "Jones","555-235-1118","jillj@hill.com");
            ht.add("John", "Doe","555-235-1119","jdoe@somedomain.com");
            ht.add("Jane", "Doe","555-235-1120","jdoe@somedomain.com");

//            System.out.println();
//            ht.displayTable();

            System.out.println();
            ht.lookUp("Pat", "Jones");
            ht.lookUp("Billy", "Kidd");

            System.out.println();
            ht.delete("John","Doe");

            System.out.println();
            ht.add("Test", "Case", "555-235-1121", "Test_Case@testcase.com");
            ht.add("Nadezhda", "Kanachekhovskaya", "555-235-1122", "dr.nadezhda.kanacheckovskaya@somehospital.moscow.ci.ru");
            ht.add("Jo", "Wu", "555-235-1123", "Twu@h.com");
            ht.add("Millard", "Fillmore", "555-235-1124", "millard@theactualwhitehouse.us");
            ht.add("Bob", "vanDyke", "555-235-1125", "vandyke@nodomain.com");
            ht.add("Upside", "Down", "555-235-1126", "upsidedown@rightsideup.com");

            System.out.println();
            ht.lookUp("Jack","Jones");
            ht.lookUp("Nadezhda","Kanachekhovskaya");

            System.out.println();
            ht.delete("Jill","Jones");
            ht.delete("John","Doe");

            System.out.println();
            ht.lookUp("Jill","Jones");
            ht.lookUp("John","Doe");

//            System.out.println();
//            ht.displayTable();


        }
    }







