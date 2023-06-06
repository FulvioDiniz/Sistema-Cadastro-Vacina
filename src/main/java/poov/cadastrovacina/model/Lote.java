package poov.cadastrovacina.model;

import java.sql.Date;
import java.time.LocalDate;

public class Lote {
    private Long codigo;
    private LocalDate Validade;
    private int nroDosesAtual;
    private int nroDosesLote;
    private Vacina vacina;
    private Situacao situacao = Situacao.ATIVO;
    private String descicao;
    private String nomeVacina;
    

    
    public Lote() {
        Validade = LocalDate.now();
        nroDosesAtual = 0;
        nroDosesLote = 0;
        vacina = new Vacina();
    }

    public Lote(LocalDate Validade, int nroDosesAtual, int nroDosesLote, Vacina vacina) {
        this.Validade = Validade;
        this.nroDosesAtual = nroDosesAtual;
        this.nroDosesLote = nroDosesLote;
        this.vacina = vacina;
    }

    public Lote(Long codigo, LocalDate Validade, int nroDosesAtual, int nroDosesLote, Vacina vacina) {
        this.codigo = codigo;
        this.Validade = Validade;
        this.nroDosesAtual = nroDosesAtual;
        this.nroDosesLote = nroDosesLote;
        this.vacina = vacina;
    }

    public Long getCodigo() {
        return codigo;
    }

    public LocalDate getValidade() {
        return Validade;
    }

    public int getNroDosesAtual() {
        return nroDosesAtual;
    }

    public int getNroDosesLote() {
        return nroDosesLote;
    }

    public Vacina getVacina() {
        return vacina;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public void setValidade(LocalDate date) {
        this.Validade = date;
    }

    public void setNroDosesAtual(int nroDosesAtual) {
        this.nroDosesAtual = nroDosesAtual;
    }

    public void setNroDosesLote(int nroDosesLote) {
        this.nroDosesLote = nroDosesLote;
    }

    public void setVacina(Vacina vacina) {
        this.vacina = vacina;
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao situacao){
        this.situacao = situacao;
    }

    


    @Override
    public String toString() {
        return "codigo: " + codigo + "\nValidade: " + Validade + "\nnroDosesAtual: " + nroDosesAtual + "\nnroDosesLote: " + nroDosesLote + "\nvacina: " + vacina;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Lote)) return false;
        Lote lote = (Lote) obj;
        return lote.getCodigo().equals(this.codigo);
    }

    @Override
    public int hashCode() {
        return this.codigo.hashCode();
    }

    public String getDescicao() {
        return descicao;
    }

    public void setDescicao(String descicao) {
        this.descicao = descicao;
    }

    public String getNomeVacina() {
        return nomeVacina;
    }

    public void setNomeVacina(String nomeVacina) {
        this.nomeVacina = nomeVacina;
    }

    public void setValidade(Date date) {
    }
}
