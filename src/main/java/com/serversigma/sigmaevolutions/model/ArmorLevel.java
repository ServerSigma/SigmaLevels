package com.serversigma.sigmaevolutions.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class ArmorLevel {

    private final String name;
    private final int gems;
    private final int protectionLevel;
    private final int unbreakingLevel;
    private final String permission;

}
