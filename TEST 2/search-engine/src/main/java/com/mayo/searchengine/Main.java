package com.mayo.searchengine;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mayo.searchengine.engine.SearchEngine;
import com.mayo.searchengine.model.*;
import com.mayo.searchengine.output.Output;

import java.io.File;
import java.util.Arrays;

/**
 * CLI entry-point.
 *
 * Usage:
 *   java -jar search-engine.jar --case <case_directory> --out <output_file>
 */
public class Main {

    public static void main(String[] args) throws Exception {
        String caseDir = null;
        String outFile = null;

        for (int i = 0; i < args.length; i++) {
            if ("--case".equals(args[i]) && i + 1 < args.length) {
                caseDir = args[++i];
            } else if ("--out".equals(args[i]) && i + 1 < args.length) {
                outFile = args[++i];
            }
        }

        if (caseDir == null || outFile == null) {
            System.err.println("Usage: java -jar search-engine.jar --case <case_directory> --out <output_file>");
            System.exit(1);
        }

        File caseDirFile = new File(caseDir);
        if (!caseDirFile.isDirectory()) {
            System.err.println("Error: case directory does not exist: " + caseDir);
            System.exit(1);
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        Content[] contents = mapper.readValue(new File(caseDirFile, "content.json"), Content[].class);
        User[]    users    = mapper.readValue(new File(caseDirFile, "users.json"),   User[].class);
        Query[]   queries  = mapper.readValue(new File(caseDirFile, "queries.json"), Query[].class);
        Rules     rules    = mapper.readValue(new File(caseDirFile, "rules.json"),   Rules.class);

        SearchEngine engine = new SearchEngine(
                Arrays.asList(contents),
                Arrays.asList(users),
                rules
        );

        Output output = engine.process(Arrays.asList(queries));

        File out = new File(outFile);
        if (out.getParentFile() != null) out.getParentFile().mkdirs();
        mapper.writeValue(out, output);

        System.out.println("Output written to: " + outFile);
    }
}
