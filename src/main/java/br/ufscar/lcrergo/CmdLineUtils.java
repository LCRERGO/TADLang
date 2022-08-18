package br.ufscar.lcrergo;

import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CmdLineUtils {
    private static Options buildOptions() {
        var options = new Options();
        var input = Option.builder("input")
                .argName("file")
                .desc("file input to be parsed")
                .hasArg()
                .optionalArg(true)
                .build();
        var output = Option.builder("output")
                .argName("file")
                .desc("file output to be generated")
                .hasArg()
                .optionalArg(true)
                .build();
        var help = new Option("help", "print this message");

        options.addOption(input);
        options.addOption(output);
        options.addOption(help);

        return options;
    }

    static public void printHelpWrapper() {
        var opts = buildOptions();
        printHelp(opts);
    }

    static private void printHelp(Options options) {
        var fmt = new HelpFormatter();
        fmt.printHelp("TADL [OPTION]... [FILE]", options);

    }

    public static Config buildConfig(String args[]) throws ParseException {
        var options = buildOptions();
        var parser = new DefaultParser();
        var cmd = parser.parse(options, args);
        var argsList = cmd.getArgList();
        Config cfg;
        String inFname, outFname;

        if (cmd.hasOption("help")) {
            printHelp(options);
            System.exit(0);
        }
        if (cmd.hasOption("input")) {
            inFname = cmd.getOptionValue("input");
        } else {
            if (argsList.isEmpty()) {
                inFname = "in.tsk";
            } else {
                var inArgs = argsList.get(0);
                inFname = inArgs;
            }
        }
        if (cmd.hasOption("output")) {
            outFname = cmd.getOptionValue("output");
        } else {
            outFname = "out.html";
        }
        cfg = new Config(inFname, outFname);

        return cfg;
    }
}