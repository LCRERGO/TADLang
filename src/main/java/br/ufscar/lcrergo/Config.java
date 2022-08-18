package br.ufscar.lcrergo;

/**
 * A class for the compiler configuration
 */
public class Config {
    private String inputFileName;
    private String outputFileName;

    public Config(String inFname, String outFname) {
        this.inputFileName = inFname;
        this.outputFileName = outFname;
    }

    public String getInputFileName() {
        return inputFileName;
    }

    public String getOutputFileName() {
        return outputFileName;
    }
}
