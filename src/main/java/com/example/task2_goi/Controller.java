package com.example.task2_goi;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ResourceBundle;

public class Controller implements Initializable {


    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private Button btn_next, btn_rest, btn_done, btn_complete;
    @FXML
    private Label q1, q2, q3, q4, q5, q6, q7, q8, q9, q10;
    @FXML
    private Label label_all;
    @FXML
    private Label label_no;
    @FXML
    private ImageView image_qr;

    private int countALLWind1 = 0;
    private int countALLWind2 = 0;
    private int countALLWind3 = 0;
    private Queue<Integer> queue1,queue2 = new LinkedList<>();
   // private Queue<Integer> queue2 = new LinkedList<>();

    public void updateScreen() {
        // Retrieve data from the database and display it on the screen
        queue1 = MyJDBC.getFromDatabase();
        q1.setText(String.valueOf(queue1.poll()));
        q2.setText(String.valueOf(queue1.poll()));
        q3.setText(String.valueOf(queue1.poll()));
        q4.setText(String.valueOf(queue1.poll()));
        q5.setText(String.valueOf(queue1.poll()));
        q6.setText(String.valueOf(queue1.poll()));
        q7.setText(String.valueOf(queue1.poll()));
        q8.setText(String.valueOf(queue1.poll()));
        q9.setText(String.valueOf(queue1.poll()));
        q10.setText(String.valueOf(queue1.poll()));
    }

    @FXML
    void Complete(ActionEvent event) {
        btn_next.setDisable(false);
        label_no.setText("");
    }

    @FXML
    void Next(ActionEvent event) {
        // Get the next item from the queue and remove it from the database
        queue2 = MyJDBC.getFromDatabase();
        if (queue2.peek() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("The queue is empty");
            alert.showAndWait();
        } else {
            label_no.setText(String.valueOf(queue2.peek()));
            MyJDBC.executeQuery("DELETE FROM basic_table WHERE id = " + queue2.poll());
            updateScreen();
            btn_next.setDisable(true);
            countALLWind1++;
            label_all.setText(String.valueOf(countALLWind1));
        }
    }

    @FXML
    void Rest(ActionEvent event) {
        // Reset the AUTO_INCREMENT value in the database
        if (MyJDBC.checkDatabaseCount() == 0) {
            MyJDBC.executeQuery("ALTER TABLE basic_table AUTO_INCREMENT = 1");
            countALLWind1 = 0;
            label_all.setText(String.valueOf(countALLWind1));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Reset the AUTO_INCREMENT");
            alert.setHeaderText("DONE!");
            alert.setContentText("You can now start adding from 1");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("You can't do that because the Database is not empty");
            alert.showAndWait();
        }
    }

    @FXML
    void Done(ActionEvent event) throws Exception {
        String choice = getChoice(null);
        updateScreen();

        if (choice == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please choose your phone type");
            alert.showAndWait();
        } else {
            // Insert the choice into the database and generate a QR code then display it
            MyJDBC.executeQuery("INSERT INTO `basic_table` (`id`, `type`) VALUES (NULL, '" + choice + "');");
            GenerateQR.generateQR();
            image_qr.setVisible(true);
            image_qr.setImage(GenerateQR.getQRCodeImage());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("DONE!");
            alert.setHeaderText("Scan this QR code and press OK");
            alert.setContentText("Thanks for your patience \uD83D\uDC96");

            ButtonType okButton = ButtonType.OK;
            alert.getButtonTypes().setAll(okButton);

            // Wait for the OK button to be clicked and hide the QR code
            alert.showAndWait().ifPresent(response -> {
                if (response == okButton) {
                    image_qr.setVisible(false);
                    updateScreen();
                }
            });
        }
    }

    String getChoice(ActionEvent event) {
        return comboBox.getValue();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBox.setItems(FXCollections.observableArrayList("Apple", "Samsung", "Huawei", "Poco"));
        comboBox.setOnAction(this::getChoice);
        updateScreen();
    }
}
