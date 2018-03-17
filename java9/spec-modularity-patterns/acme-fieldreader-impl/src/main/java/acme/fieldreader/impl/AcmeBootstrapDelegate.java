package acme.fieldreader.impl;

import fieldreader.spec.FieldValueReader;
import fieldreader.spec.bootstrap.BootstrapDelegate;

public class AcmeBootstrapDelegate implements BootstrapDelegate {

    @Override
    public FieldValueReader getFieldValueReader(PackageOpener opener) {
        return new FieldValueReaderImpl( opener );
    }
}
