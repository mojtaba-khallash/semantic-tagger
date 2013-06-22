package ir.ac.iust.nlp.semantictagger;

import ir.ac.iust.nlp.semantictagger.enums.FeatureType;
import ir.ac.iust.nlp.semantictagger.utility.WordNet;
import java.io.*;
import java.util.Date;

/**
 *
 * @author Mojtaba Khallash
 */
public class ApplyInCoNLL {
    
    public static void Start(String input, String output, FeatureType type) 
            throws Exception {
        
        System.out.println();
        System.out.println("Started: " + new Date(System.currentTimeMillis()));
        
        int numFeats = 0;
        
        System.out.println();
        System.out.println("Loading Data From FarsNet");
        
        WordNet wordNet;
        try {
            wordNet = new WordNet();
        }
        catch(NoClassDefFoundError ex) {
            System.out.println("\nerror: FarsNet files not found.");
            System.out.println("Due to restricted pulish for farsnet we couldn't place the FarsNet files");
            System.out.println("to this package. For running the package you must download related files and");
            System.out.println("put them manually in right places.");
            System.out.println("After installing FarsNet, It creates 2 folders, dict and lib. You must copy:");
            System.out.println("1- dict folder in FarsNet installation directory to SemanticTagger/dict/");
            System.out.println("2- FarsNetBrowser.jar to SemanticTagger/lib/");
            System.out.println("3- sensesRelations0.xml to SemanticTagger/ (root)");
            System.out.println("4- synsetsRelation0.xml to SemanticTagger/ (root)");
            System.out.println("5- words0.xml to SemanticTagger/ (root)");
            System.out.println("6- synsets0.xml to SemanticTagger/ (root)");
            
            return;
        }
        
        File out = new File(output);
        if (out.exists())
            out.delete();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(input), "UTF8")); 
             Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(output, true), "UTF-8"))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().length() != 0) {
                    String[] split = line.split("\t");
                    int id = Integer.parseInt(split[0]);
                    String lexeme = split[1];
                    String lemma = split[2];
                    String cpos = split[3];
                    String fpos = split[4];
                    String feat = split[5];
                    int head = Integer.parseInt(split[6]);
                    String dependency = split[7];

                    // get synsetID
                    String lm = lemma;
                    if (lm.contains("#")) {
                        String[] parts = lm.split("#");
                        if (parts.length == 3)
                            lm = parts[0] + parts[1] + "ن";
                        else if (parts.length == 2)
                            lm = parts[0] + "ن";
                        else
                            lm = parts[0];
                    }
                    String[] ws = WordNet.CorrectFormats(lm);
                    for (int k = 0; k < ws.length; k++) {
                        String word = ws[k];
                        if (type == FeatureType.SS) {
                            String sid = wordNet.GetFirstSenseID(word);
                            if (sid.length() != 0) {
                                if (feat.length() != 0)
                                    feat += "|";
                                feat += "synsetID=" + sid;
                                numFeats++;
                                break;
                            }
                        }
                        else if (type == FeatureType.SF) {
                            String sf = wordNet.GetFirstSemanticFile(word);
                            if (sf.length() != 0) {
                                if (feat.length() != 0)
                                    feat += "|";
                                feat += "semanticFile=" + sf;
                                numFeats++;
                                break;
                            }
                        }
                    }
                    
                    StringBuilder os = new StringBuilder();
                    os.append(id).append("\t").append(lexeme).append("\t").append(lemma).append("\t").append(cpos).append("\t").append(fpos).append("\t").append(feat).append("\t").append(head).append("\t").append(dependency).append("\t_\t_");
                    writer.write(os.toString() + "\n");
                } else {
                    writer.write("\n");
                }
            }
        }
        
        System.out.println("Number of added feats: " + numFeats);
        System.out.println("Finished: " + new Date(System.currentTimeMillis()));
    }
}