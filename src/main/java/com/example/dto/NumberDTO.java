package com.example.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
//@NoArgsConstructor
//@AllArgsConstructor

public class NumberDTO {
    /*
    Stringa errore
    stringa risultato somma
    stringa risultato prodotto
     */

    private String errore;
    private String somma;
    private String prodotto;
    private String numeriInput;

}
