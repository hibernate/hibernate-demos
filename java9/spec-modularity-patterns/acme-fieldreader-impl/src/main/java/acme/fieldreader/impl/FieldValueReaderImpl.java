package acme.fieldreader.impl;

import fieldreader.spec.FieldValueReader;
import fieldreader.spec.bootstrap.BootstrapDelegate.LookupProvider;

public class FieldValueReaderImpl implements FieldValueReader {

    private LookupProvider lookupProvider;

    public FieldValueReaderImpl(LookupProvider lookupProvider) {
        this.lookupProvider = lookupProvider;
    }

    @Override
    public Object getFieldValue(Object o, String fieldName) {
        try {
            return lookupProvider.getPrivateLookup( o )
                    .unreflectVarHandle( o.getClass().getDeclaredField( fieldName ) )
                    .get( o );
        }
        catch (Throwable e) {
            throw new RuntimeException( e );
        }
    }
}
