package com.example.main;

import com.example.beans.MyEntity;

import fieldreader.spec.FieldReaderApi;
import fieldreader.spec.FieldValueReader;

public class FieldReaderTest {

    public static void main(String[] args) {
        FieldValueReader fieldValueReader = FieldReaderApi.getFieldValueReader();
        Object value = fieldValueReader.getFieldValue( new MyEntity( "bob" ), "name" );
        assert "bob".equals( value );
    }
}
