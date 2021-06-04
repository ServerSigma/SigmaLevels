package com.serversigma.sigmalevels.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Level {

    private final double price;
    private final String name;
    private final String permission;
    private final LevelType levelType;

    private int protection;
    private int unbreaking;

    private int fortune;
    private int efficiency;

    private int looting;
    private int sharpness;
    private int fireAspect;

}