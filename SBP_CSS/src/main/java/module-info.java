module thomasmccue.scenebuilderpractice2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens thomasmccue.scenebuilderpractice2 to javafx.fxml;
    exports thomasmccue.scenebuilderpractice2;
    exports thomasmccue.scenebuilderpractice2.Control;
    opens thomasmccue.scenebuilderpractice2.Control to javafx.fxml;
}