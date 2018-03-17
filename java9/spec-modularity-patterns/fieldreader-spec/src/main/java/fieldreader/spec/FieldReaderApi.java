package fieldreader.spec;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.util.ServiceLoader;

import fieldreader.spec.bootstrap.BootstrapDelegate;
import fieldreader.spec.bootstrap.BootstrapDelegate.LookupProvider;

public class FieldReaderApi {

    private static final LookupProvider LOOKUP_PROVIDER = new LookupProviderImpl();

    public static FieldValueReader getFieldValueReader() {
        ServiceLoader<BootstrapDelegate> loader = ServiceLoader.load( BootstrapDelegate.class );

        return loader.findFirst()
                .orElseThrow( () -> new IllegalStateException( "No provider of " + BootstrapDelegate.class.getName() + " available" ) )
                .getFieldValueReader( LOOKUP_PROVIDER );
    }

    private static class LookupProviderImpl implements BootstrapDelegate.LookupProvider {

        private final ClassValue<Lookup> classValue;

        private LookupProviderImpl() {
            classValue = new ClassValue<Lookup>() {

                @Override
                protected Lookup computeValue(Class<?> type) {
                    FieldValueReader.class.getModule().addReads( type.getModule() );
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
        public Lookup getPrivateLookup(Object o) {
            return classValue.get( o.getClass() );
        }
    }
}
