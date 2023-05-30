package poov.cadastrovacina.controller;

import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import poov.cadastrovacina.dao.ConexaoFactoryPostgreSQL;
import poov.cadastrovacina.dao.VacinaDAO;
import poov.cadastrovacina.dao.core.ConnectionFactory;
import poov.cadastrovacina.dao.core.DAO;
import poov.cadastrovacina.dao.core.DAOFactory;
import poov.cadastrovacina.model.Vacina;

public class TelaCadastroVacinaController {

    @FXML
    private Button buttonCadastrar;

    @FXML
    private Button buttonFechar;

    @FXML
    private Button buttonLimpar;

    @FXML
    private TextArea textAreaDescricao;

    @FXML
    private TextField textFieldCodigo;

    @FXML
    private TextField textFieldNome;

    @FXML
    void cadastrarClicado(ActionEvent event) throws SQLException {
        if (ehValido()) {
            Vacina vacina = new Vacina();
            vacina.setNome(textFieldNome.getText());
            vacina.setDescricao(textAreaDescricao.getText());

            ConnectionFactory conexaoFactory = 
                new ConexaoFactoryPostgreSQL("localhost:5433/poov", "postgres", "12345");
            DAOFactory factory = new DAOFactory(conexaoFactory);
            try {
                factory.abrirConexao();
                DAO<Vacina, Long> dao = factory.getDAO(VacinaDAO.class);
                dao.create(vacina);
                limparFormulario();
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Cadastro");
                alert.setHeaderText("Sucesso no Cadastro");
                alert.setContentText("Vacina cadastrada com sucesso!");
                alert.showAndWait();
            } finally {
                factory.fecharConexao();
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Problema");
            alert.setHeaderText("Problema no Cadastro");
            alert.setContentText("Preencha todos os campos para poder cadastrar.");
            alert.showAndWait();
        }
    }

    private boolean ehValido() {
        return !textFieldNome.getText().isBlank() &&
                !textAreaDescricao.getText().isBlank();
    }

    @FXML
    void fecharClicado(ActionEvent event) {
        ((Button) event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    void limparClicado(ActionEvent event) {
        limparFormulario();
    }

    public void limparFormulario() {
        textFieldCodigo.setText("");
        textFieldNome.setText("");
        textAreaDescricao.setText("");
    }

}
