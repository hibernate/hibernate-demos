package org.hibernate.demos.validation.javafx.sample4;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import javafx.beans.property.ListProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Hendrik Ebbers
 */
@SuppressWarnings("restriction")
public class Model {

    @NotEmpty
    private final StringProperty name = new SimpleStringProperty();

    private final Property<@Min(50) Integer> count = new SimpleObjectProperty<>();

    @Size(min=1, max=4, message="1 to 4 tags required")
    private final ListProperty<@Size(min=2, max=32, message="Invalid tag length") String> tags = new SimpleListProperty<>(FXCollections.observableArrayList());

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
