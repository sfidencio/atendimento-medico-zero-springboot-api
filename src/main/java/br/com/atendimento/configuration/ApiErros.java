package br.com.atendimento.configuration;


import lombok.Getter;

import java.util.*;

public class ApiErros {

    @Getter
    private List<String> erros;
    //private Map<Integer, String> mapaErros;


    public ApiErros(List<String> erros) {
        this.erros = erros;
        /*if (Objects.isNull(mapaErros))
            this.mapaErros = new HashMap<>();

        //Incluido chave/valor, o map ja inclui ordenando pela chave
        this.erros.forEach(item -> {
            mapaErros.put(Integer.parseInt(Util.separarStringDelimitadorPipeLine(item)[0]), Util.separarStringDelimitadorPipeLine(item)[1]);
        });
        //Zeramos a lista padrao pra preenche-la ordenadamente agora, com os dados do MAP
        this.erros.clear();

        mapaErros.forEach((k, v) -> {
            //System.out.printf("Valor: " + v + "\n");
            this.erros.add(v);
        });*/
    }

    public ApiErros(String message) {
        this.erros = Arrays.asList(message);
    }

    /*public static void main(String[] args) {
        Map<Integer, String> mapaErros = new HashMap<>();
        List<String> erros = new ArrayList<>(0);
        erros.add("6|Erro numero1");
        erros.add("5|Erro numero 2");
        erros.add("3|Erro numero 3");


        erros.forEach(item -> {
            mapaErros.put(Integer.parseInt(Util.separarStringDelimitadorPipeLine(item)[0]), Util.separarStringDelimitadorPipeLine(item)[1]);
        });
        System.out.printf(mapaErros.toString());
    }*/


}
