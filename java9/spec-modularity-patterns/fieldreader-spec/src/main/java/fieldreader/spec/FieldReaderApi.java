package fieldreader.spec;

import java.util.ServiceLoader;

import fieldreader.spec.bootstrap.BootstrapDelegate;
import fieldreader.spec.bootstrap.BootstrapDelegate.PackageOpener;

public class FieldReaderApi {

    private static final PackageOpener PACKAGE_OPENER = new PackageOpenerImpl();

    public static FieldValueReader getFieldValueReader() {
        ServiceLoader<BootstrapDelegate> loader = ServiceLoader.load( BootstrapDelegate.class );

        return loader.findFirst()
                .orElseThrow( () -> new IllegalStateException( "No provider of " + BootstrapDelegate.class.getName() + " available" ) )
                .getFieldValueReader( PACKAGE_OPENER );
    }

    private static class PackageOpenerImpl implements BootstrapDelegate.PackageOpener {

        @Override
        public void openPackageIfNeeded(Module targetModule, String targetPackage, Module specImplModule) {
            if ( !targetModule.isOpen( targetPackage, specImplModule ) ) {
                targetModule.addOpens( targetPackage, specImplModule );
            }
        }
    }
}
