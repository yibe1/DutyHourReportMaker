/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler;


/**
 *
 * @author Jimma University
 */
import java.util.LinkedList;
import java.util.Queue;

public class Que {

    public void mainQue() {
        Queue<Integer> q = new LinkedList<Integer>();
        q.add(5);
        q.add(2);
        q.add(1);
        q.add(4);
        q.add(3);
        System.out.println(q);
    }
}
