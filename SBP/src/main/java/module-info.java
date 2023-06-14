module thomasmccue.scenebuilderpractice {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens thomasmccue.scenebuilderpractice to javafx.fxml;
    exports thomasmccue.scenebuilderpractice;
    exports thomasmccue.scenebuilderpractice.Controller;
    opens thomasmccue.scenebuilderpractice.Controller to javafx.fxml;
}