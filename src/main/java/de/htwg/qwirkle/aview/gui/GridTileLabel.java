package de.htwg.qwirkle.aview.gui;

import de.htwg.qwirkle.controller.IQController;
import de.htwg.qwirkle.controller.IQControllerGui;
import util.GridPosition;
import util.observer.QEvent;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by niels on 18.12.2015.
 * This is implemented as Label to use Icons, however it rather functions as a Panel
 */
public class GridTileLabel extends AbstractTileLabel {

    private GridPosition position;

    public GridTileLabel(GridPosition position, IQControllerGui controller) {

        this.controller = controller;
        controller.addObserver(this);

        this.position = position;

        addComponentListener(this);
        addMouseListener(new Listener());

        update(new QEvent());
    }

    public GridTileLabel(int row, int col, IQControllerGui controller) {
        this(new GridPosition(row, col), controller);
    }

    @Override
    public void refreshTile() {
        tile = controller.getTileFromGrid(position);
    }

    @Override
    public void refreshBorder() {
        if (controller.targetPositionOnGridIsSet()
                && position == controller.getTargetPositionOnGrid()) {
            setBorder(BORDER_TARGET);
        } else {
            setBorder(BORDER_PLAIN);
        }
    }

    class Listener extends MouseAdapter {

        // remember this tile when clicked in ADDTILES state
        @Override
        public void mousePressed(MouseEvent e) {

            if (controller.getState() == IQController.State.ADDTILES
                    && controller.getNumberOfSelectedTiles() == 1
                    && tile == null) {

                controller.setTargetPositionOnGrid(position);
            }
        }

        // hover empty fields when in ADDTILES state
        @Override
        public void mouseEntered(MouseEvent e) {
            if (controller.getState() == IQController.State.ADDTILES
                    && controller.getNumberOfSelectedTiles() == 1
                    && tile == null) {
                setBorder(BORDER_HOVER);
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (controller.getState() == IQController.State.ADDTILES
                    && controller.getNumberOfSelectedTiles() == 1
                    && tile == null) {
                refreshBorder();
            }
        }
    }
}
