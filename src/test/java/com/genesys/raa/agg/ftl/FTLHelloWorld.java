package com.genesys.raa.agg.ftl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.genesys.raa.agg.ftl.mock.Definition;
import com.genesys.raa.agg.ftl.mock.Group;
import com.genesys.raa.agg.ftl.mock.GroupLevel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Created by vkhaluti on 08-Feb-2016.
 */
public class FTLHelloWorld {

    public static void main(String[] args) {
        //Freemarker configuration object
        Configuration cfg = new Configuration();
        try {
            //Load template from source folder
            Template template = cfg.getTemplate("src/test/resources/create-aggregate-tables.ftl");

            // Build the data-model
            Map<String, Object> data = new HashMap<String, Object>();

            List<GroupLevel> groupLevels = new ArrayList<>();
            groupLevels.add(new GroupLevel(0, "SUBHR"));
            groupLevels.add(new GroupLevel(1, "HOUR"));
            groupLevels.add(new GroupLevel(2, "DAY"));
            groupLevels.add(new GroupLevel(3, "MONTH"));
            groupLevels.add(new GroupLevel(4, "YEAR"));

            data.put("definition", new Definition());
            data.put("group", new Group());
            data.put("groupLevels", groupLevels);

            // Console output
            Writer out = new OutputStreamWriter(System.out);
            template.process(data, out);
            out.flush();

            /*// File output
            Writer file = new FileWriter(new File("C:\\FTL_helloworld.txt"));
            template.process(data, file);
            file.flush();
            file.close();*/

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}