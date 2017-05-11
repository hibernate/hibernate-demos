package com.canoo.validation.sample2;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

/**
 *
 * @author Hendrik Ebbers
 */
public class Model {

    @NotEmpty
    private StringProperty name = new SimpleStringProperty();

    private Property<@Min(50) Integer> count = new SimpleObjectProperty<>();

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public Integer getCount() {
        return count.getValue();
    }

    public Property<Integer> countProperty() {
        return count;
    }

    public void setCount(Integer count) {
        this.count.setValue(count);
    }
}
