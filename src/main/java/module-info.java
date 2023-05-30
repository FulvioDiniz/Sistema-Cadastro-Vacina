module JavaFXApplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires java.sql;
    
    opens poov.cadastrovacina to javafx.fxml;
    opens poov.cadastrovacina.controller to javafx.fxml, javafx.graphics;
    opens poov.cadastrovacina.dao to javafx.fxml, javafx.graphics;
    opens poov.cadastrovacina.dao.core to javafx.fxml, javafx.graphics;
    opens poov.cadastrovacina.model to javafx.base, javafx.fxml, javafx.graphics;
    exports poov.cadastrovacina;
}
