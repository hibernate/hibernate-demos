package com.canoo.validation.sample4;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 *
 * @author Hendrik Ebbers
 */
public class Model {

    @NotEmpty
    private final StringProperty name = new SimpleStringProperty();

    private final Property<@Min(50) Integer> count = new SimpleObjectProperty<>();

    @Size(min=1, max=4)
    private final ListProperty<@Size(min=2, max=32) String> tags = new SimpleListProperty<>(FXCollections.observableArrayList());

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

    public ObservableList<String> getTags() {
        return tags.get();
    }

    public ListProperty<String> tagsProperty() {
        return tags;
    }

    public void setTags(ObservableList<String> tags) {
        this.tags.set(tags);
    }
}
