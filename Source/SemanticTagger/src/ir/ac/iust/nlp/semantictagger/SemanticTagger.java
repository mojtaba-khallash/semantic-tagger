package ir.ac.iust.nlp.semantictagger;

/*
* Copyright (C) 2013 Iran University of Science and Technology
*
* This file is part of "Semantic Tagger" Project, as available 
* from http://nlp.iust.ac.ir This file is free software;
* you can redistribute it and/or modify it under the terms of the GNU General 
* Public License (GPL) as published by the Free Software Foundation, in 
* version 2 as it comes in the "COPYING" file of the VirtualBox OSE 
* distribution. VirtualBox OSE is distributed in the hope that it will be 
* useful, but WITHOUT ANY WARRANTY of any kind.
*
* You may elect to license modified versions of this file under the terms 
* and conditions of either the GPL.
*/

import ir.ac.iust.nlp.semantictagger.enums.FeatureType;
import java.io.File;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Mojtaba Khallash
 */
public class SemanticTagger {

    // -v 0 -i <input-file> -o <output-file> -t <SS|SF>
    public static void main(String[] args) {
        boolean visisble = true;
        String input = "";
        String output = "";
        FeatureType type = FeatureType.SS;
        
        showIntroduction();
        
        boolean exception = false;
        try {
            for (int i = 0; i< args.length; i++) {
                switch (args[i]) {
                    case "-v":
                        i++;
                        String val = args[i];
                        if (!(val.equals("0") || val.equals("1"))) {
                            throw new Exception();
                        }
                        visisble = val.equals("1");
                        break;
                    case "-i":
                        i++;
                        input = args[i];
                        break;
                    case "-o":
                        i++;
                        output = args[i];
                        break;
                    case "-t":
                        i++;
                        switch (args[i]) {
                            case "SS":
                                type = FeatureType.SS;
                                break;
                            case "SF":
                                type = FeatureType.SF;
                                break;
                        }
                        break;
                }
            }
            
            if (visisble == false && 
                (input.length() == 0 ||
                 output.length() == 0)) {
                throw new Exception();
            }
        } catch (Exception e) {
            exception = true;
            visisble = false;
        }
        finally {
            if (visisble == false) {
                if (exception == true) {
                    showHelp();
                    System.exit(1);
                }
                else {
                    try {
                        File in = new File(input);
                        if (!in.exists()) {
                            System.out.println("\t- input file \"" + input + "\" not found.");
                            throw new Exception();
                        }
                        ApplyInCoNLL.Start(input, output, type);
                        System.exit(0);
                    } catch(Exception ex) {
                        System.exit(1);
                    }
                }
            }
            else {
                showHelp();
            }
        }
        
        Tagger application = new Tagger();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(application);
            application.pack();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }
        application.setVisible(true);
    }
    
    private static void showIntroduction() {
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("                          Semantic Tagger 1.0");
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("                            Mojtaba Khallash");
        System.out.println();
        System.out.println("             Iran University of Science and Technology (IUST)");
        System.out.println("                                 Iran");
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println();        
    }
    
    private static void showHelp() {
        System.out.println("Required Arguments:");
        System.out.println("        -v <visibility (0|1)>");
        System.out.println("        -i <input file>");
        System.out.println("        -o <output file>");        
        System.out.println("Optional Argument:");
        System.out.println("        -t <type of feature (SS|SF)>");
        System.out.println("            SS: Synset ID (Default)");
        System.out.println("            SF: Semantic File");
    }
}