package com.company;

import java.math.BigDecimal;

public class Operations {


    public BigDecimal additionOp(BigDecimal result, BigDecimal arg) {
        return new BigDecimal(String.valueOf(result.add(arg))).setScale(16,BigDecimal.ROUND_CEILING).stripTrailingZeros();
    }

    public BigDecimal subtractionOp (BigDecimal result, BigDecimal arg) {
        return new BigDecimal(String.valueOf(result.subtract(arg))).setScale(16,BigDecimal.ROUND_CEILING).stripTrailingZeros();
    }

    public BigDecimal divOp (BigDecimal result, BigDecimal arg) {
        return new BigDecimal(String.valueOf(result.divide(arg,16,BigDecimal.ROUND_CEILING))).stripTrailingZeros();
    }
    public BigDecimal multOp (BigDecimal result, BigDecimal arg) {
        return new BigDecimal(String.valueOf(result.multiply(arg))).setScale(16,BigDecimal.ROUND_CEILING).stripTrailingZeros();
    }

}
