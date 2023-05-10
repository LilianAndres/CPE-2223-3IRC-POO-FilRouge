package model;

import nutsAndBolts.PieceSquareColor;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractPieceModel implements PieceModel {
    private Coord coord;
    private PieceSquareColor pieceColor;

    public AbstractPieceModel(Coord coord, PieceSquareColor pieceColor) {
        this.coord = coord;
        this.pieceColor = pieceColor;
    }

    @Override
    public char getColonne() {
        return this.coord.getColonne();
    }

    @Override
    public int getLigne() {
        return this.coord.getLigne();
    }

    @Override
    public boolean hasThisCoord(Coord coord) {
        return this.coord.equals(coord);
    }

    @Override
    public void move(Coord coord) {
        this.coord = coord;
    }

    @Override
    public PieceSquareColor getPieceColor() {
        return this.pieceColor;
    }

    public List<Coord> getCoordsOnItinerary(Coord targetCoord) {

        List<Coord> coordsOnItinery = new LinkedList<Coord>();

        int ligDistance = targetCoord.getLigne() - this.getLigne();

        if (ligDistance==1) return coordsOnItinery;

        for (int i = 0 ; i < ligDistance-1 ; i++){
            Coord c = null;
            if (this.pieceColor == PieceSquareColor.WHITE){
                c = new Coord((char) (this.getColonne() + i + 1 ),this.getLigne() + i + 1);
            } else {
                c = new Coord((char) (this.getColonne() - i - 1 ),this.getLigne() - i - 1);
            }
            coordsOnItinery.add(c);
        }
        return coordsOnItinery;
    }

    public abstract boolean isMoveOk(Coord targetCoord, boolean isPieceToCapture);

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return " ["+pieceColor.toString().charAt(0) + coord + "]";
    }
}
