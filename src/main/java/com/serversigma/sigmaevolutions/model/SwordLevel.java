package com.serversigma.sigmaevolutions.model;

import lombok.*;

@Getter
@AllArgsConstructor
public final class SwordLevel {

    private final String name;
    private final int entities;
    private final int sharpnessLevel;
    private final int lootingLevel;
    private final int unbreakingLevel;
    private final String permission;

}