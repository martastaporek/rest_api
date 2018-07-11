package com.teamA.supplier;

public enum PersistenceUnit {

    POSTGRES("mundialPU");

    private final String unit;


    PersistenceUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }
}
