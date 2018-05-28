/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sie;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author denis
 */
public class Testul extends javax.swing.JFrame {

    /**
     * Creates new form Testul
     */
    public Testul() {
        initComponents();
        startTimer();
    }
    
    String raspunsCorect = "";
    String continut = "gol"; 
    String varianta1 = "gol";
    String varianta2 = "gol";
    String varianta3 = "gol";
    boolean multiplu = false;
    int timpIntrebare = 0;
    
    //pt citirea intrebarilor
    DocumentBuilderFactory factory;
    DocumentBuilder builder;
    Document doc;
    
    //pt raspunsurile date de utilizator
    DocumentBuilderFactory factory2;
    DocumentBuilder builder2;
    Document doc2;
    
    int intrebariFolosite[] = {0,0,0,0};
    int nrIntrebare = 0;
    
    GridBagLayout layout = new GridBagLayout();
    intrebarea intreb;
    public Testul(int idIntrebare, String email) {
        initComponents();
        startTimer();   
        
        //citirea intrebarilor
        factory = DocumentBuilderFactory.newInstance();
        factory2 = DocumentBuilderFactory.newInstance();
        try {
            builder = factory.newDocumentBuilder();
            doc = builder.parse("test1.xml");
            
            builder2 = factory2.newDocumentBuilder();
            doc2 = builder2.parse("raspunsuri.xml");
            
            NodeList nrAcces = doc.getElementsByTagName("intrebare");
            
            for(int x = 0; x < nrAcces.getLength(); x++) {
                Node nNode = nrAcces.item(x);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                   Element eElement = (Element) nNode;
                   
                   String idIn = eElement.getAttribute("id");
                   int idInt = Integer.parseInt(idIn);
                   
                   if(idInt == idIntrebare) {
                       
                       NodeList nList = nNode.getChildNodes();
                       
                       for(int j=0; j < nList.getLength(); j++) {
                           Node node = nList.item(j);
                           if("continut".equals(node.getNodeName())) {
                               continut = node.getTextContent();
                           }
                           
                           if("variante".equals(node.getNodeName())) {    
                               Element el = (Element) node;
                               
                               varianta1 = el.getElementsByTagName("first").item(0).getTextContent();
                               varianta2 = el.getElementsByTagName("second").item(0).getTextContent();
                               varianta3 = el.getElementsByTagName("third").item(0).getTextContent();
                           }
                           
                           if("correct".equals(node.getNodeName())){
                               raspunsCorect = node.getTextContent();
                           }
                           
                           if("timp".equals(node.getNodeName())){
                               timpIntrebare = Integer.parseInt(node.getTextContent());
                           }
                           
                           if("multiple".equals(node.getNodeName())){
                               String multe = node.getTextContent();
                               if(multe.equals("yes")) multiplu = true;
                               else multiplu = false;
                           }
                           
                       }
                       
                   }
                }  
            }
            
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException ex) {
            Logger.getLogger(Intrebari.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Intrebari.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        logat.setText(email);
        intreb = new intrebarea(idIntrebare, continut, varianta1, varianta2, varianta3, timpIntrebare, multiplu, nrIntrebare+1);
        dinamicPannel.setLayout(layout);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        dinamicPannel.add(intreb,c);
        intreb.setVisible(true);
        
        intrebariFolosite[nrIntrebare] = idIntrebare;
        
        //generare intrebari
        for(int j=1; j < intrebariFolosite.length; j++){
            Random r = new Random();
            int idIntre = 0;
            
            do {
                idIntre = r.nextInt((5-1)+ 1) + 1;
            } while (checkIntrebare(idIntre));//cat timp id-ul e folosit, sa genereze altul
            intrebariFolosite[j] = idIntre;
        }
    }
    
    private Timer t;
    private int timerCount = 0;
    private int minutesCount = 0;
    public void startTimer(){
        t= new Timer(1000, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                timerCount++;
                if(timerCount < 10)
                    timer.setText("0" + minutesCount + ":0" + timerCount);
                if(timerCount >= 10 && timerCount < 60)
                    timer.setText("0" + minutesCount + ":" + timerCount);
                if(timerCount == 60) {
                    timerCount = 0;
                    minutesCount++;
                    timer.setText("0" + minutesCount + ":0" + timerCount);
                }
            }          
        });
        t.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        radioSingle = new javax.swing.ButtonGroup();
        previous = new javax.swing.JButton();
        next = new javax.swing.JButton();
        dinamicPannel = new javax.swing.JPanel();
        timer = new javax.swing.JLabel();
        logat = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        previous.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        previous.setForeground(new java.awt.Color(255, 51, 51));
        previous.setText("Previous");
        previous.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                previousActionPerformed(evt);
            }
        });

        next.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        next.setForeground(new java.awt.Color(0, 204, 51));
        next.setText("Next");
        next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dinamicPannelLayout = new javax.swing.GroupLayout(dinamicPannel);
        dinamicPannel.setLayout(dinamicPannelLayout);
        dinamicPannelLayout.setHorizontalGroup(
            dinamicPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 805, Short.MAX_VALUE)
        );
        dinamicPannelLayout.setVerticalGroup(
            dinamicPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 355, Short.MAX_VALUE)
        );

        timer.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        timer.setText("00:00");

        logat.setText("nume");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(previous)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(logat)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(timer)
                        .addGap(279, 279, 279)
                        .addComponent(next, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dinamicPannel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dinamicPannel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(previous)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(next)
                        .addComponent(timer)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(logat)
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void nextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextActionPerformed
        // TODO add your handling code here:
        nrIntrebare++;
        String raspunsurileDate[] = intreb.getRaspunsuri();
        
        Node raspunsuriNode = doc2.getDocumentElement();
        
        NodeList rL = doc2.getElementsByTagName("intrebarea");
        
        for(int x = 0; x < rL.getLength(); x++) {
            Node nNode = rL.item(x);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
               Element eElement = (Element) nNode;

               if(Integer.parseInt(eElement.getAttribute("id")) == intrebariFolosite[nrIntrebare-1]) {
                   
                   raspunsuriNode.removeChild(nNode);
               }
            }
        }
        
        try {
            Node intrNou = doc2.createElement("intrebarea");
            ((Element)intrNou).setAttribute("id", Integer.toString(intrebariFolosite[nrIntrebare-1]));
            
            Node continutIntreb = doc2.createElement("continut");
            continutIntreb.setTextContent(continut);
            intrNou.appendChild(continutIntreb);
            
            Node raspunsCor = doc2.createElement("raspunsCorect");
            raspunsCor.setTextContent(raspunsCorect);
            intrNou.appendChild(raspunsCor);
                     
            Node raspUtilizator = doc2.createElement("raspunsUtilizator");
            String ra = "";
            for(int t=0; t<raspunsurileDate.length; t++){
                if(!raspunsurileDate[t].equals("-")){
                    ra += raspunsurileDate[t];
                    if(t==0) ra += ",";
                }
            }
            raspUtilizator.setTextContent(ra);
            intrNou.appendChild(raspUtilizator);
            
            Node intrCo = doc2.createElement("corect");
            if(ra.equals(raspunsCorect.replaceAll("", ""))) intrCo.setTextContent("yes");
            else intrCo.setTextContent("no");
            intrNou.appendChild(intrCo);
            
            raspunsuriNode.appendChild(intrNou);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc2);
            StreamResult result = new StreamResult(new File("raspunsuri.xml"));
            transformer.transform(source, result);

        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
        
        if(intrebariFolosite.length > nrIntrebare) {// la ultima intrebare sa pun finish in loc de next (la buton)     
            
            intreb.setVisible(false);
            dinamicPannel.remove(intreb);
          
            int idIntrebare =  intrebariFolosite[nrIntrebare];
            

            //citirea intrebarilor
            NodeList nrAcces = doc.getElementsByTagName("intrebare");

            for(int x = 0; x < nrAcces.getLength(); x++) {
                Node nNode = nrAcces.item(x);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                   Element eElement = (Element) nNode;

                   if(Integer.parseInt(eElement.getAttribute("id")) == idIntrebare) {
                       NodeList nList = nNode.getChildNodes();
                       
                       for(int j=0; j < nList.getLength(); j++) {
                           Node node = nList.item(j);
                           if("continut".equals(node.getNodeName())) {
                               continut = node.getTextContent();
                           }
                           
                           if("variante".equals(node.getNodeName())) {    
                               Element el = (Element) node;
                               
                               varianta1 = el.getElementsByTagName("first").item(0).getTextContent();
                               varianta2 = el.getElementsByTagName("second").item(0).getTextContent();
                               varianta3 = el.getElementsByTagName("third").item(0).getTextContent();
                           }
                           
                           if("correct".equals(node.getNodeName())){
                               raspunsCorect = node.getTextContent();
                           }
                           
                           if("timp".equals(node.getNodeName())){
                               timpIntrebare = Integer.parseInt(node.getTextContent());
                           }
                           
                           if("multiple".equals(node.getNodeName())){
                               String multe = node.getTextContent();
                               if(multe.equals("yes")) multiplu = true;
                               else multiplu = false;
                           }                       
                       }
                   }
                }  
            }          
           
            intreb = new intrebarea(intrebariFolosite[nrIntrebare], continut, varianta1, varianta2, varianta3, timpIntrebare, multiplu, nrIntrebare+1);
            
            dinamicPannel.setLayout(layout);
            GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 0;
            dinamicPannel.add(intreb,c);
            intreb.setVisible(true);      
        } 
        
        if(intrebariFolosite.length-1 == nrIntrebare){//e ultima intrebare si trebuie afisat scorul daca e logat
            next.setText("Finish");    
        }
        
        if(intrebariFolosite.length == nrIntrebare) {
            //afiseaza rezultatul sau mesaj (in functie de logare)
            if(logat.getText().equals("Anonim")) this.setVisible(false);
            else {
                new rezultate(logat.getText()).setVisible(true);
                this.setVisible(false);
            }
        }
    }//GEN-LAST:event_nextActionPerformed

    private void previousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_previousActionPerformed
        // TODO add your handling code here:
        next.setText("Next"); 
        
        if(nrIntrebare > 0){
            intreb.setVisible(false);
            dinamicPannel.remove(intreb);
            nrIntrebare -= 1;
            
            int idIntrebare =  intrebariFolosite[nrIntrebare];

            //citirea intrebarilor
            NodeList nrAcces = doc.getElementsByTagName("intrebare");

            for(int x = 0; x < nrAcces.getLength(); x++) {
                Node nNode = nrAcces.item(x);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                   Element eElement = (Element) nNode;
                   
                   if(Integer.parseInt(eElement.getAttribute("id")) == idIntrebare) {
                       NodeList nList = nNode.getChildNodes();
                       
                       for(int j=0; j < nList.getLength(); j++) {
                           Node node = nList.item(j);
                           if("continut".equals(node.getNodeName())) {
                               continut = node.getTextContent();
                           }
                           
                           if("variante".equals(node.getNodeName())) {    
                               Element el = (Element) node;
                               
                               varianta1 = el.getElementsByTagName("first").item(0).getTextContent();
                               varianta2 = el.getElementsByTagName("second").item(0).getTextContent();
                               varianta3 = el.getElementsByTagName("third").item(0).getTextContent();
                           }
                           
                           if("correct".equals(node.getNodeName())){
                               raspunsCorect = node.getTextContent();
                           }
                           
                           if("timp".equals(node.getNodeName())){
                               timpIntrebare = Integer.parseInt(node.getTextContent());
                           }
                           
                           if("multiple".equals(node.getNodeName())){
                               String multe = node.getTextContent();
                               if(multe.equals("yes")) multiplu = true;
                               else multiplu = false;
                           }
                           
                       }
                   }
                }  
            }          
           
            intreb = new intrebarea(intrebariFolosite[nrIntrebare], continut, varianta1, varianta2, varianta3, timpIntrebare, multiplu, nrIntrebare+1);
            
            dinamicPannel.setLayout(layout);
            GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 0;
            dinamicPannel.add(intreb,c);
            intreb.setVisible(true);            
        }
    }//GEN-LAST:event_previousActionPerformed

    public boolean checkIntrebare(int id) {
        for(int i = 0; i < intrebariFolosite.length; i++) {
            if(intrebariFolosite[i] == id) return true;
        }
        return false;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Testul.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Testul.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Testul.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Testul.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Testul().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel dinamicPannel;
    private javax.swing.JLabel logat;
    private javax.swing.JButton next;
    private javax.swing.JButton previous;
    private javax.swing.ButtonGroup radioSingle;
    private javax.swing.JLabel timer;
    // End of variables declaration//GEN-END:variables
}
