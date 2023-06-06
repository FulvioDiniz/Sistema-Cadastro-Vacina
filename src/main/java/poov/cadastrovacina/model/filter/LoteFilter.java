package poov.cadastrovacina.model.filter;

import java.time.LocalDate;

import poov.cadastrovacina.model.Situacao;
import poov.cadastrovacina.model.Vacina;

public class LoteFilter {
    private Long codigo;
    private LocalDate Validade;
    private int nroDosesAtual;
    private int nroDosesLote;
    private Vacina vacina;
    private Situacao situacao = Situacao.ATIVO;
    private String descricao;
    private String nomeVacina;

    public LoteFilter() {
    }

    public String getNomeVacina() {
        return nomeVacina;
    }

    public void setNomeVacina(String nomeVacina) {
        this.nomeVacina = nomeVacina;
    }

    public LoteFilter(LocalDate Validade, int nroDosesAtual, int nroDosesLote, Vacina vacina) {
        this.Validade = Validade;
        this.nroDosesAtual = nroDosesAtual;
        this.nroDosesLote = nroDosesLote;
        this.vacina = vacina;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long i) {
        this.codigo = i;
    }

    public LocalDate getValidade() {
        return Validade;
    }

    public void setValidade(LocalDate validade) {
        Validade = validade;
    }

    public int getNroDosesAtual() {
        return nroDosesAtual;
    }

    public void setNroDosesAtual(int nroDosesAtual) {
        this.nroDosesAtual = nroDosesAtual;
    }

    public int getNroDosesLote() {
        return nroDosesLote;
    }

    public void setNroDosesLote(int nroDosesLote) {
        this.nroDosesLote = nroDosesLote;
    }

    public Vacina getVacina() {
        return vacina;
    }

    public void setVacina(Vacina vacina) {
        this.vacina = vacina;
    }

    public void setVacinaNome(String nome) {
        this.vacina.setNome(nome);
    }

    public void setVacinaDescricao(String descricao) {
        this.vacina.setDescricao(descricao);
    }

    public String getVacinaNome() {
        return vacina.getNome();
    }

    public String getVacinaDescricao() {
        return this.vacina.getDescricao();
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

 





}
