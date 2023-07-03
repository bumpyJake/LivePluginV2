package com.bumpyjake.liveplugin.data.rank;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Rank {
    private final String name;
    private final String luckPermsGroup;
    private final String onMessage;
    private final String offMessage;
    private final String onMessageAll;
    private final String onTitle;
    private final String onSubtitle;
}
