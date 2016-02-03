package com.genesys.raa.agg.service;

/**
 * Created by SPIDER on 03.02.2016.
 */

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AggServiceRestController {

    @RequestMapping("/")
    String test() {
        return "Test";
    }
}
