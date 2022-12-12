module com.example.rgr_pps {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.rgr_pps to javafx.fxml;
    exports com.example.rgr_pps;
}