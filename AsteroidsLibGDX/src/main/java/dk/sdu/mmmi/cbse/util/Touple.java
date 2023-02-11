package dk.sdu.mmmi.cbse.util;

public class Touple <T,R>{

    public T t;
    public R r;

    public Touple(T t, R r){
        this.t = t;
        this.r = r;
    }

    @Override
    public String toString()
    {
        return "Touple{"+t+","+r+"}";
    }
}
