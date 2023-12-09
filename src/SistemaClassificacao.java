import java.util.List;

class SistemaClassificacao {
    private ArvoreAVL arvore;
    private MergeSort mergeSort;

    public SistemaClassificacao() {
        this.arvore = new ArvoreAVL();
        this.mergeSort = new MergeSort();
    }

    public boolean adicionarEstudante(Estudante estudante) {
        if (estudante == null || arvore.buscar(estudante.getNome()) != null) {
            return false;
        }
        arvore.inserir(estudante);
        return true;
    }

    public boolean removerEstudante(String nome) {
        return arvore.remover(nome) != null;
    }

    public Estudante buscarEstudante(String nome) {
        return arvore.buscar(nome);
    }

    public List<Estudante> classificarEstudantes() {
        List<Estudante> estudantes = arvore.paraLista();
        return mergeSort.ordenarPorNota(estudantes);
    }
}