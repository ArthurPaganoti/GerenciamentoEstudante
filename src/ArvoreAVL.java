import java.util.ArrayList;
import java.util.List;

class ArvoreAVL {
    private No raiz;

    private class No {
        Estudante estudante;
        No esquerda;
        No direita;
        int altura;

        // Construtor de nó
        No(Estudante estudante) {
            this.estudante = estudante;
            this.altura = 1;
        }
    }

    // Método para inserir um estudante na árvore
    // Retorna false se o estudante for null, caso contrário, insere o estudante e retorna true
    public boolean inserir(Estudante estudante) {
        if (estudante == null) return false;
        raiz = inserirRecursivo(raiz, estudante);
        return true;
    }

    // Método recursivo para inserir um estudante na árvore
    // Este método é chamado pelo método inserir
    private No inserirRecursivo(No no, Estudante estudante) {
        if (no == null) {
            return new No(estudante);
        }

        if (estudante.getNome().compareTo(no.estudante.getNome()) < 0) {
            no.esquerda = inserirRecursivo(no.esquerda, estudante);
        } else if (estudante.getNome().compareTo(no.estudante.getNome()) > 0) {
            no.direita = inserirRecursivo(no.direita, estudante);
        } else {
            // O estudante já existe na árvore, então retornamos o mesmo nó
            return no;
        }

        no.altura = 1 + Math.max(altura(no.esquerda), altura(no.direita));

        int balance = fatorBalanceamento(no);

        if (balance > 1 && estudante.getNome().compareTo(no.esquerda.estudante.getNome()) < 0) {
            return rotacaoLL(no);
        }
        if (balance < -1 && estudante.getNome().compareTo(no.direita.estudante.getNome()) > 0) {
            return rotacaoRR(no);
        }
        if (balance > 1 && estudante.getNome().compareTo(no.esquerda.estudante.getNome()) > 0) {
            no.esquerda = rotacaoRR(no.esquerda);
            return rotacaoLL(no);
        }
        if (balance < -1 && estudante.getNome().compareTo(no.direita.estudante.getNome()) < 0) {
            no.direita = rotacaoLL(no.direita);
            return rotacaoRR(no);
        }

        return no;
    }

    // Método para remover um estudante da árvore pelo nome
    // Retorna null se o estudante não for encontrado, caso contrário, remove o estudante e retorna seus dados
    public Estudante remover(String nome) {
        No estudanteRemovido = removerRecursivo(raiz, nome);
        if (estudanteRemovido != null) {
            return estudanteRemovido.estudante;
        }
        return null;
    }

    // Método recursivo para remover um estudante da árvore
    // Este método é chamado pelo método remover
    private No removerRecursivo(No no, String nome) {
        if (no == null) return null;

        if (nome.equals(no.estudante.getNome())) {
            if (no.esquerda == null) {
                return no.direita;
            } else if (no.direita == null) {
                return no.esquerda;
            } else {
                No temp = minValueNode(no.direita);
                no.estudante = temp.estudante;
                no.direita = removerRecursivo(no.direita, temp.estudante.getNome());
            }
        } else if (nome.compareTo(no.estudante.getNome()) < 0) {
            no.esquerda = removerRecursivo(no.esquerda, nome);
        } else {
            no.direita = removerRecursivo(no.direita, nome);
        }

        if (no == null) return null;

        no.altura = 1 + Math.max(altura(no.esquerda), altura(no.direita));

        int balance = fatorBalanceamento(no);

        if (balance > 1 && fatorBalanceamento(no.esquerda) >= 0) {
            return rotacaoLL(no);
        }
        if (balance < -1 && fatorBalanceamento(no.direita) <= 0) {
            return rotacaoRR(no);
        }
        if (balance > 1 && fatorBalanceamento(no.esquerda) < 0) {
            no.esquerda = rotacaoRR(no.esquerda);
            return rotacaoLL(no);
        }
        if (balance < -1 && fatorBalanceamento(no.direita) > 0) {
            no.direita = rotacaoLL(no.direita);
            return rotacaoRR(no);
        }

        return no;
    }


    // Método para buscar um estudante na árvore pelo nome
    // Retorna null se o estudante não for encontrado, caso contrário, retorna os dados do estudante
    public Estudante buscar(String nome) {
        No estudanteEncontrado = buscarRecursivo(raiz, nome);
        if (estudanteEncontrado != null) {
            return estudanteEncontrado.estudante;
        }
        return null;
    }

    // Método recursivo para buscar um estudante na árvore
    // Este método é chamado pelo método buscar
    private No buscarRecursivo(No no, String nome) {
        if (no == null || no.estudante == null || nome == null) {
            return null;
        }

        if (no.estudante.getNome().equals(nome)) {
            return no;
        } else if (nome.compareTo(no.estudante.getNome()) < 0) {
            return buscarRecursivo(no.esquerda, nome);
        } else {
            return buscarRecursivo(no.direita, nome);
        }
    }

    // Método para obter a altura de um nó
    // Retorna 0 se o nó for null, caso contrário, retorna a altura do nó
    private int altura(No no) {
        if (no == null) return 0;
        return no.altura;
    }

    // Método para calcular o fator de balanceamento de um nó
    // Retorna 0 se o nó for null, caso contrário, retorna a diferença entre as alturas dos filhos esquerdo e direito do nó
    private int fatorBalanceamento(No no) {
        if (no == null) return 0;
        return altura(no.esquerda) - altura(no.direita);
    }

    // Método para realizar uma rotação LL
    // Este método é usado para balancear a árvore quando o fator de balanceamento de um nó é maior que 1 e o fator de balanceamento de seu filho esquerdo é maior ou igual a 0
    private No rotacaoLL(No no) {
        No temp = no.esquerda;
        no.esquerda = temp.direita;
        temp.direita = no;
        no.altura = 1 + Math.max(altura(no.esquerda), altura(no.direita));
        temp.altura = 1 + Math.max(altura(temp.esquerda), altura(temp.direita));
        return temp;
    }

    // Método para realizar uma rotação RR
    // Este método é usado para balancear a árvore quando o fator de balanceamento de um nó é menor que -1 e o fator de balanceamento de seu filho direito é menor ou igual a 0
    private No rotacaoRR(No no) {
        No temp = no.direita;
        no.direita = temp.esquerda;
        temp.esquerda = no;
        no.altura = 1 + Math.max(altura(no.esquerda), altura(no.direita));
        temp.altura = 1 + Math.max(altura(temp.esquerda), altura(temp.direita));
        return temp;
    }

    // Método para realizar uma rotação LR (Left Right)
    // Este método é usado para balancear a árvore quando o fator de balanceamento de um nó é maior que 1 e o fator de balanceamento de seu filho esquerdo é menor que 0
    // Primeiro, realiza uma rotação RR no filho esquerdo do nó, e depois realiza uma rotação LL no próprio nó
    private No rotacaoLR(No no) {
        no.esquerda = rotacaoRR(no.esquerda);
        return rotacaoLL(no);
    }

    // Método para realizar uma rotação RL (Right Left)
// Este método é usado para balancear a árvore quando o fator de balanceamento de um nó é menor que -1 e o fator de balanceamento de seu filho direito é maior que 0
// Primeiro, realiza uma rotação LL no filho direito do nó, e depois realiza uma rotação RR no próprio nó
    private No rotacaoRL(No no) {
        no.direita = rotacaoLL(no.direita);
        return rotacaoRR(no);
    }


    // Método para encontrar o nó com o menor valor
    // Este método é usado pelo método removerRecursivo para encontrar o nó com o menor valor na subárvore direita de um nó
    private No minValueNode(No node) {
        No current = node;
        while (current != null && current.esquerda != null) {
            current = current.esquerda;
        }
        return current;
    }

    // Método para converter a árvore em uma lista de estudantes
    // Este método é usado para obter todos os estudantes na árvore em ordem
    public List<Estudante> paraLista() {
        List<Estudante> estudantes = new ArrayList<>();
        paraListaRecursivo(this.raiz, estudantes);
        return estudantes;
    }

    // Método recursivo para converter a árvore em uma lista
    // Este método é chamado pelo método paraLista
    private void paraListaRecursivo(No no, List<Estudante> lista) {
        if (no != null) {
            paraListaRecursivo(no.esquerda, lista);
            lista.add(no.estudante);
            paraListaRecursivo(no.direita, lista);
        }
    }
}