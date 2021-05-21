package com.serversigma.sigmaevolutions.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class SwordLevel {

    private final String name;
    private final int entitys;
    private final int sharpnessLevel;
    private final int lootingLevel;
    private final int unbreakingLevel;

}