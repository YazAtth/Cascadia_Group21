/*
 * Cascadia
 * 21: Group 21
 * Student name:            GitHub ID:
 * Yasith Atthanayake       YazAtth
 * Colm Ó hAonghusa         C0hAongha
 * Dominykas Jakubauskas    dominicjk
 */

package org.grouptwentyone.models;

public class CustomPair<S,T> {

    private S field1;
    private T field2;

    public CustomPair(S field1, T field2) {
        this.field1 = field1;
        this.field2 = field2;
    }

    public S getField1() {
        return field1;
    }

    public void setField1(S field1) {
        this.field1 = field1;
    }

    public T getField2() {
        return field2;
    }

    public void setField2(T field2) {
        this.field2 = field2;
    }

    public String toString() {
        return String.format("('%s', '%s')", this.getField1(), this.getField2());
    }
}
