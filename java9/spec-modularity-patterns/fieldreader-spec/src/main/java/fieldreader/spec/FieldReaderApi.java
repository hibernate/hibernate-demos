package fieldreader.spec;

import java.util.ServiceLoader;

import fieldreader.spec.bootstrap.FieldReaderProvider;
import fieldreader.spec.bootstrap.FieldReaderProvider.PackageOpener;

public class FieldReaderApi {

    private static final PackageOpener PACKAGE_OPENER = new PackageOpenerImpl();

    public static FieldValueReader getFieldValueReader() {
        ServiceLoader<FieldReaderProvider> loader = ServiceLoader.load( FieldReaderProvider.class );

        return loader.findFirst()
                .orElseThrow( () -> new IllegalStateException( "No provider of " + FieldReaderProvider.class.getName() + " available" ) )
                .provideFieldValueReader( PACKAGE_OPENER );
    }

    private static class PackageOpenerImpl implements FieldReaderProvider.PackageOpener {

        @Override
        public void openPackageIfNeeded(Module targetModule, String targetPackage, Module specImplModule) {
            if ( !targetModule.isOpen( targetPackage, specImplModule ) ) {
                targetModule.addOpens( targetPackage, specImplModule );
            }
        }
    }
}
