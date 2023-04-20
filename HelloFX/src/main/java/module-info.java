module thomasmccue.hellofx {
    requires javafx.controls;
    requires javafx.fxml;


    opens thomasmccue.hellofx to javafx.fxml;
    exports thomasmccue.hellofx;
}