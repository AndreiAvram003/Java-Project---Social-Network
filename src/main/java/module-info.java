module com.example.socialnetwork_gui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.example.socialnetwork_gui to javafx.fxml;
    opens com.example.socialnetwork_gui.persistance.model.dtos;
    exports com.example.socialnetwork_gui;
    exports com.example.socialnetwork_gui.presentation.InterfaceGUI;
    opens com.example.socialnetwork_gui.presentation.InterfaceGUI to javafx.fxml;
}