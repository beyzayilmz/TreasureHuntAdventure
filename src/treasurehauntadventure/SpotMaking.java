/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package treasurehauntadventure;

/**
 *
 * @author ervas
 */
public class SpotMaking {
    
    
    public enum Type { TREASURE, TRAP, EMPTY, FORWARD, BACKWARD }
    public Type type;
    public int index;
    public boolean isStartOrEnd;
    public int jump ;

    public SpotMaking(Type type, int index, boolean isStartOrEnd) {
        this.type = type;
        this.index = index;
        this.isStartOrEnd = isStartOrEnd;
        this.jump=0;
    }

    @Override
    public String toString() {
        if (isStartOrEnd) return "Start/End";
        if (type == Type.EMPTY) return "Empty #" + index;
        return type.toString();
    }
}
    

