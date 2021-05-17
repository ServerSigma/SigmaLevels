package com.serversigma.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class PickaxeLevel {

    private final String name;
    private final int blocks;
    private final int efficiencyLevel;
    private final int fortuneLevel;
    private final int unbreakingLevel;

}
