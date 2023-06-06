package poov.cadastrovacina.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import poov.cadastrovacina.dao.core.GenericJDBCDAO;
import poov.cadastrovacina.model.Lote;
import poov.cadastrovacina.model.Situacao;
import poov.cadastrovacina.model.Vacina;
import poov.cadastrovacina.model.filter.LoteFilter;


public class LoteDAO extends GenericJDBCDAO<Lote, Long> {

    public LoteDAO(Connection connection) {
        super(connection);
    }

    private static final String FIND_ALL_QUERY = "SELECT codigo, nome, descricao, situacao FROM LOTE WHERE situacao = 'ATIVO' ";
    private static final String FIND_BY_KEY_QUERY = FIND_ALL_QUERY + "AND codigo=? ";
    private static final String FIND_BY_NAME_LIKE_QUERY = FIND_ALL_QUERY + "AND upper(nome) like upper(?)";
    private static final String UPDATE_QUERY = "UPDATE lote SET nome=?, descricao=?, situacao=? WHERE codigo=?";
    private static final String CREATE_QUERY = "INSERT INTO LOTE (nome, descricao, situacao) VALUES (?, ?, ?)";
    private static final String REMOVE_QUERY = "DELETE FROM LOTE WHERE codigo=?";
    private static final String FIND_BY_LOTE_VACINA = "SELECT lote.validade, lote.situacao, lote.nro_Doses_Atual, vacina.nome AS nomevacina, vacina.descricao, vacina.codigo FROM LOTE INNER JOIN VACINA ON vacina.codigo = lote.codigo_vacina WHERE vacina.Situacao = 'ATIVO'";

    @Override
    protected Lote toEntity(ResultSet resultSet) throws SQLException {
        Lote lote = new Lote();
        lote.setCodigo(resultSet.getLong("codigo"));
        lote.setDescicao(resultSet.getString("descricao"));
        lote.setNomeVacina(resultSet.getString("nomevacina"));
        //lote.setNroDosesAtual(resultSet.getInt("nroDosesAtual"));
        lote.setValidade(resultSet.getDate("validade"));        

        Vacina vacina = new Vacina();
        vacina.setCodigo(resultSet.getLong("codigo"));
        vacina.setNome(resultSet.getString("nomevacina"));
        vacina.setDescricao(resultSet.getString("descricao"));

        lote.setVacina(vacina);

        
        if (resultSet.getString("situacao").equals("ATIVO")) {
            lote.setSituacao(Situacao.ATIVO);
        } else {
            lote.setSituacao(Situacao.INATIVO);
        }
        return lote;
    }

    @Override
    protected void addParameters(PreparedStatement resultSet, Lote entity) throws SQLException {
        // resultSet.setString(1, entity.get());
        // resultSet.setString(2, entity.getDescricao());
        // resultSet.setString(3, entity.getSituacao().toString());
        // if (entity.getCodigo() != null) {
        //     resultSet.setLong(4, entity.getCodigo());
        // }
    }

    @Override
    protected String findByKeyQuery() {
        return FIND_BY_KEY_QUERY;
    }

    @Override
    protected String findAllQuery() {
        return FIND_ALL_QUERY;
    }

    @Override
    protected String updateQuery() {
        return UPDATE_QUERY;
    }

    @Override
    protected String createQuery() {
        return CREATE_QUERY;
    }

    @Override
    protected String removeQuery() {
        return REMOVE_QUERY;
    }

    public List<Lote> findByNameVacina(String nome) {
        try {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME_LIKE_QUERY);
            statement.setString(1, "%" + nome + "%");
            ResultSet resultSet = statement.executeQuery();
            return toEntityList(resultSet);
        } catch (SQLException e) {
            showSQLException(e);
        }
        return new ArrayList<Lote>();
    }

    @Override
    protected void setKeyInStatementFromEntity(PreparedStatement statement, Lote entity) throws SQLException {
        statement.setLong(1, entity.getCodigo());
    }

    @Override
    protected void setKeyInStatement(PreparedStatement statement, Long key) throws SQLException {
        statement.setLong(1, key);
    }

    @Override
    protected void setKeyInEntity(ResultSet rs, Lote entity) throws SQLException {
        entity.setCodigo(rs.getLong(1));
    }

    public List<Lote> findByLoteVacina(Long codigo) {
        try {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_LOTE_VACINA);
            statement.setLong(1, codigo);
            ResultSet resultSet = statement.executeQuery();
            return toEntityList(resultSet);
        } catch (SQLException e) {
            showSQLException(e);
        }
        return new ArrayList<Lote>();
    }

    public List<Lote> pesquisar(LoteFilter filtro) {
        int parametro = 1;
        String query = FIND_BY_LOTE_VACINA;

        if (filtro.getCodigo() != null) {
            query += "AND vacina.codigo = ?";
        } 
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            if (filtro.getCodigo() != null) {
                statement.setLong(1, filtro.getCodigo());
                parametro++;
            }
            System.out.println(statement.toString());
            ResultSet resultSet = statement.executeQuery();
            return toEntityList(resultSet);
        } catch (SQLException e) {
            showSQLException(e);
        }
        return new ArrayList<Lote>();
    }

}
