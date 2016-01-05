package de.htwg.qwirkle.aview.gui;

import de.htwg.qwirkle.controller.IQControllerGui;
import de.htwg.qwirkle.model.Tile;
import javafx.scene.layout.Border;
import util.Constants;
import util.StretchIcon;
import util.observer.IObserver;
import util.observer.QEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class QFrame extends JFrame implements IObserver {

    private static final Dimension RIGID_AREA = new Dimension(10, 10);
    private static final Dimension RIGID_BOTTOM = new Dimension(0, 250);

    private JPanel mainPanel;
    private JPanel sidePanel;
    private LogoPanel logoPanel;
    private GridPanel gridPanel;
    private HandPanel handPanel;
    private PlayerScorePanel playerScorePanel;
    private OpPanel opPanel;
    private StatusPanel statusPanel;
    private IQControllerGui controller;

    public QFrame (final IQControllerGui controller) {
        super("HTWG QWIRKLE 2016");
        this.controller = controller;
        controller.addObserver(this);

        MenuBar menuBar = new MenuBar(controller);
        setJMenuBar(menuBar);

        // Main Panel
        mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.LINE_AXIS));

        gridPanel = new GridPanel(controller);
        gridPanel.setPreferredSize(Constants.GRID_PANEL_SIZE);
        mainPanel.add(gridPanel);

        sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.PAGE_AXIS));

        logoPanel = new LogoPanel();
        logoPanel.setMaximumSize(new Dimension(Constants.SIDE_PANEL_WIDTH, 220));
        sidePanel.add(logoPanel);

        opPanel = new OpPanel(controller);
        opPanel.setPreferredSize(new Dimension(Constants.SIDE_PANEL_WIDTH, 130));
        sidePanel.add(Box.createRigidArea(RIGID_AREA));
        sidePanel.add(opPanel);

        handPanel = new HandPanel(controller);
        handPanel.setPreferredSize(new Dimension(Constants.SIDE_PANEL_WIDTH, 200));
        sidePanel.add(Box.createRigidArea(RIGID_AREA));
        sidePanel.add(handPanel);

        playerScorePanel = new PlayerScorePanel(controller);
        //playerScorePanel.setPreferredSize(new Dimension(Constants.SIDE_PANEL_WIDTH, 100));
        sidePanel.add(Box.createRigidArea(RIGID_AREA));
        sidePanel.add(playerScorePanel);

        statusPanel = new StatusPanel(controller);
        sidePanel.add(Box.createRigidArea(RIGID_AREA));
        //statusPanel.setMaximumSize(new Dimension(Constants.SIDE_PANEL_WIDTH, 120));
        sidePanel.add(statusPanel);
        sidePanel.add(Box.createRigidArea(RIGID_BOTTOM));

        sidePanel.setPreferredSize(new Dimension(Constants.SIDE_PANEL_WIDTH, 0));
        mainPanel.add(Box.createRigidArea(RIGID_AREA));
        mainPanel.add(sidePanel);

        // JFrame Settings
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setSize(DEFAULT_WINDOWSIZE);
        pack();
        setResizable(true);
        setVisible(true);

        // turn the ligths off
        getRootPane().setGlassPane(new JComponent() {
            public void paintComponent(Graphics g) {
                g.setColor(new Color(0, 0, 0, 175));
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        });


        // open the New Game dialog
        new NewGameDialog(controller, this);
    }

    @Override
    public void update(QEvent e) {
        //repaint(50);
    }
}
