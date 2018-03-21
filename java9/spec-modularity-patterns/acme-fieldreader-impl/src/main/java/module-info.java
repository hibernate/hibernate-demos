module acme.fieldreader.impl {
    requires fieldreader.spec;
    provides fieldreader.spec.bootstrap.FieldReaderProvider with acme.fieldreader.impl.AcmeFieldReaderProvider;
}