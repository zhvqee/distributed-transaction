package com.distributed.transaction.core.models;

import java.util.Optional;

public enum TransactionType {

    AT;

    public TransactionType getType(int ordinal) {
        for (TransactionType type : values()) {
            if (type.ordinal() == ordinal) {
                return type;
            }
        }
        return null;
    }

    public TransactionType getTypeOrDefault(int ordinal) {
        return Optional.ofNullable(getType(ordinal)).orElse(TransactionType.AT);
    }
}
