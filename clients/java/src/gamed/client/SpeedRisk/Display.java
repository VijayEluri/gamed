/*
 * Display.java
 *
 * Created on July 7, 2008, 10:50 PM
 */

package gamed.client.SpeedRisk;

import java.lang.Math;
import java.awt.Point;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import java.io.IOException;

/**
 *
 * @author  bruce
 */
public class Display extends gamed.Game
        implements Runnable, ImageObserver, ActionListener {
    
    private Thread thread;
    private Image bg;
    private gamed.Server server;
    private volatile boolean running;
    private volatile int lines_seen;
    private volatile int images_downloaded;
    private static final int TOTAL_IMAGES = 44;
    private Borders map;
    private int selectedCountry = -1;
    private Country[] countries;
    private JCheckBox[] playerDisplays;
    private int playerInd[] = { -1, -1, -1, -1, -1, -1 };
    private boolean playerReady[];
    private int reserve;
    private int player_id;
    private boolean atWar;
    
    public Display(gamed.Server s) {
        server = s;
        map = new Borders();
        playerDisplays = new JCheckBox[6];
        playerReady = new boolean[6];
        initComponents();
        atWar = false;
        byte cmd[] = {PLAYER_STATUS, 0, 0, 0};
        s.sendGameData(cmd);
    }
    
    @Override
    public void paintComponent(java.awt.Graphics g) {
        if (images_downloaded == TOTAL_IMAGES) {
            g.drawImage(bg, 0, 0, null);
            for (Country c : countries) {
                c.paint(g);
            }
            for (Country c : countries) {
                c.paintIcon(g);
            }
        } else {
            g.clearRect(0, 0, 650, 375);
        }
    }
    
    public void joinedGame() {
        if (thread == null) {
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }
    
    public void run() {
        initCountries();
        initMediaDownload();
        while(images_downloaded < TOTAL_IMAGES) {
            try {
                Thread.sleep(100);
            }
            catch (java.lang.InterruptedException e) {
                return;
            }
        }
        init();
        loadingText.setVisible(false);
        progress.setVisible(false);
        reserveLabel.setText("0 armies in reserve");
        phaseLabel.setText("Waiting for players");
        statusPanel.setVisible(true);
        readyRadio.setVisible(true);
        notReadyRadio.setVisible(true);
        jButton1.setVisible(true);
        repaint();
    }
    
    private void initMediaDownload() {
        images_downloaded = 0;
        progress.setValue(0);
        bg = getImage("images/world_map_relief.jpg");
        for (int i=0; i<42; i++) {
            countries[i].overlay = getImage("images/c"+i+".png");
        }
        ((Kamchat)countries[36]).overlay2 = getImage("images/c36b.png");
    }
    
    private Image getImage(String image) {
        Image img = server.getImage(image);
        int height = img.getHeight(this);
        if (height != -1) {
            images_downloaded++;
            lines_seen += height;
            progress.setValue(lines_seen);
        }
        return img;
    }
    
    public void stop() {
        if ((thread != null) && thread.isAlive()) {
            running = false;
        }
        thread = null;
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        progress = new javax.swing.JProgressBar();
        loadingText = new javax.swing.JLabel();
        statusPanel = new javax.swing.JPanel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jCheckBox6 = new javax.swing.JCheckBox();
        phaseBG = new javax.swing.JPanel();
        phaseLabel = new javax.swing.JLabel();
        reserveBG = new javax.swing.JPanel();
        reserveLabel = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        readyRadio = new javax.swing.JRadioButton();
        notReadyRadio = new javax.swing.JRadioButton();

        setPreferredSize(new java.awt.Dimension(650, 375));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });
        setLayout(null);

        progress.setMaximum(2558);
        progress.setFocusable(false);
        progress.setStringPainted(true);
        add(progress);
        progress.setBounds(10, 325, 630, 20);

        loadingText.setFont(new java.awt.Font("DejaVu Sans", 0, 28));
        loadingText.setText("Loading Media...");
        add(loadingText);
        loadingText.setBounds(200, 150, 250, 70);

        statusPanel.setLayout(new java.awt.GridLayout(0, 1, 1, 0));

        jCheckBox1.setVisible(false);
        playerDisplays[5] = jCheckBox1;
        jCheckBox1.setFocusable(false);
        jCheckBox1.setRequestFocusEnabled(false);
        jCheckBox1.setRolloverEnabled(false);
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playerDisplayActionPerformed(evt);
            }
        });
        statusPanel.add(jCheckBox1);

        jCheckBox2.setVisible(false);
        playerDisplays[4] = jCheckBox2;
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playerDisplayActionPerformed(evt);
            }
        });
        statusPanel.add(jCheckBox2);

        jCheckBox3.setVisible(false);
        playerDisplays[3] = jCheckBox3;
        jCheckBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playerDisplayActionPerformed(evt);
            }
        });
        statusPanel.add(jCheckBox3);

        jCheckBox4.setVisible(false);
        playerDisplays[2] = jCheckBox4;
        jCheckBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playerDisplayActionPerformed(evt);
            }
        });
        statusPanel.add(jCheckBox4);

        jCheckBox5.setVisible(false);
        playerDisplays[1] = jCheckBox5;
        jCheckBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playerDisplayActionPerformed(evt);
            }
        });
        statusPanel.add(jCheckBox5);

        jCheckBox6.setVisible(false);
        playerDisplays[0] = jCheckBox6;
        jCheckBox6.setFocusable(false);
        jCheckBox6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playerDisplayActionPerformed(evt);
            }
        });
        statusPanel.add(jCheckBox6);

        phaseBG.setLayout(new java.awt.GridLayout(1, 0));

        phaseLabel.setText("Waiting for players");
        phaseLabel.setFocusable(false);
        phaseLabel.setRequestFocusEnabled(false);
        phaseBG.add(phaseLabel);

        statusPanel.add(phaseBG);

        reserveBG.setLayout(new java.awt.GridLayout(1, 0));

        reserveLabel.setText("jLabel2");
        reserveLabel.setFocusable(false);
        reserveLabel.setRequestFocusEnabled(false);
        reserveBG.add(reserveLabel);

        statusPanel.add(reserveBG);

        statusPanel.setVisible(false);

        add(statusPanel);
        statusPanel.setBounds(20, 210, 150, 150);
        statusPanel.setBackground(new java.awt.Color(0,true));

        jButton1.setVisible(false);
        jButton1.setText("Quit Game");
        jButton1.setDefaultCapable(false);
        jButton1.setFocusable(false);
        jButton1.setRequestFocusEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1);
        jButton1.setBounds(546, 340, 100, 29);

        readyRadio.setBackground(new Color(0,true));
        buttonGroup1.add(readyRadio);
        readyRadio.setText("Ready");
        readyRadio.setVisible(false);
        readyRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                readyRadioActionPerformed(evt);
            }
        });
        add(readyRadio);
        readyRadio.setBounds(250, 280, 63, 22);

        notReadyRadio.setBackground(new Color(0, true));
        buttonGroup1.add(notReadyRadio);
        notReadyRadio.setSelected(true);
        notReadyRadio.setText("Not Ready");
        notReadyRadio.setVisible(false);
        notReadyRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                readyRadioActionPerformed(evt);
            }
        });
        add(notReadyRadio);
        notReadyRadio.setBounds(250, 300, 90, 22);
    }// </editor-fold>//GEN-END:initComponents

    private void playerDisplayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playerDisplayActionPerformed
        javax.swing.ButtonModel model = ((JCheckBox)evt.getSource()).getModel();
        model.setSelected(!model.isSelected());
}//GEN-LAST:event_playerDisplayActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        server.quitGame();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void readyRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_readyRadioActionPerformed
        sendReady(readyRadio.getModel().isSelected());
    }//GEN-LAST:event_readyRadioActionPerformed

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        if (evt.isPopupTrigger()) {
            byte c = (byte)getCountryAt(evt.getPoint());
            if (c != -1 && countries[c].owner == player_id) {
                if (selectedCountry != -1 &&
                        countries[selectedCountry].armies > 1 &&
                        map.borders(selectedCountry, c)) {
                    showMovePopup(evt, selectedCountry, c);
                    setSelectedCountry(-1);
                }
                else if (reserve > 0) {
                        showPlacementPopup(evt, c);
                }
            }
        }
    }//GEN-LAST:event_formMousePressed

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        byte c = (byte)getCountryAt(evt.getPoint());
        if (c != -1) {
            if (countries[c].owner == player_id) {
                if (selectedCountry != -1 &&
                        countries[selectedCountry].armies > 1 &&
                        map.borders(selectedCountry, c)) {
                    if (evt.isPopupTrigger()) {
                        showMovePopup(evt, selectedCountry, c);
                    }
                    else {
                        moveArmies((byte)selectedCountry, c, (byte)(countries[selectedCountry].armies - 1));
                        
                    } 
                }
                else if (reserve > 0) {
                    if (evt.isPopupTrigger()) {
                        showPlacementPopup(evt, c);
                    }
                    else if (evt.getClickCount() > 1) {
                        placeAllArmiesAt(c);
                    }
                }
                setSelectedCountry(c);
                repaint();
            }
            else if (selectedCountry != -1 &&
                    map.borders(selectedCountry, c)) {
                attack(c);                
            }
        }
    }//GEN-LAST:event_formMouseReleased

    private void showPlacementPopup(java.awt.event.MouseEvent evt, int country) {
        showPopup(evt, -1, country, reserve);
    }
    
    private void showMovePopup(java.awt.event.MouseEvent evt, int from, int to) {
        if (atWar) {
            showPopup(evt, from, to, countries[from].armies - 1);
        }
    }
    
    private void showPopup(java.awt.event.MouseEvent evt, int from, int to, int armies) {
        JPopupMenu popup = new JPopupMenu("Move");
        float step = (float)armies / 5;
        float value = armies;
        int last = 0;
        int v = armies;
        do {
            if (last != v) {
                JMenuItem i = new JMenuItem(Integer.toString(v));
                i.setActionCommand(String.format("%d:%d:%d", from, to, v));
                i.addActionListener(this);
                popup.add(i);
                last = v;
            }
            value -= step;
            v = (int)Math.ceil(value);
        } while (value > 0);
        popup.show(evt.getComponent(), evt.getX(), evt.getY());
    }
    
    public void actionPerformed(ActionEvent evt) {
        String args[] = evt.getActionCommand().split(":");
        byte from   = Byte.parseByte(args[0]);
        byte to     = Byte.parseByte(args[1]);
        byte armies = Byte.parseByte(args[2]);
        if (from == -1) {
            placeArmiesAt(to, armies);
        }
        else {
            moveArmies(from, to, armies);
        }
    }
    
    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        if ((infoflags & ImageObserver.SOMEBITS) > 0) {
            lines_seen++;
            progress.setValue(lines_seen);
        }
        if ((infoflags & ImageObserver.ERROR) > 0) {
            images_downloaded++;
            System.err.println("Error loading image");
            return false;
        }
        if ((infoflags & ImageObserver.ALLBITS) > 0) {
            images_downloaded++;
            return false;
        }
        return true;
    }
        
    public void init() {
        for (Country c : countries) {
            c.init();
        }
    }
    
    public void handleGameData(byte[] data) {
        switch (data[0]) {
            case PLAYER_JOIN:
            case PLAYER_QUIT:
                server.askForPlayerList();
                break;
            case SR_ERROR:
                System.err.println("Error: " + data[1]);
                System.err.flush();
                break;
            case READY:
                playerReady[data[1]] = true;
                if (playerInd[data[1]] != -1) {
                    playerDisplays[playerInd[data[1]]].getModel().setSelected(true);
                }
                break;
            case NOTREADY:
                playerReady[data[1]] = false;
                if (playerInd[data[1]] != -1) {
                    playerDisplays[playerInd[data[1]]].getModel().setSelected(false);
                }
                break;
            case START_PLACING:
                phaseLabel.setText("Placing Armies");
                notReadyRadio.getModel().setSelected(true);
                setSelectedCountry(-1);
                for (int i = 0; i < 6; i++) {
                    playerDisplays[i].getModel().setSelected(false);
                    playerReady[i] = false;
                }
                break;
            case BEGIN:
                atWar = true;
                phaseLabel.setText("At War");
                readyRadio.setVisible(false);
                notReadyRadio.setVisible(false);
                break;
            case GET_ARMIES:
                reserve = data[3];
                reserveLabel.setText(data[3] + " armies in reserve");
                break;
            case ATTACK_RESULT:
            case MOVE_RESULT:
                countries[data[4]].set(data[5], data[6]);
                int old_owner = countries[data[8]].owner;
                countries[data[8]].set(data[9], data[10]);
                if (old_owner != data[9]) {
                    if (data[9] == player_id) {
                        setSelectedCountry(data[8]);
                    } else if (selectedCountry == data[8]) {
                        setSelectedCountry(-1);
                    }
                }
                break;
            case GAME_STATUS:
                for (int i = 1; i <= 42; i++) {
                    countries[data[i*4]].set(data[i*4+1], data[i*4+2]);
                }
                break;
            case PLAYER_STATUS:
                player_id = data[1];
                reserve = data[3];
                Color myColor = new Color(Country.token_colors[player_id]);
                phaseBG.setBackground(myColor);
                reserveBG.setBackground(myColor);
                break;
            case COUNTRY_STATUS:
                countries[data[4]].set(data[5], data[6]);
                break;
            case DEFEAT:
                if (playerInd[data[1]] != -1) {
                    playerDisplays[playerInd[data[1]]].getModel().setSelected(false);
                    playerReady[data[1]] = false;
                }
                break;
            case VICTORY:
                phaseLabel.setText("Game Over");
                break;
        }
        repaint();
    }
    
    public void updatePlayers(gamed.Player[] players) {
        int i=0;
        for (; i < players.length; i++) {
            playerInd[players[i].id] = i;
            playerDisplays[i].setText(players[i].name);
            playerDisplays[i].setBackground(new Color(Country.token_colors[players[i].id]));
            playerDisplays[i].getModel().setSelected(playerReady[players[i].id]);
            playerDisplays[i].setVisible(true);

        }
        for (; i<6; i++) {
            playerInd[i] = -1;
            playerDisplays[i].setVisible(false);
        }
    }
    
    public void renamePlayer(gamed.Player player) {
        playerDisplays[playerInd[player.id]].setText(player.name);
    }

    private void sendReady(boolean ready) {
        byte cmd[] = { ready ? READY : NOTREADY, 0, 0, 0 };
        server.sendGameData(cmd);
    }
    
    private int getCountryAt(Point p) {
        for (int i=0; i<42; i++) {
            if (countries[i].contains(p)) {
                return i;
            }
        }
        return -1;
    }
    
    private void setSelectedCountry(int c) {
        if (selectedCountry != c) {
            if (selectedCountry != -1) {
                countries[selectedCountry].setSelected(false);
            }
            if (c != -1) {
                countries[c].setSelected(true);
            }
        }
        selectedCountry = c;
    }
    
    private void attack(byte to) {
        if (countries[selectedCountry].armies > 1) {
            byte cmd[] = {
                ATTACK, 
                (byte)selectedCountry,
                to,
                (byte)(countries[selectedCountry].armies - 1) 
            };
            server.sendGameData(cmd);
        }
    }
    
    private void moveArmies(byte from, byte to, byte armies) {
        if (countries[from].armies > 1) {
            byte cmd[] = {
                MOVE, 
                from,
                to,
                armies 
            };
            server.sendGameData(cmd);
        }
    }
    
    private void placeArmiesAt(byte to, byte armies) {
        if (reserve > 0) {
            byte cmd[] = {
                PLACE, 
                0,
                to,
                armies
            };
            server.sendGameData(cmd);
        }
    }
    
    private void placeAllArmiesAt(byte to) {
        placeArmiesAt(to, (byte)reserve);
    }
    
    private void initCountries() {
        countries = new Country[42];
        countries[ 0] = new Country(133, 114, 157, 131); // EasternUS
        countries[ 1] = new Country( 79,  52, 103,  65); // NorthwestTerritory
        countries[ 2] = new Country(100, 112, 117, 123); // WesternUS
        countries[ 3] = new Country(150,  84, 154,  97); // Ontario
        countries[ 4] = new Country(114, 148, 140, 168); // CentralAmerica
        countries[ 5] = new Country( 87,  84, 109,  92); // Alberta
        countries[ 6] = new Country( 99,   1, 187,  38); // Greenland
        countries[ 7] = new Country( 12,  49,  44,  62); // Alaska
        countries[ 8] = new Country(180,  76, 191,  92); // Quebec
        countries[ 9] = new Country(196, 198, 227, 219); // Brazil
        countries[10] = new Country(180, 184, 194, 191); // Venezuela
        countries[11] = new Country(189, 240, 197, 267); // Argentina
        countries[12] = new Country(177, 199, 197, 228); // Peru
        countries[13] = new Country(282,  65, 282,  62); // Iceland
        countries[14] = new Country(332, 113, 348, 118); // SouthernEurope
        countries[15] = new Country(362,  29, 388,  84); // Ukraine
        countries[16] = new Country(333,  50, 348,  66); // Scandinavia
        countries[17] = new Country(306,  88, 306,  92); // GreatBritan
        countries[18] = new Country(304, 108, 310, 119); // WesternEurope
        countries[19] = new Country(325,  90, 339,  99); // NorthernEurope
        countries[20] = new Country(340, 143, 358, 151); // Egypt
        countries[21] = new Country(341, 189, 353, 213); // Congo
        countries[22] = new Country(403, 231, 400, 236); // Madagascar
        countries[23] = new Country(349, 242, 359, 251); // SouthAfrica
        countries[24] = new Country(362, 166, 379, 190); // EastAfrica
        countries[25] = new Country(293, 136, 317, 167); // NorthAfrica
        countries[26] = new Country(409,  75, 428, 109); // Afghanistan
        countries[27] = new Country(484,  98, 517, 109); // Mongolia
        countries[28] = new Country(432,  43, 443,  78); // Ural
        countries[29] = new Country(560, 120, 569, 131); // Japan
        countries[30] = new Country(482,  81, 523,  85); // Irkutsk
        countries[31] = new Country(433, 142, 456, 156); // India
        countries[32] = new Country(489, 157, 495, 167); // Siam
        countries[33] = new Country(519,  42, 546,  62); // Yakutsk
        countries[34] = new Country(455,   8, 483,  58); // Siberia
        countries[35] = new Country(451, 124, 491, 133); // China
        countries[36] = new Kamchat(560,  32, 601,  60); // Kamchatka
        countries[37] = new Country(372, 127, 398, 147); // MiddleEast
        countries[38] = new Country(562, 208, 573, 211); // NewGuinea
        countries[39] = new Country(498, 174, 520, 199); // Indonesia
        countries[40] = new Country(529, 228, 542, 247); // WesternAustralia
        countries[41] = new Country(569, 227, 572, 248); // EasternAustralia
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JLabel loadingText;
    private javax.swing.JRadioButton notReadyRadio;
    private javax.swing.JPanel phaseBG;
    private javax.swing.JLabel phaseLabel;
    private javax.swing.JProgressBar progress;
    private javax.swing.JRadioButton readyRadio;
    private javax.swing.JPanel reserveBG;
    private javax.swing.JLabel reserveLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables
    
    public static final byte PLAYER_JOIN = 0;
    public static final byte MESSAGE = 1;
    public static final byte SR_ERROR = 2;
    public static final byte READY = 3;
    public static final byte NOTREADY = 4;
    public static final byte START_PLACING = 5;
    public static final byte BEGIN = 6;
    public static final byte MOVE = 7;
    public static final byte ATTACK = 8;
    public static final byte PLACE = 9;
    public static final byte GET_ARMIES = 10;
    public static final byte ATTACK_RESULT = 11;
    public static final byte MOVE_RESULT = 12;
    public static final byte GAME_STATUS = 13;
    public static final byte PLAYER_STATUS = 14;
    public static final byte COUNTRY_STATUS = 15;
    public static final byte DEFEAT = 16;
    public static final byte VICTORY = 17;
    public static final byte PLAYER_QUIT = 18;
}