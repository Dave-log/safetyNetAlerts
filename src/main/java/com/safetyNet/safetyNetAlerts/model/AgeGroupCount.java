package com.safetyNet.safetyNetAlerts.model;

import lombok.Getter;

@Getter
public class AgeGroupCount {
    private final int adults;
    private final int children;

    public AgeGroupCount(int adults, int children) {
        this.adults = adults;
        this.children = children;
    }
}
