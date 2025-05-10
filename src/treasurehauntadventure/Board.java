/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package treasurehauntadventure;
  import java.util.Random;
/**
 *
 * @author ervas
 */

public class Board <T>{
    private Spot<T> head;
    private Spot<T> tail;
    private int size;

    public void add(T data) {
        Spot<T> newNode = new Spot<>(data);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    public Spot<T> getHead() {
        return head;
    }

    public int getSize() {
        return size;
    }
}


//public class Board {
//    
//
//    public static Spot Board() {
//        int size = 30; //spot sayısı
//        Random rand = new Random();
//
//        Spot head = new Spot(randomType(rand));
//        Spot current = head;
//
//        for (int i = 1; i < size; i++) {
//            current.next = new Spot(randomType(rand));
//            current = current.next;
//        }
//
//        return head;
//    }
//    
//    String[] types = {"trap", "treasure", "empty"};
//Random rand = new Random();
//
//SpotLinkedList<Spot> board = new SpotLinkedList<>();
//
//for (int i = 0; i < 30; i++) {
//    String randomType = types[rand.nextInt(types.length)];
//    Spot s = new Spot(randomType);
//    board.add(s);
//}
//
//    private static Spot.Type randomType(Random rand) {
//        int r = rand.nextInt(3); // 0, 1, 2
//        return switch (r) {
//            case 0 -> Spot.Type.TREASURE;
//            case 1 -> Spot.Type.TRAP;
//            default -> Spot.Type.EMPTY;
//        };
//    }
//}

    

