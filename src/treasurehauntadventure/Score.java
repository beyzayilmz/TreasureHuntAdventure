/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package treasurehauntadventure;

/**
 *
 * @author ervas
 */
public class Score implements Comparable<Score> {

    public int score;
    public String level;
    public String username;

    public Score(int score, String level, String username) {
        this.score = score;
        this.level = level;
        this.username = username;
    }

    @Override
    public int compareTo(Score other) {
        return Integer.compare(this.score, other.score);
    }

    @Override
    public String toString() {
        return score + " (" + level + ")";
    }

}

