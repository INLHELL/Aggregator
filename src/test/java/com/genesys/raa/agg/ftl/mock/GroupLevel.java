package com.genesys.raa.agg.ftl.mock;

import lombok.Data;

/**
 * Created by vkhaluti on 08-Feb-2016.
 */
@Data
public class GroupLevel {
    int num;
    String code;

    public GroupLevel(int num, String code) {
        this.num = num;
        this.code = code;
    }
}
