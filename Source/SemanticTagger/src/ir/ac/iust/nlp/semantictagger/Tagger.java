package ir.ac.iust.nlp.semantictagger;

import ir.ac.iust.nlp.semantictagger.utility.WordNet;
import ir.sbu.nlp.wordnet.data.model.Sense;
import ir.sbu.nlp.wordnet.data.model.Synset;
import java.awt.ComponentOrientation;
import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mojtaba Khallash
 */
public class Tagger extends javax.swing.JFrame {

    private static final ResourceBundle bundle = ResourceBundle.getBundle("ir/ac/iust/nlp/semantictagger/Bundle");

    boolean is_EOF = false;
    String inputName;
    BufferedReader in = null;
    String outputName;
    Writer out = null;
    int index = -1;
    List<String> Sentence = null;
    int tokenID = 0;
    // FarsNet Browser
    WordNet wordNet;
    List<String> SemID;

    /**
     * Creates new form Tagger
     */
    public Tagger() {
        initComponents();
        
        lblSentence.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        tblSynsetList.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    }
    
    public void start() {
        
        is_EOF = true;
        
        // External FarsNet
//        farsnetbrowser.Main.main(null);
        // Internal FarsNet
        if (wordNet == null) {
            try {
                wordNet = new WordNet();
            }
            catch (NoClassDefFoundError ex) {
                StringBuilder Sen = new StringBuilder("<html><body style='font-size:24px'>");
                Sen.append(bundle.getString("FarsNetNotFound"));
                Sen.append("</body></html>");
                lblSentence.setText(Sen.toString());
                
                return;
            }
            catch (Exception ex) {
                StringBuilder Sen = new StringBuilder("<html><body style='font-size:24px'><br/><br/>");
                Sen.append("<center><font color='red'>").append(bundle.getString("FarsNetError")).append("</font></center>");
                Sen.append("</body></html>");
                lblSentence.setText(Sen.toString());
                
                btnUnknown.setEnabled(false);
                btnNone.setEnabled(false);
                btnSave.setEnabled(false);
                btnConfig.setEnabled(false);
                txtSynsetID.setEnabled(false);
                
                return;
            }
        }
                
        try {
            File configFile = new File("config.txt");
            BufferedReader tmp = new BufferedReader(new FileReader(configFile));
            
            String s;
            while( (s = tmp.readLine()) != null) {
                String[] parts = s.split("=");
                if (parts[0].equals("Input")){
                    inputName = parts[1];
                    File inputFile = new File(parts[1]);
                    in = new BufferedReader(new FileReader(inputFile));
                }
                if (parts[0].equals("Output")){
                    outputName = parts[1];
                    out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(parts[1], true), "UTF-8"));
                }
                if (parts[0].equals("Start")){
                    index = Integer.parseInt(parts[1]);
                }
            }
            
            if (in == null || out == null || index == -1)
                System.exit(1);
        
            int i = 0;
            while( i < index && in.readLine() != null) {
                i++;
            }
                    
            ReadSentence();
            
            btnUnknown.setEnabled(true);
            btnNone.setEnabled(true);
            btnSave.setEnabled(true);
            txtSynsetID.setEnabled(true);
            
            is_EOF = false;
        }
        catch(IOException | NumberFormatException ex){
            StringBuilder Sen = new StringBuilder("<html><body style='font-size:24px'><br/><br/>");
            Sen.append("<center><font color='blue'>").append(bundle.getString("Error")).append("</font></center>");
            Sen.append("</body></html>");
            lblSentence.setText(Sen.toString());

            btnConfig.doClick();
        }
        
        txtSynsetID.requestFocusInWindow(); 
    }
    
    private void ReadSentence() {
        String s;
        Sentence = new LinkedList<>();
        tokenID = 0;
        try {
            while( (s = in.readLine()) != null) {
                String UTF8Str = new String(s.getBytes(),"UTF-8");
                UTF8Str = UTF8Str.replace("�?", "ف");
                if (UTF8Str.length() != 0)
                    Sentence.add(UTF8Str);
                else {
                    highlightCurrentToken();
                    break;
                }
            }
        }
        catch(Exception ex){}
        if (Sentence.isEmpty()) {
            StringBuilder Sen = new StringBuilder("<html><body style='font-size:24px'><br/><br/>");
            Sen.append("<center><font color='red'>").append(bundle.getString("EOF")).append("</font></center>");
            Sen.append("</body></html>");
            lblSentence.setText(Sen.toString());
            
            is_EOF = true;
            btnUnknown.setEnabled(false);
            btnNone.setEnabled(false);
            btnSave.setEnabled(false);
            txtSynsetID.setEnabled(false);
        }
        else
            txtCurrentToken_Focus(null);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblSynsetID = new javax.swing.JLabel();
        txtSynsetID = new javax.swing.JTextField();
        btnSave = new javax.swing.JButton();
        btnNone = new javax.swing.JButton();
        btnUnknown = new javax.swing.JButton();
        lblCurrentWord = new javax.swing.JLabel();
        txtCurrentToken = new javax.swing.JTextField();
        lblCurrentStem = new javax.swing.JLabel();
        txtCurrentStem = new javax.swing.JTextField();
        lblPOS = new javax.swing.JLabel();
        txtCurrentPOS = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        lblSentence = new javax.swing.JTextPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSynsetList = new javax.swing.JTable(new DefaultTableModel());
        btnConfig = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle(bundle.getString("SEMANTIC_TAGGER")); // NOI18N
        setMinimumSize(new java.awt.Dimension(916, 568));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                Closing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                JFrame_Load(evt);
            }
        });

        lblSynsetID.setText(bundle.getString("SynsetID")); // NOI18N

        txtSynsetID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSynsetID_KeyReleased(evt);
            }
        });

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ir/ac/iust/nlp/semantictagger/ok.png"))); // NOI18N
        btnSave.setText(bundle.getString("Save")); // NOI18N
        btnSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSave_mouseExit(evt);
            }
        });
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSave(evt);
            }
        });

        btnNone.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ir/ac/iust/nlp/semantictagger/close.png"))); // NOI18N
        btnNone.setText(bundle.getString("None")); // NOI18N
        btnNone.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnNone_mouseExit(evt);
            }
        });
        btnNone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNone_Click(evt);
            }
        });

        btnUnknown.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ir/ac/iust/nlp/semantictagger/question.png"))); // NOI18N
        btnUnknown.setText(bundle.getString("Unknown")); // NOI18N
        btnUnknown.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnUnknown_mouseExit(evt);
            }
        });
        btnUnknown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUnknown_click(evt);
            }
        });

        lblCurrentWord.setText(bundle.getString("Word")); // NOI18N

        txtCurrentToken.setEditable(false);
        txtCurrentToken.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCurrentToken.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCurrentToken_Focus(evt);
            }
        });

        lblCurrentStem.setText(bundle.getString("Stem")); // NOI18N

        txtCurrentStem.setEditable(false);
        txtCurrentStem.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCurrentStem.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCurrentStem_Focus(evt);
            }
        });

        lblPOS.setText(bundle.getString("POS")); // NOI18N

        txtCurrentPOS.setEditable(false);
        txtCurrentPOS.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCurrentPOS.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCurrentPOS_Focus(evt);
            }
        });

        lblSentence.setContentType("text/html");
        lblSentence.setEditable(false);
        lblSentence.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblSentence.setText("<html>\r\n  <head>\r\n  </head>\r\n  <body style='font-size:24px;'>\r\n    <center>\r\n\t<br/>\n\tلطفا صبر نمائيد\n\t<br/>\n\tدر حال دريافت اطلاعات از فارس‌نت\n    </center>\r\n  </body>\r\n</html>\r\n");
        jScrollPane3.setViewportView(lblSentence);

        tblSynsetList.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblSynsetList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "معنا", "تعريف", "مثال", "POS"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSynsetList.setAutoscrolls(false);
        tblSynsetList.setRowHeight(22);
        tblSynsetList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblSynsetList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSynsetList_Event(evt);
            }
        });
        tblSynsetList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblSynsetList_KeyEvent(evt);
            }
        });
        jScrollPane1.setViewportView(tblSynsetList);
        tblSynsetList.getColumnModel().getColumn(3).setMinWidth(50);
        tblSynsetList.getColumnModel().getColumn(3).setPreferredWidth(50);
        tblSynsetList.getColumnModel().getColumn(3).setMaxWidth(50);

        btnConfig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ir/ac/iust/nlp/semantictagger/refresh.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("ir/ac/iust/nlp/semantictagger/Bundle"); // NOI18N
        btnConfig.setText(bundle.getString("Settings")); // NOI18N
        btnConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfigActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtSynsetID)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblSynsetID)
                                .addGap(47, 47, 47))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnUnknown)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnNone)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSave)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(txtCurrentPOS)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblPOS))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtCurrentStem, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblCurrentStem)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtCurrentToken, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblCurrentWord))))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnNone, btnSave, btnUnknown});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtCurrentStem, txtCurrentToken});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnUnknown, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNone, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSave, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCurrentStem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblCurrentStem)
                        .addComponent(txtCurrentToken, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblCurrentWord)
                        .addComponent(btnConfig, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPOS)
                    .addComponent(lblSynsetID)
                    .addComponent(txtSynsetID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCurrentPOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnNone, btnUnknown});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSave(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSave
        btnSave.setEnabled(false);
        DoSave();
    }//GEN-LAST:event_btnSave

    private void DoSave() {
        String ID = txtSynsetID.getText().trim();
        if (ID.length() == 0) {
            JOptionPane.showMessageDialog(this, bundle.getString("SynsetIDRequired"));
            txtSynsetID.requestFocusInWindow(); 
            return;
        }
        
        try {
            if (tokenID < Sentence.size()) {
                String UTF8Str = Sentence.get(tokenID);
                tokenID++;
                if (UTF8Str != null) {
                    String[] parts = UTF8Str.split("\t");

                    if (parts.length >= 2) {
                        if(parts[5].equals("_"))
                            parts[5] = "synsetID=" + ID;
                        else
                            parts[5] += "|synsetID=" + ID;

                        StringBuilder sb = new StringBuilder();
                        sb.append(parts[0]);
                        for(int j = 1 ; j<parts.length; j++) {
                            sb.append("\t").append(parts[j]);
                        }
                        sb.append("\n");

                        out.write(sb.toString());
                        out.flush();
                    }
                    else {
                        out.write("\n");
                        out.flush();
                    }
                    index++;
                    
                    txtSynsetID.setText("");
                    
                    if (tokenID == Sentence.size()) {
                        out.write("\n");
                        out.flush();
                        index++;
                        ReadSentence();
                        
                    }
                    else
                        highlightCurrentToken();
                }
            }
        }
        catch(Exception ex) {
        }
        press = false;
        txtCurrentToken_Focus(null);
        txtSynsetID.requestFocusInWindow(); 
    }
    
    private void btnUnknown_click(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUnknown_click
        btnUnknown.setEnabled(false);
        DoUnknown();
    }//GEN-LAST:event_btnUnknown_click

    private void DoUnknown() {
        try {
            if (tokenID < Sentence.size()) {
                String UTF8Str = Sentence.get(tokenID);
                tokenID++;
                if (UTF8Str != null) {
                    String[] parts = UTF8Str.split("\t");

                    if (parts.length >= 2) {
                        if(parts[5].equals("_"))
                            parts[5] = "synsetID=?????";
                        else
                            parts[5] += "|synsetID=?????";

                        StringBuilder sb = new StringBuilder();
                        sb.append(parts[0]);
                        for(int j = 1 ; j<parts.length; j++) {
                            sb.append("\t").append(parts[j]);
                        }
                        sb.append("\n");

                        out.write(sb.toString());
                        out.flush();
                    }
                    else {
                        out.write("\n");
                        out.flush();
                    }
                    index++;
                    
                    txtSynsetID.setText("");

                    if (tokenID == Sentence.size()) {
                        out.write("\n");
                        out.flush();
                        index++;
                        ReadSentence();
                    }
                    else
                        highlightCurrentToken();
                }
            }
        }
        catch(Exception ex) {
        }
        txtSynsetID.requestFocusInWindow(); 
    }
    
    private void btnNone_Click(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNone_Click
        btnNone.setEnabled(false);
        DoNone();
    }//GEN-LAST:event_btnNone_Click

    private void DoNone() {
        try {
            if (tokenID < Sentence.size()) {
                String s = Sentence.get(tokenID);
                tokenID++;
                if (s != null) {
                    out.write(s + "\n");
                    out.flush();
                    index++;
                }

                txtSynsetID.setText("");
                
                if (tokenID == Sentence.size()) {
                    out.write("\n");
                    out.flush();
                    index++;
                    ReadSentence();
                }
                else
                    highlightCurrentToken();
            }
        }
        catch(Exception ex) {
        }
        txtCurrentToken_Focus(null);
        txtSynsetID.requestFocusInWindow(); 
    }
    
    private void Closing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_Closing
        try {
            if (out!= null)
                out.close();
            if (in!= null)
                in.close();
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("config.txt"), "UTF-8"))) {
                writer.write("Input=" + inputName);
                writer.write("\nOutput=" + outputName);
                writer.write("\nStart=" + index);
            }
        }
        catch(Exception ex){}
    }//GEN-LAST:event_Closing

    private void txtCurrentStem_Focus(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCurrentStem_Focus
        txtCurrentStem.selectAll();
        txtCurrentStem.copy();
    }//GEN-LAST:event_txtCurrentStem_Focus

    boolean press = false;
    
    private void txtSynsetID_KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSynsetID_KeyReleased
        if (evt.getKeyCode() == 10) {
            if (press == false) {
                press = true;
                DoSave();
            }
            else
                press = false;
        }
        else if (evt.getKeyCode() == 32) {
            DoNone();
        }
    }//GEN-LAST:event_txtSynsetID_KeyReleased

    private void btnSave_mouseExit(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSave_mouseExit
        if (is_EOF == false)
            btnSave.setEnabled(true);
    }//GEN-LAST:event_btnSave_mouseExit

    private void btnNone_mouseExit(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNone_mouseExit
        if (is_EOF == false)
            btnNone.setEnabled(true);
    }//GEN-LAST:event_btnNone_mouseExit

    private void btnUnknown_mouseExit(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUnknown_mouseExit
        if (is_EOF == false)
            btnUnknown.setEnabled(true);
    }//GEN-LAST:event_btnUnknown_mouseExit

    private void txtCurrentToken_Focus(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCurrentToken_Focus
        txtCurrentToken.selectAll();
        txtCurrentToken.copy();
    }//GEN-LAST:event_txtCurrentToken_Focus

    private void txtCurrentPOS_Focus(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCurrentPOS_Focus
        txtCurrentPOS.selectAll();
        txtCurrentPOS.copy();
    }//GEN-LAST:event_txtCurrentPOS_Focus

    private void JFrame_Load(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_JFrame_Load
        start();
    }//GEN-LAST:event_JFrame_Load

    private void tblSynsetList_Event(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSynsetList_Event
        if (tblSynsetList.getSelectedRowCount() > 0 && SemID != null && SemID.isEmpty() == false)
            txtSynsetID.setText(SemID.get(tblSynsetList.getSelectedRow()));
    }//GEN-LAST:event_tblSynsetList_Event

    private void tblSynsetList_KeyEvent(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblSynsetList_KeyEvent
        tblSynsetList_Event(null);
    }//GEN-LAST:event_tblSynsetList_KeyEvent

    private void btnConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfigActionPerformed
        btnUnknown.setEnabled(false);
        btnNone.setEnabled(false);
        btnSave.setEnabled(false);
        txtSynsetID.setEnabled(false);

        ConfigForm cf = new ConfigForm(this);
        setEnabled(false);
        cf.setLocation(getLocation().x + (getWidth() - cf.getWidth()) / 2, getLocation().y + (getHeight() - cf.getHeight()) / 2);
        cf.setVisible(true);
    }//GEN-LAST:event_btnConfigActionPerformed

    private void highlightCurrentToken() {
        StringBuilder Sen = new StringBuilder("<html><body style='font-size:24px'>");
        for ( int i = 0 ; i< Sentence.size(); i++) {
            String UTF8Str = Sentence.get(i);
            String[] parts = UTF8Str.split("\t");
            if (i == tokenID) {
                Sen.append("<font color='red'>").append(parts[1]).append("</font>");
                
                txtCurrentToken.setText(parts[1]);
                txtCurrentStem.setText(parts[2]);
                String res = parts[3];
                try {res = bundle.getString(parts[3]);}catch(Exception ex){}
                txtCurrentPOS.setText(res + " - " + parts[3]);
            }
            else
                Sen.append(parts[1]);
            
            Sen.append(" ");
        }
        Sen.append("</body></html>");
        lblSentence.setText(Sen.toString());

        DefaultTableModel model = (DefaultTableModel)tblSynsetList.getModel();
        model.setRowCount(0);
        
        // Get Synsets from FarsNet
        String word = txtCurrentToken.getText();
        getSynsets(word, model);
        
        String stem = txtCurrentStem.getText();
        if (!word.equals(stem)) {
            if (stem.contains("#")) {
                String[] parts = stem.split("#");
                if (parts.length == 3)
                    stem = parts[0] + parts[1] + "ن";
                else if (parts.length == 2)
                    stem = parts[0] + "ن";
                else
                    stem = parts[0];
            }
            getSynsets(stem, model);
        }
        if (tokenID < Sentence.size() - 1) {
            String[] parts = Sentence.get(tokenID + 1).split("\t");
            if (parts[3].equals("V")) {
                String stm = parts[2];
                if (stm.contains("#")) {
                    String[] prts = stem.split("#");
                    if (prts.length == 3)
                        stm = prts[0] + prts[1] + "ن";
                    else if (prts.length == 2)
                        stm = prts[0] + "ن";
                    else
                        stm = prts[0];
                }
                
                word = word + " " + stm;
                getSynsets(word, model);
                
                if (!word.equals(stem)) {
                    stem = stem + " " + stm;
                    getSynsets(stem, model);
                }
            }
        }
    }
    
    private void getSynsets(String word, DefaultTableModel model) {
        String[] ws = WordNet.CorrectFormats(word);
        for (int k = 0; k < ws.length; k++) {
            word = ws[k];
            List<Synset> ss = wordNet.FindSynsetsContainWord(word);
            SemID = new LinkedList<>();
            for (int i = 0; i < ss.size(); i++) {
                List<Sense> sens = ss.get(i).getSenses();
                StringBuilder synonyms = new StringBuilder();
                for (int j = 0; j < sens.size(); j++) {
                    List<String> strs = sens.get(j).getWord().getValue();
                    if (synonyms.length() != 0)
                        synonyms.append(" - ");
                    if (strs != null && !strs.isEmpty())
                        synonyms.append(strs.get(0));
                }
                
                String pos = ss.get(i).getPos();
                try {pos = bundle.getString(pos);}catch(Exception ex){}
                
                model.addRow(new Object[] {
                    // Synonyms
                    synonyms,
                    // Definitions
                    ss.get(i).getGloss(),
                    // Examples
                    ss.get(i).getExample(),
                    // POS tag
                    pos
                    
                });
                SemID.add(ss.get(i).getUid());
            }
            if (ss.size() > 0) {
                tblSynsetList.setRowSelectionInterval(0, 0);
                txtSynsetID.setText(SemID.get(0));
                break;
            }
        }        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Tagger.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new Tagger().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConfig;
    private javax.swing.JButton btnNone;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUnknown;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblCurrentStem;
    private javax.swing.JLabel lblCurrentWord;
    private javax.swing.JLabel lblPOS;
    private javax.swing.JTextPane lblSentence;
    private javax.swing.JLabel lblSynsetID;
    private javax.swing.JTable tblSynsetList;
    private javax.swing.JTextField txtCurrentPOS;
    private javax.swing.JTextField txtCurrentStem;
    private javax.swing.JTextField txtCurrentToken;
    private javax.swing.JTextField txtSynsetID;
    // End of variables declaration//GEN-END:variables
}