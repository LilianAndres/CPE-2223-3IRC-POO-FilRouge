package model;


import java.util.LinkedList;
import java.util.List;

import nutsAndBolts.PieceSquareColor;

public class PawnModel implements PieceModel{

	private Coord coord;
	private PieceSquareColor pieceColor;

	public PawnModel(Coord coord, PieceSquareColor pieceColor) {
		super();
		this.coord = coord ;
		this.pieceColor = pieceColor ;
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
	public PieceSquareColor getPieceColor() {
		return this.pieceColor;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[" + pieceColor.toString().charAt(0) + coord + "]";
	}

	@Override
	public void move(Coord coord) {
		if (!this.hasThisCoord(coord)) {
			this.coord = coord;
		}
	}

	@Override
	public boolean isMoveOk(Coord targetCoord, boolean isPieceToCapture) {
		if (this.hasThisCoord(targetCoord)) return false;

		// Si pas de piece a capturer alors déplacement de 1 sinon 2
		int deplacement = isPieceToCapture ? 2 : 1;

		// Vérifie que la case cible n'est pas en dehors du tableau
		if (!Coord.coordonnees_valides(targetCoord)) return false;

		boolean isValidColumn = (this.getColonne() - deplacement == targetCoord.getColonne() ||
				this.getColonne() + deplacement == targetCoord.getColonne());

		if (this.getPieceColor() == PieceSquareColor.WHITE) {
			// Vérifie que la case cible correspond bien à une case autorisée en fonction de la couleur de la piece
			return (this.getLigne() + deplacement == targetCoord.getLigne() && isValidColumn);
		}

		return (this.getLigne() - deplacement == targetCoord.getLigne() && isValidColumn);
	}

	@Override
	public List<Coord> getCoordsOnItinerary(Coord targetCoord) {

		List<Coord> coordsOnItinery = new LinkedList<Coord>();

		// TODO Atelier 2

		return coordsOnItinery;
	}


}

