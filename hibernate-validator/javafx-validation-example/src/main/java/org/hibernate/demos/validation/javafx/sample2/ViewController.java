package org.hibernate.demos.validation.javafx.sample2;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.demos.validation.javafx.sample1.Model;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

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
    private Label validateMessageLabel;

    public void initialize(final URL location, final ResourceBundle resources) {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Model model = new Model();
        final Validator validator = factory.getValidator();

        nameField.textProperty().bindBidirectional(model.nameProperty());

        model.countProperty().bind(Bindings.createObjectBinding(() ->
                        countSlider.valueProperty().intValue()
                , countSlider.valueProperty()));


        validateMessageLabel.textProperty().bind(Bindings.createStringBinding(() -> {
            final Set<ConstraintViolation<Model>> violations = validator.validate(model);
            return violations.stream().map(v -> v.getMessage()).reduce("", (a, b) -> a + System.lineSeparator() + b);
        }, model.nameProperty(), model.countProperty()));
    }
}