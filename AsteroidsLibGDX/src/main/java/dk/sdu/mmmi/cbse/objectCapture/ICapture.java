package dk.sdu.mmmi.cbse.objectCapture;

/**
 *
 * @param <T> The object from which to derive a specific capture object
 * @param <R> The type of the capture object
 */
public interface ICapture<T,R> {

    R getOrUpdateCapture(T object, R previous);

}
