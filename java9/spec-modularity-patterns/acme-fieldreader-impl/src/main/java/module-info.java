module acme.fieldreader.impl {
    requires fieldreader.spec;
    provides fieldreader.spec.bootstrap.BootstrapDelegate with acme.fieldreader.impl.AcmeBootstrapDelegate;
}