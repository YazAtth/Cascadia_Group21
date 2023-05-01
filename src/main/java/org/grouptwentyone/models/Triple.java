package org.grouptwentyone.models;

public class Triple<R,S,T> {

    private R field1;
    private S field2;
    private T field3;

    public Triple(R field1, S field2, T field3) {
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
    }

    public R getField1() {
        return field1;
    }

    public void setField1(R field1) {
        this.field1 = field1;
    }

    public S getField2() {
        return field2;
    }

    public void setField2(S field2) {
        this.field2 = field2;
    }

    public T getField3() {
        return field3;
    }

    public void setField3(T field3) {
        this.field3 = field3;
    }

    public String toString() {
        return String.format("('%s', '%s', '%s')", this.getField1(), this.getField2(), this.getField3());
    }
}
