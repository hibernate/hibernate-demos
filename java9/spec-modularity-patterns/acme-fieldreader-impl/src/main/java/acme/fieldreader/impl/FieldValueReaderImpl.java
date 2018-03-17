package acme.fieldreader.impl;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;

import fieldreader.spec.FieldValueReader;
import fieldreader.spec.bootstrap.BootstrapDelegate.PackageOpener;

public class FieldValueReaderImpl implements FieldValueReader {

    private final ClassValue<Lookup> lookups;

    public FieldValueReaderImpl(PackageOpener moduleOpener) {
        this.lookups = new ClassValue<Lookup>() {

            @Override
            protected Lookup computeValue(Class<?> type) {
                if ( !getClass().getModule().canRead( type.getModule() ) ) {
                    getClass().getModule().addReads( type.getModule() );
                }

                moduleOpener.openPackageIfNeeded(
                        type.getModule(), type.getPackageName(), FieldValueReaderImpl.class.getModule()
                );

                try {
                    return MethodHandles.privateLookupIn( type, MethodHandles.lookup() );
                }
                catch (IllegalAccessException e) {
                    throw new RuntimeException( e );
                }
            }
        };
    }

    @Override
    public Object getFieldValue(Object o, String fieldName) {
        try {
            return lookups.get( o.getClass() )
                    .unreflectVarHandle( o.getClass().getDeclaredField( fieldName ) )
                    .get( o );
        }
        catch (Exception e) {
            throw new RuntimeException( e );
        }
    }
}
