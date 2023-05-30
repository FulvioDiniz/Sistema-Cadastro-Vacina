package poov.cadastrovacina.controller;



import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import poov.cadastrovacina.App;
import poov.cadastrovacina.dao.ConexaoFactoryPostgreSQL;
import poov.cadastrovacina.dao.VacinaDAO;
import poov.cadastrovacina.dao.core.ConnectionFactory;
import poov.cadastrovacina.dao.core.DAOFactory;
import poov.cadastrovacina.model.Situacao;
import poov.cadastrovacina.model.Vacina;
import poov.cadastrovacina.model.filter.VacinaFilter;

public class CrudVacinaController implements Initializable {

    @FXML
    private TableView<Vacina> vacinaTableView;

    @FXML
    private TableColumn<Vacina, Long> codigoTableColumn;

    @FXML
    private TableColumn<Vacina, String> nomeTableColumn;

    @FXML
    private TableColumn<Vacina, String> descricaoTableColumn;

    @FXML
    private TextField codigoTextField;

    @FXML
    private TextField descricaoTextField;

    @FXML
    private TextField nomeTextField;

    @FXML
    private Button editarButton;

    @FXML
    private Button fecharButton;

    @FXML
    private Button novaButton;

    @FXML
    private Button pesquisarButton;

    @FXML
    private Button limparButton;

    @FXML
    private Button removerButton;

    private Stage stageCadastro;
    private Stage stageAlterar;
    private TelaAlterarVacinaController telaAlterarVacinaController;

    private DAOFactory factory;

    public CrudVacinaController() {
        ConnectionFactory conexaoFactory = new ConexaoFactoryPostgreSQL("localhost:5433/poov", "postgres", "12345");
        factory = new DAOFactory(conexaoFactory);
    }

    @FXML
    void editarButtonClicado(ActionEvent event) throws SQLException {
        if (vacinaTableView.getSelectionModel().getSelectedIndex() != -1) {
            Vacina vacina = vacinaTableView.getSelectionModel().getSelectedItem();
            telaAlterarVacinaController.setVacina(vacina);
            if (stageAlterar.getOwner() == null) {
                stageAlterar.initOwner(((Button) event.getSource()).getScene().getWindow());
            }
            stageAlterar.showAndWait();
            pesquisarButtonClicado(event);
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Alteração");
            alert.setHeaderText("Alteração de Vacina");
            alert.setContentText("Selecione uma vacina na tabela para alterar!");
            alert.showAndWait();
        }
    }

    @FXML
    void limparButtonClicado(ActionEvent event) {
        codigoTextField.setText("");
        nomeTextField.setText("");
        descricaoTextField.setText("");
    }

    @FXML
    void fecharButtonClicado(ActionEvent event) {
        ((Button) event.getSource()).getScene().getWindow().hide();
    }

    @FXML
    void novaButtonClicado(ActionEvent event) {
        if (stageCadastro.getOwner() == null) {
            stageCadastro.initOwner(((Button) event.getSource()).getScene().getWindow());
        }
        stageCadastro.showAndWait();
    }

    @FXML
    void pesquisarButtonClicado(ActionEvent event) throws SQLException {

        VacinaFilter filtro = new VacinaFilter();
        if (!codigoTextField.getText().isBlank()) {
            filtro.setCodigo(Long.parseLong(codigoTextField.getText()));
        }
        if (!nomeTextField.getText().isBlank()) {
            filtro.setNome(nomeTextField.getText());
        }
        if (!descricaoTextField.getText().isBlank()) {
            filtro.setDescricao(descricaoTextField.getText());
        }
        try {
            factory.abrirConexao();
            VacinaDAO dao = factory.getDAO(VacinaDAO.class);
            List<Vacina> vacinas = dao.pesquisar(filtro);
            vacinaTableView.getItems().clear();
            vacinaTableView.getItems().addAll(vacinas);
        } finally {
            factory.fecharConexao();
        }
    }

    @FXML
    void removerButtonClicado(ActionEvent event) throws SQLException {
        if (vacinaTableView.getSelectionModel().getSelectedIndex() != -1) {
            Vacina vacina = vacinaTableView.getSelectionModel().getSelectedItem();
            ButtonType sim = new ButtonType("Sim", ButtonBar.ButtonData.OK_DONE);
            ButtonType nao = new ButtonType("Não", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(AlertType.CONFIRMATION,
                    "Você tem certeza que quer remover a vacina " + vacina.getNome() + "?",
                    sim, nao);
            alert.setTitle("Remoção");
            alert.setHeaderText("Remoção de Vacina");

            Optional<ButtonType> option = alert.showAndWait();
            System.out.println(option);
            if (option.get().equals(sim)) {
                try {
                    factory.abrirConexao();
                    VacinaDAO dao = factory.getDAO(VacinaDAO.class);
                    vacina.setSituacao(Situacao.INATIVO);
                    dao.update(vacina);
                    pesquisarButtonClicado(event);
                } finally {
                    factory.fecharConexao();
                }
            }
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Remoção");
            alert.setHeaderText("Remoção de Vacina");
            alert.setContentText("Selecione uma vacina na tabela para remover!");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        codigoTableColumn.setCellValueFactory(new PropertyValueFactory<Vacina, Long>("codigo"));
        nomeTableColumn.setCellValueFactory(new PropertyValueFactory<Vacina, String>("nome"));
        descricaoTableColumn.setCellValueFactory(new PropertyValueFactory<Vacina, String>("descricao"));

        vacinaTableView.setPlaceholder(new Label("Não existem Vacinas para serem exibidas."));

        TextFormatter<String> formatterApenasDigitos = new TextFormatter<>(change -> {
            if (!change.isContentChange()) {
                return change;
            }
            String text = change.getControlNewText();
            System.out.println(text);
            if (text.length() == 0) { // permite campo vazio
                return change;
            } else { // verifica se o texto, com a mudança, é um long válido
                try {
                    Long.parseLong(text);
                } catch (NumberFormatException e) {
                    return null;
                }
            }
            return change;
        });
        codigoTextField.setTextFormatter(formatterApenasDigitos);

        Parent parent;
        FXMLLoader fxmlLoader;
        try {
            stageCadastro = new Stage();
            fxmlLoader = new FXMLLoader(App.class.getResource("/poov/cadastrovacina/TelaCadastroVacina.fxml"));
            parent = fxmlLoader.load();
            Scene sceneCadastro = new Scene(parent);
            stageCadastro.setScene(sceneCadastro);
            stageCadastro.setTitle("Cadastro de Vacina");
            stageCadastro.setResizable(false);
            stageCadastro.initModality(Modality.WINDOW_MODAL);

            stageAlterar = new Stage();
            fxmlLoader = new FXMLLoader(App.class.getResource("/poov/cadastrovacina/TelaAlterarVacina.fxml"));
            parent = fxmlLoader.load();
            telaAlterarVacinaController = fxmlLoader.getController();
            telaAlterarVacinaController.setDAOFactory(factory);
            Scene sceneAlterar = new Scene(parent);
            stageAlterar.setScene(sceneAlterar);
            stageAlterar.setTitle("Alteração de Vacina");
            stageAlterar.setResizable(false);
            stageAlterar.initModality(Modality.WINDOW_MODAL);
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.initModality(Modality.WINDOW_MODAL);
            alert.setTitle("ERRO");
            alert.setHeaderText("Erro");
            alert.setContentText("Erro carregando a aplicação!");
            alert.showAndWait();
            // closes the javafx application
            Platform.exit();
        }

    }

}
