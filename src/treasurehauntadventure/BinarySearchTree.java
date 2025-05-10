/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package treasurehauntadventure;

import java.util.ArrayList;

/**
 *
 * @author ervas
 */
public class BinarySearchTree<T extends Comparable<T>>  {
    
    BstNode<T> root;

    public void insert(T newData) {
        BstNode<T> newNode = new BstNode(newData);

        if (root == null) {
            root = newNode;
        } else {
            BstNode<T> temp = root;

            while (temp != null) {
                if (newData.compareTo(temp.data) > 0) {
                    if (temp.right == null) {
                        temp.right = newNode;
                        return;
                    }
                    temp = temp.right;
                } else if (newData.compareTo(temp.data) < 0) {
                    if (temp.left == null) {
                        temp.left = newNode;
                        return;
                    }
                    temp = temp.left;
                } else {
                    return;
                }
            }
        }
    }

    public BstNode<T> getRoot() {
        return root;
    }

    public T min() {
        if (root == null) {
            System.out.println("Agac bos!");
            return null;
        }
        BstNode<T> temp = root;
        while (temp.left != null) {
            temp = temp.left;
        }
        return temp.data;
    }

    public T max() {
        if (root == null) {
            System.out.println("Agac bos!");
            return null;
        }
        BstNode <T> temp = root;
        while(temp.right != null){
            temp = temp.right;
        }
        return temp.data;
    }
        public void inorder(BstNode<T> node, ArrayList<T> result) {
        if (node == null) return;
        inorder(node.left, result);
        result.add(node.data);
        inorder(node.right, result);
    }
    
}
