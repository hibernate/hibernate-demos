package org.hibernate.demos.validation.javafx.sample4;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;

/**
 *
 * @author Hendrik Ebbers
 */
@SuppressWarnings("restriction")
public class ViewController implements Initializable {

    @FXML
    private TextField nameField;

    @FXML
    private Slider countSlider;

    @FXML
    private ImageView nameErrorNode;

    @FXML
    private Tooltip nameErrorTooltip;

    @FXML
    private ImageView countErrorNode;

    @FXML
    private Tooltip countErrorTooltip;

    @FXML
    private ListView<String> tagListView;

    @FXML
    private ImageView tagErrorNode;

    @FXML
    private Tooltip tagErrorTooltip;

    @FXML
    private TextField tagNameField;

    @FXML
    private Button addTagButton;

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Model model = new Model();
        final Validator validator = factory.getValidator();

        nameErrorTooltip = new Tooltip();
        Tooltip.install( nameErrorNode, nameErrorTooltip );

        countErrorTooltip = new Tooltip();
        Tooltip.install( countErrorNode, countErrorTooltip );

        tagErrorTooltip = new Tooltip();
        Tooltip.install( tagErrorNode, tagErrorTooltip );

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
        nameErrorTooltip.textProperty().bind(
                Bindings.createStringBinding(
                        () -> validator.validateProperty(model, "name")
                            .stream()
                            .map( cv -> cv.getMessage() )
                            .collect( Collectors.joining() ),
                        model.nameProperty()
                )
        );

        countErrorNode.visibleProperty().bind(Bindings.createBooleanBinding(() ->
                        !validator.validateProperty(model, "count").isEmpty(),
                model.countProperty()));
        countErrorTooltip.textProperty().bind(
                Bindings.createStringBinding(
                        () -> validator.validateProperty(model, "count")
                            .stream()
                            .map( cv -> cv.getMessage() )
                            .collect( Collectors.joining() ),
                        model.countProperty()
                )
        );

        tagErrorNode.visibleProperty().bind(Bindings.createBooleanBinding(() ->
                        !validator.validateProperty(model, "tags").isEmpty(),
                model.tagsProperty()));
        tagErrorTooltip.textProperty().bind(
                Bindings.createStringBinding(
                        () -> validator.validateProperty(model, "tags")
                            .stream()
                            .map( cv -> cv.getMessage() )
                            .collect( Collectors.joining() ),
                        model.tagsProperty()
                )
        );
    }
}
