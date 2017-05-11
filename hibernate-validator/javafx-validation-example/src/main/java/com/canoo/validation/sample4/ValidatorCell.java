package com.canoo.validation.sample4;

import javafx.scene.control.Button;
import javafx.scene.control.ListCell;

import javax.validation.Validator;
import java.util.Objects;

/**
 *
 * @author Hendrik Ebbers
 */
public class ValidatorCell extends ListCell<String> {

    private final Validator validator;

    private final Button remove = new Button("x");

    public ValidatorCell(final Validator validator) {
        this.validator = Objects.requireNonNull(validator);
        remove.setOnAction(e -> getListView().getItems().remove(getItem()));
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        setText(item);

        if(!empty && item != null) {
            setGraphic(remove);
        } else {
            setGraphic(null);
        }
    }
}
