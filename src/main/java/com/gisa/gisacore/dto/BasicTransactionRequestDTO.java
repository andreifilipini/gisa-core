package com.gisa.gisacore.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class BasicTransactionRequestDTO implements Serializable {

    private static final long serialVersionUID = 918674197047398102L;

    private Long transactionId;
}
