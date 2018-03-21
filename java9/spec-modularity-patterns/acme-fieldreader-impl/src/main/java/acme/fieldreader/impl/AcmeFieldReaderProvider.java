package acme.fieldreader.impl;

import fieldreader.spec.FieldValueReader;
import fieldreader.spec.bootstrap.FieldReaderProvider;

public class AcmeFieldReaderProvider implements FieldReaderProvider {

    @Override
    public FieldValueReader provideFieldValueReader(PackageOpener opener) {
        return new FieldValueReaderImpl( opener );
    }
}
