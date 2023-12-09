import java.util.ArrayList;
import java.util.List;

class MergeSort {
    public List<Estudante> ordenarPorNota(List<Estudante> lista) {
        if (lista.size() <= 1) {
            return lista;
        }

        int meio = lista.size() / 2;
        List<Estudante> esquerda = ordenarPorNota(lista.subList(0, meio));
        List<Estudante> direita = ordenarPorNota(lista.subList(meio, lista.size()));

        return mergePorNota(esquerda, direita);
    }

    private List<Estudante> mergePorNota(List<Estudante> esquerda, List<Estudante> direita) {
        List<Estudante> resultado = new ArrayList<>();
        int i = 0, j = 0;

        while (i < esquerda.size() && j < direita.size()) {
            if (esquerda.get(i).getNota() >= direita.get(j).getNota()) { // Inverta a condição aqui
                resultado.add(esquerda.get(i++));
            } else {
                resultado.add(direita.get(j++));
            }
        }

        while (i < esquerda.size()) {
            resultado.add(esquerda.get(i++));
        }

        while (j < direita.size()) {
            resultado.add(direita.get(j++));
        }

        return resultado;
    }
}