/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package treasurehauntadventure;

/**
 *
 * @author ervas
 */
public class Spot<T> {

     T data;
    Spot <T> next;
    Spot <T> prev;
    Spot <T> jumpTo;
    Spot(T data){
        this.data=data;
        this.next=null;
        this.prev=null;
        this.jumpTo=null;
    }
}
