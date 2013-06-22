package ir.ac.iust.nlp.semantictagger.utility;

import edu.mit.jwi.item.ISynset;
import ir.sbu.nlp.wordnet.Browser.Browser;
import ir.sbu.nlp.wordnet.bussiness.CheckFormat.InputFormat;
import ir.sbu.nlp.wordnet.data.dao.DaoInitializer;
import ir.sbu.nlp.wordnet.data.dao.DaoManager;
import ir.sbu.nlp.wordnet.data.model.Synset;
import java.util.List;

/**
 *
 * @author Mojtaba Khallash
 */
public class WordNet {

    private Browser browser;
    private static InputFormat formatter = new InputFormat();

    public WordNet() {
        DaoManager dm = DaoManager.getInstance(0);
        DaoInitializer di = new DaoInitializer(dm.getWordDao(), dm.getSenseRelationDao(), dm.getSynsetDao(), dm.getSynsetsRelationDao(), 0);
        browser = di.getBrowser();
    }
    
    public List<Synset> FindSynsetsContainWord(String word) {
        return browser.FindSynsetsContainWord(word);
    }
    
    public String GetFirstSenseID(String word) {
        List<Synset> ss = FindSynsetsContainWord(word);
        if (ss != null && !ss.isEmpty())
            return ss.get(0).getUid();
        else
            return "";
    }
    
    public String GetFirstSemanticFile(String word) {
        List<Synset> ss = FindSynsetsContainWord(word);
        for (int i=0;i<ss.size();i++) {
            List<ISynset> ms = ss.get(0).getMappedISynsets();
            if (ms.size() > 0)
                return ms.get(0).getLexicalFile().getName();
        }
        return "";
    }
    
    public static String[] CorrectFormats(String word) {
        return formatter.CorrectSearchFormat(word);
    }
}