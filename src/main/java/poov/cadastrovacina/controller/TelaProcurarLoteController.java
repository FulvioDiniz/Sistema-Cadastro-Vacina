package poov.cadastrovacina.controller;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import poov.cadastrovacina.dao.LoteDAO;
import poov.cadastrovacina.dao.core.DAOFactory;
import poov.cadastrovacina.model.Lote;
import poov.cadastrovacina.model.Vacina;
import poov.cadastrovacina.model.filter.LoteFilter;

public class TelaProcurarLoteController implements Initializable {

    @FXML
    private Button ButtonPesquisar;

    @FXML
    private TableColumn<Lote, Integer> CodigoTextField;

    @FXML
    private TableColumn<Lote, String> DescricaoVacina;

    @FXML
    private TableColumn<Lote, String> NomeVacina;

    @FXML
    private TableColumn<Lote, Integer> QuantidadeLote;

    @FXML
    private TableView<Lote> TableLote;

    @FXML
    private TableColumn<Lote, LocalDate> ValidadeVacina;

    @FXML
    private TextField codigoTextField;

    private DAOFactory factory;

    public void setDAOFactory(DAOFactory factory) {
        this.factory = factory;
    }

    public boolean ehValido() {
        return !codigoTextField.getText().isBlank();
    }

    @FXML
    void ButtonpesquiasrClicado(ActionEvent event) {
        if (ehValido()) {
            LoteFilter filter = new LoteFilter();

            try {
                factory.abrirConexao();
                LoteDAO dao = factory.getDAO(LoteDAO.class);
                List<Lote> lotes = dao.pesquisar(filter);
                TableLote.getItems().clear();
                TableLote.getItems().addAll(lotes);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                factory.fecharConexao();
            }
        }
    }

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        ValidadeVacina.setCellValueFactory(new PropertyValueFactory<Lote, LocalDate>("validade"));
        //QuantidadeLote.setCellValueFactory(new PropertyValueFactory<Lote, Integer>("nroDosesAtual"));
        NomeVacina.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVacina().getNome()));
        DescricaoVacina.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVacina().getDescricao()));
        //DescricaoVacina.setCellValueFactory(new PropertyValueFactory<Lote, String>("vacina.descricao"));
        CodigoTextField.setCellValueFactory(new PropertyValueFactory<Lote, Integer>("codigo"));
        System.out.println("TelaProcurarLoteController.initialize()");

        TableLote.setPlaceholder(new Label("Não existem Lotes para serem exibidas."));

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
    }

}
