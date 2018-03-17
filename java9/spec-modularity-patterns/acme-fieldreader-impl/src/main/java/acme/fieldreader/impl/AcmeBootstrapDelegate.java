package acme.fieldreader.impl;

import fieldreader.spec.FieldValueReader;
import fieldreader.spec.bootstrap.BootstrapDelegate;

public class AcmeBootstrapDelegate implements BootstrapDelegate {

    @Override
    public FieldValueReader getFieldValueReader(LookupProvider lookupProvider) {
        return new FieldValueReaderImpl( lookupProvider );
    }
}
