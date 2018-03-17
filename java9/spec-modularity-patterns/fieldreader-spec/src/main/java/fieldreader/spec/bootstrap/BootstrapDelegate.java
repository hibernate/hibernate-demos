package fieldreader.spec.bootstrap;

import fieldreader.spec.FieldValueReader;

public interface BootstrapDelegate {

    FieldValueReader getFieldValueReader(PackageOpener opener);

    public interface PackageOpener {
        void openPackageIfNeeded(Module targetModule, String targetPackage, Module specImplModule);
    }
}
