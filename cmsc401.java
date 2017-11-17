// Yaxiaer Atajiang

import java.util.Arrays;
import java.util.Scanner;

class Node {
    int key, height;
    Node left, right;

    Node(int i) {
        key = i;
        height = 1;
    }
}

public class cmsc401 {

    private static int sArray[], wArray[];
    public static int predecessor, successor;

    public static class AVLTree {

        Node root;

        // Gets height of the tree
        int treeHeight(Node node) {
            if (node == null) {
                return 0;
            }
            return node.height;
        }

        // Compares two integers and returns larger one
        int largerInt(int first, int second) {
            return (first > second) ? first : second;
        }

        // Finds largerInt valued key in the tree
        int maxValue(Node node) {
            Node current = node;

            while (current.right != null) {
                current = current.right;
            }
            return (current.key);
        }

        // Right rotates subtree rooted with rootNode
        Node rightRotate(Node rootNode) {
            Node leftNode = rootNode.left;
            Node tree = leftNode.right;
            leftNode.right = rootNode;
            rootNode.left = tree;
            rootNode.height = largerInt(treeHeight(rootNode.left), treeHeight(rootNode.right)) + 1;
            leftNode.height = largerInt(treeHeight(leftNode.left), treeHeight(leftNode.right)) + 1;

            return leftNode;
        }

        // Left rotates subtree rooted with rootNode
        Node leftRotate(Node rootNode) {
            Node rightNode = rootNode.right;
            Node tree = rightNode.left;
            rightNode.left = rootNode;
            rootNode.right = tree;
            rootNode.height = largerInt(treeHeight(rootNode.left), treeHeight(rootNode.right)) + 1;
            rightNode.height = largerInt(treeHeight(rightNode.left), treeHeight(rightNode.right)) + 1;

            return rightNode;
        }

        // Checks if the tree is balanced
        int checkBalance(Node node) {
            if (node == null) {
                return 0;
            }
            return treeHeight(node.left) - treeHeight(node.right);
        }

        // Inserts integer to the AVLTree
        Node insert(Node node, int key) {

            if (node == null) {
                return (new Node(key));
            }
            if (key < node.key) {
                node.left = insert(node.left, key);
            }
            else if (key > node.key) {
                node.right = insert(node.right, key);
            }
            else {
                return node;
            }

            node.height = 1 + largerInt(treeHeight(node.left), treeHeight(node.right));

            // Check if the tree is still balanced
            int balance = checkBalance(node);

            // Functions to balance an unbalanced tree
            if (balance > 1 && key < node.left.key) {
                return rightRotate(node);
            }

            if (balance < -1 && key > node.right.key) {
                return leftRotate(node);
            }

            if (balance > 1 && key > node.left.key) {
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }

            if (balance < -1 && key < node.right.key) {
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }

            return node;
        }

        void succPredFinder(Node root, int key) {

            if (root != null) {
                if (root.key == key) {

                    // Finds predecessor
                    if (root.left != null) {
                        Node temporary = root.left;
                        while (temporary.right != null) {
                            temporary = temporary.right;
                        }
                        predecessor = temporary.key;
                    }

                    // Finds successor
                    if (root.right != null) {
                        Node temporary = root.right;
                        while (temporary.left != null) {
                            temporary = temporary.left;
                        }
                        successor = temporary.key;
                    }
                } else if (root.key > key) {
                    successor = root.key;
                    succPredFinder(root.left, key);
                } else if (root.key < key) {
                    predecessor = root.key;
                    succPredFinder(root.right, key);
                }
            }
        }
    }

    public static void main(String[] args) {

        // New scanner
        Scanner scanner1 = new Scanner(System.in);
        // Match counter
        int matched = 0, count = 0, count2 = 0;
        // Start and End index saver
        int indexSaver[];
        // New AVL Tree (Balanced BST)
        AVLTree tree = new AVLTree();

        // Input array sizes
        int sSize = scanner1.nextInt();
        int wSize = scanner1.nextInt();

        // Sets array sizes
        sArray = new int[sSize + 1];
        wArray = new int[wSize];
        indexSaver = new int [2*wSize];

        // Inserts positions from the start of the text
        sArray[0] = 0;
        for (int i = 1; i < sSize + 1; ++i) {
            sArray[i] = scanner1.nextInt();
        }

        // Insets sArray elements to the AVLTree
        for (int i = 0; i < sArray.length; i++){
            tree.root = tree.insert(tree.root, sArray[i]);
        }

        // Inserts numbers that contain the positions
        for (int i = 0; i < wSize; ++i) {
            wArray[i] = scanner1.nextInt();
        }

        for (int i = 0; i < wSize; i++){

            if (wArray[i] > 0 && wArray[i] < tree.maxValue(tree.root)){
                tree.succPredFinder(tree.root, wArray[i]);
                matched++;
                indexSaver[count] = predecessor + 1;
                count++;
                indexSaver[count] = successor;
                count++;
            }
        }

        //array tests
        System.out.println("---Array test---");
        System.out.println(Arrays.toString(sArray));
        System.out.println(Arrays.toString(wArray));
        System.out.println("---Array test end---\n\n");

        System.out.println("indexsaver is: " + Arrays.toString(indexSaver));

        System.out.println(matched);
        for (int i = 0; i < matched; i++){
            System.out.println(indexSaver[count2] + " " + indexSaver[count2+1]);
            count2 += 2;
        }
    }
}
