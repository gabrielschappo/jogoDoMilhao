package br.com.milhao.game;

import br.com.milhao.ajuda.Ajuda;
import br.com.milhao.ajuda.AjudaCartas;
import br.com.milhao.ajuda.AjudaUniversitarios;
import br.com.milhao.model.Jogador;
import br.com.milhao.model.NivelDificuldade;
import br.com.milhao.model.Pergunta;

import java.util.*;

public class Jogo {

    private final Jogador jogador;
    private final Map<NivelDificuldade, List<Pergunta>> perguntasPorNivel;
    private final List<Ajuda> ajudasDisponiveis;
    private int rodadaAtual;
    private final int[] PREMIOS = {0, 1000, 2000, 3000, 4000, 5000, 10000, 20000, 30000, 40000, 50000, 100000, 200000, 300000, 400000, 500000, 1000000};
    private final int[] VALOR_ERRO = {0, 500, 1000, 1500, 2000, 2500, 5000, 10000, 15000, 20000, 25000, 50000, 100000, 150000, 200000, 250000, 500000};
    private final Scanner scanner;

    private List<Integer> indicesOcultosDaRodada;

    public Jogo(String nomeJogador, List<Pergunta> todasAsPerguntas, Scanner scanner) {
        this.jogador = new Jogador(nomeJogador);
        this.perguntasPorNivel = new EnumMap<>(NivelDificuldade.class);
        this.scanner = scanner;
        this.rodadaAtual = 1;
        this.ajudasDisponiveis = new ArrayList<>(Arrays.asList(new AjudaCartas(), new AjudaUniversitarios()));
        
        for (NivelDificuldade nivel : NivelDificuldade.values()) {
            perguntasPorNivel.put(nivel, new ArrayList<>());
        }
        for (Pergunta p : todasAsPerguntas) {
            perguntasPorNivel.get(p.getNivel()).add(p);
        }
        this.indicesOcultosDaRodada = new ArrayList<>();
    }

    public void iniciar() {
        System.out.println("\n BEM-VINDO AO JOGO DO MILHÃO, " + jogador.getNome() + "! \n");
        
        while (rodadaAtual < PREMIOS.length) {
            
            indicesOcultosDaRodada.clear();
            
            Pergunta pergunta = selecionarPergunta();
            if (pergunta == null) {
                System.out.println("Não há mais perguntas disponíveis! Você venceu com o que acumulou!");
                break;
            }

            exibirStatus();
        
            pergunta.exibirPergunta(indicesOcultosDaRodada);

            while (true) {
                System.out.print("> ");
                String comando = scanner.nextLine().trim();

                if (comando.toLowerCase().startsWith("ajuda")) {
                    if (usarAjuda(comando, pergunta)) {
                        // Se a ajuda foi usada com sucesso, mostra o status e a pergunta novamente
                        exibirStatus();
                        pergunta.exibirPergunta(indicesOcultosDaRodada); // Re-exibe com os índices ocultos
                    }
                    continue;
                }

                if (comando.equalsIgnoreCase("parar")) {
                    pararJogo();
                    return; 
                }

                if (comando.length() == 1 && "abcd".contains(comando.toLowerCase())) {
                    if (processarResposta(comando, pergunta)) {
                        jogador.setPontuacao(PREMIOS[rodadaAtual]);
                        System.out.println("\nResposta Correta! Você ganhou R$ " + PREMIOS[rodadaAtual] + ".");
                        if (rodadaAtual == PREMIOS.length - 1) {
                            System.out.println("\nParabéns! Você ganhou R$ 1.000.000! ");
                        }
                        rodadaAtual++;
                        break; 
                    } else {
                        System.out.println("\nResposta Errada! Que pena... :(");
                        jogador.setPontuacao(VALOR_ERRO[rodadaAtual]);
                        System.out.println("Você sai do jogo com R$ " + jogador.getPontuacao() + ".");
                        return;
                    }
                } else {
                    System.out.println("Comando inválido. Digite A, B, C, D, 'PARAR' ou 'AJUDA [nome]'.");
                }
            }
        }
    }

    // Métodos `exibirStatus`, `selecionarPergunta`, `processarResposta`, `pararJogo`, `getNomesAjudas` e `getJogador` não precisam de alteração
    private void exibirStatus() {
        System.out.println("--- RODADA " + rodadaAtual + " --- VALENDO R$ " + PREMIOS[rodadaAtual] + " ---");
        System.out.println("Prêmio por parar: R$ " + (rodadaAtual > 1 ? PREMIOS[rodadaAtual - 1] : 0));
        System.out.println("Prêmio por errar: R$ " + VALOR_ERRO[rodadaAtual]);
        System.out.println("Ajudas disponíveis: " + getNomesAjudas());
        System.out.println("Digite A, B, C, D para responder, 'PARAR' ou 'AJUDA [nome]'.");
    }

    private Pergunta selecionarPergunta() {
        NivelDificuldade nivelAlvo;
        if (rodadaAtual <= 5) nivelAlvo = NivelDificuldade.FACIL;
        else if (rodadaAtual <= 10) nivelAlvo = NivelDificuldade.MEDIO;
        else nivelAlvo = NivelDificuldade.DIFICIL;
        List<Pergunta> perguntasDoNivel = perguntasPorNivel.get(nivelAlvo);
        if (perguntasDoNivel.isEmpty()) {
            if (nivelAlvo == NivelDificuldade.DIFICIL) {
                perguntasDoNivel = perguntasPorNivel.get(NivelDificuldade.MEDIO);
            }
            if (perguntasDoNivel.isEmpty() && (nivelAlvo == NivelDificuldade.DIFICIL || nivelAlvo == NivelDificuldade.MEDIO)) {
                perguntasDoNivel = perguntasPorNivel.get(NivelDificuldade.FACIL);
            }
        }
        if (perguntasDoNivel.isEmpty()) return null;
        int index = new Random().nextInt(perguntasDoNivel.size());
        return perguntasDoNivel.remove(index);
    }
    
    private boolean processarResposta(String resposta, Pergunta pergunta) {
        int indiceResposta = resposta.toLowerCase().charAt(0) - 'a';
        return indiceResposta == pergunta.getRespostaCorreta();
    }

    private void pararJogo() {
        int pontuacaoFinal = (rodadaAtual > 1) ? PREMIOS[rodadaAtual - 1] : 0;
        jogador.setPontuacao(pontuacaoFinal);
        System.out.println("\nVocê decidiu parar. Parabéns!");
        System.out.println("Você ganhou R$ " + jogador.getPontuacao() + ".");
    }
    
    private String getNomesAjudas() {
        if (ajudasDisponiveis.isEmpty()) {
            return "Nenhuma";
        }
        List<String> nomes = new ArrayList<>();
        for (Ajuda a : ajudasDisponiveis) {
            nomes.add(a.getNome());
        }
        return nomes.toString();
    }

    public Jogador getJogador() {
        return jogador;
    }
    
    private boolean usarAjuda(String comando, Pergunta pergunta) {
        if (comando.toLowerCase().split(" ").length < 2) {
            System.out.println("Comando de ajuda incompleto. Use 'AJUDA [nome]', ex: AJUDA CARTAS.");
            return false;
        }

        String nomeAjuda = comando.toLowerCase().replace("ajuda ", "").trim();
        Ajuda ajudaParaUsar = null;
        
        for (Ajuda a : ajudasDisponiveis) {
            if (a.getNome().equalsIgnoreCase(nomeAjuda)) {
                ajudaParaUsar = a;
                break;
            }
        }

        if (ajudaParaUsar != null) {
            // Captura a lista de índices a ocultar e adiciona na variável de controle da rodada
            List<Integer> indicesParaOcultar = ajudaParaUsar.usar(pergunta);
            indicesOcultosDaRodada.addAll(indicesParaOcultar);

            ajudasDisponiveis.remove(ajudaParaUsar);
            return true; // Sucesso
        } else {
            System.out.println("Ajuda '" + nomeAjuda + "' não encontrada ou já utilizada.");
            return false; // Falha
        }
    }
}   