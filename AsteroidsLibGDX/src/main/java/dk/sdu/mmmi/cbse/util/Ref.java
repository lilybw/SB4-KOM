package dk.sdu.mmmi.cbse.util;

public class Ref<T> {

    protected T val;

    public Ref(T val){
        this.val = val;
    }

    public T get() {
        return val;
    }
    public void set(T val){
        this.val = val;
    }

    @Override
    public String toString()
    {
        return val.toString();
    }
}
