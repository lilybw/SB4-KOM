package dk.sdu.mmmi.cbse.entities;

public interface IConditionalEffect<T> {
    boolean isApplicableTo(T obj);
}
