package fieldreader.spec.bootstrap;

import fieldreader.spec.FieldValueReader;

/**
 * Contract between the field reader API bootstrap mechanism and spec implementations.
 */
public interface FieldReaderProvider {

    FieldValueReader provideFieldValueReader(PackageOpener opener);

    public interface PackageOpener {
        void openPackageIfNeeded(Module targetModule, String targetPackage, Module specImplModule);
    }
}
