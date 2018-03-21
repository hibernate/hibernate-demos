package fieldreader.spec;

/**
 * Retrieves the value of the specified (private) field from the given object.
 */
public interface FieldValueReader {

    public Object getFieldValue(Object o, String fieldName);
}
