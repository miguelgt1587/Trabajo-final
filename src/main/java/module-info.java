module org.example.trabajofinal {
    requires javafx.controls;

    opens cinemauq.gui to javafx.graphics;

    exports cinemauq;
    exports cinemauq.gui;
    exports cinemauq.model;
    exports cinemauq.enums;
    exports cinemauq.excepciones;
    exports cinemauq.util;
}
