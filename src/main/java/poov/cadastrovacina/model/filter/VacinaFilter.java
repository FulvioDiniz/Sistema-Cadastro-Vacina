package poov.cadastrovacina.model.filter;

public class VacinaFilter {
    
    private Long codigo;
    private String nome;
    private String descricao;
    
    public VacinaFilter() {
    }

    public VacinaFilter(Long codigo, String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return "codigo: " + codigo + "\nnome: " + nome + "\ndescricao: " + descricao;
    }

}
