package com.canoo.validation.sample4;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author Hendrik Ebbers
 */
public class ViewController implements Initializable {

    @FXML
    private TextField nameField;

    @FXML
    private Slider countSlider;

    @FXML
    private ImageView nameErrorNode;

    @FXML
    private ImageView countErrorNode;

    @FXML
    private ListView<String> tagListView;

    @FXML
    private ImageView tagErrorNode;

    @FXML
    private TextField tagNameField;

    @FXML
    private Button addTagButton;

    public void initialize(final URL location, final ResourceBundle resources) {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Model model = new Model();
        final Validator validator = factory.getValidator();


        nameField.textProperty().bindBidirectional(model.nameProperty());
        model.countProperty().bind(Bindings.createObjectBinding(() ->
                        countSlider.valueProperty().intValue(),
                countSlider.valueProperty()));
        tagListView.itemsProperty().bind(model.tagsProperty());
        tagListView.setCellFactory(e -> new ValidatorCell(validator));
        addTagButton.setOnAction(e -> model.tagsProperty().add(tagNameField.getText()));


        nameErrorNode.visibleProperty().bind(Bindings.createBooleanBinding(() ->
                        !validator.validateProperty(model, "name").isEmpty(),
                model.nameProperty()));

        countErrorNode.visibleProperty().bind(Bindings.createBooleanBinding(() ->
                        !validator.validateProperty(model, "count").isEmpty(),
                model.countProperty()));

        tagErrorNode.visibleProperty().bind(Bindings.createBooleanBinding(() ->
                        !validator.validateProperty(model, "tags").isEmpty(),
                model.tagsProperty()));
    }
}