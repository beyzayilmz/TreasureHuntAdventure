/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package treasurehauntadventure;

/**
 *
 * @author ervas
 */
public class BstNode<T extends Comparable<T>> {

    T data;
    BstNode<T> left;
    BstNode<T> right;

    public BstNode(T data) {
        this.data = data;
    }

}
