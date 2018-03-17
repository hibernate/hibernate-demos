package fieldreader.spec.bootstrap;

import java.lang.invoke.MethodHandles.Lookup;

import fieldreader.spec.FieldValueReader;

public interface BootstrapDelegate {

    FieldValueReader getFieldValueReader(LookupProvider lookupProvider);

    public interface LookupProvider {
        Lookup getPrivateLookup(Object o);
    }
}
