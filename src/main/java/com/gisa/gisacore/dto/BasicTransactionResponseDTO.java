package com.gisa.gisacore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class BasicTransactionResponseDTO implements Serializable {

    private Long transactionId;
    private boolean approved;
}
