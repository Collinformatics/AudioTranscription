module com.example.audiotranscription {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.audiotranscription to javafx.fxml;
    exports com.example.audiotranscription;
}