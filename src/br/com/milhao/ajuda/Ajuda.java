package br.com.milhao.ajuda;

import br.com.milhao.model.Pergunta;
import java.util.List;

public interface Ajuda {
    String getNome();
    
    List<Integer> usar(Pergunta pergunta);
}