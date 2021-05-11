package com.dledmonds.encodertest;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.File;

/**
 * Main entry point into application that allows basic command line
 * configuration
 *
 * @author dledmonds
 */
public class ConsoleRunner {

    public static void main(String[] args) {
        CommandLineParser parser = new DefaultParser();

        Options options = new Options();
        Option helpOption = new Option("help", "Print this message");
        options.addOption(helpOption);
        Option outputOption = new Option("o", "output-directory", true, "Output directory");
        options.addOption(outputOption);

        File outputDir = null;

        try {
            CommandLine cLine = parser.parse(options, args);

            if (cLine.hasOption(helpOption.getOpt())) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("java", options);
                System.exit(0);
            }

            if (cLine.hasOption(outputOption.getOpt())) {
                outputDir = new File(cLine.getOptionValue(outputOption.getOpt()));
            } else {
                outputDir = new File("output");
            }
            System.out.println("Attempting to use output directory " + outputDir.getAbsolutePath());

        } catch (ParseException pe) {
            System.err.println("Unable to parse command line options: " + pe.getMessage());
        }

        if (!outputDir.exists()) {
            System.err.println(outputDir.getAbsolutePath() + " does not exist");
            System.exit(1);
        }

        ConfigRunner cr = new ConfigRunner(outputDir);
        cr.run(new HardcodedTestConfigs().getAllTestConfigs());
    }

}
