package com.serversigma.sigmaevolutions.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class PickaxeLevel {

    private final String name;
    private final int blocks;
    private final int efficiencyLevel;
    private final int fortuneLevel;
    private final int unbreakingLevel;
    private final String permission;

}
