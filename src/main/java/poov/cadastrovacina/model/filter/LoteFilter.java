package poov.cadastrovacina.model.filter;

import java.time.LocalDate;

import poov.cadastrovacina.model.Vacina;

public class LoteFilter {
    private Long codigo;
    private LocalDate Validade;
    private int nroDosesAtual;
    private int nroDosesLote;  
    private Vacina vacina;

    public LoteFilter() {
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

    




}
