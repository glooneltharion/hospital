package com.glooneltharion.hospital.models.enums;

public enum InsuranceCompany {
    VSZP("Všeobecná zdravotná poisťovňa"),
    DOVERA("Dôvera"),
    UNION("Union");

    private final String label;

    InsuranceCompany(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
