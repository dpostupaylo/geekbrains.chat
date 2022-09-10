module com.example.geekbrainschatfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires log4j;

    exports com.gb.chat.fx.client;
    opens com.gb.chat.fx.client to javafx.fxml;
    exports com.gb.chat.fx.server;
    opens com.gb.chat.fx.server to javafx.fxml;
}