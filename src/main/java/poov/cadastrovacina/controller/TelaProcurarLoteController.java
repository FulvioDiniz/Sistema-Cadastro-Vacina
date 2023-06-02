package poov.cadastrovacina.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import poov.cadastrovacina.dao.LoteDAO;
import poov.cadastrovacina.dao.core.DAOFactory;
import poov.cadastrovacina.model.Lote;
import poov.cadastrovacina.model.Vacina;
import poov.cadastrovacina.model.filter.LoteFilter;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class TelaProcurarLoteController {

    @FXML
    private Button ButtonPesquisar;

    @FXML
    private TableColumn<Vacina, String> DescricaoVacina;

    @FXML
    private TableColumn<Vacina, String> NomeVacina;

    @FXML
    private TableColumn<Lote, Integer> QuantidadeLote;

    @FXML
    private TableView<Lote> TableLote;

    @FXML
    private TableColumn<Lote, LocalDate> ValidadeVacina;

    @FXML
    private TextField codigoTextField;


    private DAOFactory factory;

    public void setDAOFactory (DAOFactory factory) {
        this.factory = factory;
    }

    public boolean ehValido() {
        return !codigoTextField.getText().isBlank();
    }

    @FXML
    void ButtonpesquiasrClicado(ActionEvent event) {
        TableLote.setPlaceholder(new Label("Não existem Lotes para serem exibidas."));
        if (ehValido()) {
            LoteFilter filter = new LoteFilter();
            if(!codigoTextField.getText().isBlank()) {
                filter.setCodigo(Long.parseLong(codigoTextField.getText()));
            }
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

}
