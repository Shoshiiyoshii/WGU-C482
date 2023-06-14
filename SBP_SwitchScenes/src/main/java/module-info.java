module thomasmccue.sbp_switchscenes {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens thomasmccue.sbp_switchscenes to javafx.fxml;
    exports thomasmccue.sbp_switchscenes;
}