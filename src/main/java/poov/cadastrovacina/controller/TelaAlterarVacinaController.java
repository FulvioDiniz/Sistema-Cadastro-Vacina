package poov.cadastrovacina.controller;

import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import poov.cadastrovacina.dao.VacinaDAO;
import poov.cadastrovacina.dao.core.DAOFactory;
import poov.cadastrovacina.model.Vacina;

public class TelaAlterarVacinaController {

    @FXML
    private Button alterarButton;

    @FXML
    private TextField codigoTextField;

    @FXML
    private TextArea descricaoTextArea;

    @FXML
    private Button fecharButton;

    @FXML
    private TextField nomeTextField;

    private Vacina vacina;
    private DAOFactory factory;

    public void setDAOFactory (DAOFactory factory) {
        this.factory = factory;
    }

    public void setVacina(Vacina vacina) {
        this.vacina = vacina;
        codigoTextField.setText("" + vacina.getCodigo());
        nomeTextField.setText(vacina.getNome());
        descricaoTextArea.setText(vacina.getDescricao());
    }

    public boolean ehValido() {
        return !codigoTextField.getText().isBlank() &&
               !nomeTextField.getText().isBlank() &&
               !descricaoTextArea.getText().isBlank();
    }

    @FXML
    void alterarButtonClicado(ActionEvent event) throws SQLException {
        if (ehValido()) {
            try {
                factory.abrirConexao();
                VacinaDAO dao = factory.getDAO(VacinaDAO.class);
                vacina.setNome(nomeTextField.getText());
                vacina.setDescricao(descricaoTextArea.getText());
                dao.update(vacina);

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Alterar");
                alert.setHeaderText("Sucesso na Alteração");
                alert.setContentText("Vacina alterada com sucesso!");
                alert.showAndWait();

                fecharButtonClicado(event);
            } finally {
                factory.fecharConexao();
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Problema");
            alert.setHeaderText("Problema na Alteração");
            alert.setContentText("Preencha todos os campos para poder alterar.");
            alert.showAndWait();
        }
    }

    @FXML
    void fecharButtonClicado(ActionEvent event) {
        ((Button) event.getSource()).getScene().getWindow().hide();
    }

}
