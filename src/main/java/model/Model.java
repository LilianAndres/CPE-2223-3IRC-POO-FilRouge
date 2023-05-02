package model;


import java.util.List;

import controller.OutputModelData;
import nutsAndBolts.PieceSquareColor;

/**
 * @author francoise.perrin
 *
 * Cette classe gère les aspects métiers du jeu de dame
 * indépendamment de toute vue
 * 
 * Elle délègue à son objet ModelImplementor 
 * le stockage des PieceModel dans une collection
 * 
 * Les pièces sont capables de se déplacer d'une case en diagonale 
 * si la case de destination est vide
 * 
 * Ne sont pas gérés les prises, les rafles, les dames, 
 * 
 * N'est pas géré le fait que lorsqu'une prise est possible
 * une autre pièce ne doit pas être jouée
 * 
 */
public class Model implements BoardGame<Coord> {

	private PieceSquareColor currentGamerColor;    // couleur du joueur courant

	private ModelImplementor implementor;        // Cet objet sait communiquer avec les PieceModel

	public Model() {
		super();
		this.implementor = new ModelImplementor();
		this.currentGamerColor = ModelConfig.BEGIN_COLOR;

		System.out.println(this);
	}

	@Override
	public String toString() {
		return implementor.toString();
	}


	/**
	 * Actions potentielles sur le model : move, capture, promotion pion, rafles
	 */
	@Override
	public OutputModelData<Coord> moveCapturePromote(Coord toMovePieceCoord, Coord targetSquareCoord) {

		OutputModelData<Coord> outputModelData = null;

		boolean isMoveDone = false;
		Coord toCapturePieceCoord = null;
		Coord toPromotePieceCoord = null;
		PieceSquareColor toPromotePieceColor = null;

		// Si la pièce est déplaçable (couleur du joueur courant et case arrivée disponible)
		if (!this.isPieceMoveable(toMovePieceCoord, targetSquareCoord)) return null;

		// S'il n'existe pas plusieurs pièces sur le chemin
		if (!this.isThereMaxOnePieceOnItinerary(toMovePieceCoord, targetSquareCoord)) return null;

		//Recherche coord de l'éventuelle pièce à prendre
		toCapturePieceCoord = this.getToCapturePieceCoord(toMovePieceCoord, targetSquareCoord);

		// si le déplacement est légal (en diagonale selon algo pion ou dame)
			boolean isPieceToCapture = toCapturePieceCoord != null;
			if (!this.isMovePiecePossible(toMovePieceCoord, targetSquareCoord, isPieceToCapture)) return null;

		// déplacement effectif de la pièce
		this.movePiece(toMovePieceCoord, targetSquareCoord);
		isMoveDone = true;

		// suppression effective de la pièce prise
		this.remove(toCapturePieceCoord);

		// promotion éventuelle de la pièce après déplacement
		if (true) {    // TODO : Test à changer atelier 3

			// TODO atelier 3
		}

		// S'il n'y a pas eu de prise
		// ou si une rafle n'est pas possible alors changement de joueur
		if (true) {    // TODO : Test à changer atelier 4
			this.switchGamer();
		}
		System.out.println(this);

		// Constitution objet de données avec toutes les infos nécessaires à la view
		outputModelData = new OutputModelData<Coord>(
				isMoveDone,
				toCapturePieceCoord,
				toPromotePieceCoord,
				toPromotePieceColor);

		return outputModelData;

	}

	/**
	 * @param toMovePieceCoord
	 * @param targetSquareCoord
	 * @return true si la PieceModel à déplacer est de la couleur du joueur courant
	 * et que les coordonnées d'arrivées soient dans les limites du tableau
	 * et qu'il n'y ait pas de pièce sur la case d'arrivée
	 */
	boolean isPieceMoveable(Coord toMovePieceCoord, Coord targetSquareCoord) { // TODO : remettre en "private" après test unitaires
		boolean bool = false;

		// TODO : à compléter atelier 4 pour gérer les rafles

		bool = this.implementor.isPiecehere(toMovePieceCoord)
				&& this.implementor.getPieceColor(toMovePieceCoord) == this.currentGamerColor
				&& Coord.coordonnees_valides(targetSquareCoord)
				&& !this.implementor.isPiecehere(targetSquareCoord) ;

		return bool ;
	}

	/**
	 * @param toMovePieceCoord
	 * @param targetSquareCoord
	 * @return true s'il n'existe qu'1 seule pièce à prendre d'une autre couleur sur la trajectoire
	 * ou pas de pièce à prendre
	 */
	private boolean isThereMaxOnePieceOnItinerary(Coord toMovePieceCoord, Coord targetSquareCoord) {
		List<Coord> coordOnItinerary = implementor.getCoordsOnItinerary(toMovePieceCoord,targetSquareCoord);
		return coordOnItinerary.size() <= 1;
	}

	/**
	 * @param toMovePieceCoord
	 * @param targetSquareCoord
	 * @return les coord de la pièce à prendre, null sinon
	 */
	private Coord getToCapturePieceCoord(Coord toMovePieceCoord, Coord targetSquareCoord) {
		char column = (char)(Math.abs(toMovePieceCoord.getColonne() + targetSquareCoord.getColonne()) / 2);
		int line = Math.abs(toMovePieceCoord.getLigne() + targetSquareCoord.getLigne()) / 2;
		Coord toCapturePieceCoord = new Coord(column, line);

		if (toCapturePieceCoord.equals(toMovePieceCoord) || toCapturePieceCoord.equals(targetSquareCoord)) {
			return null;
		}
		return toCapturePieceCoord;
	}

	/**
	 * @param initCoord
	 * @param targetCoord
	 * @param isPieceToCapture
	 * @return true si le déplacement est légal
	 * (s'effectue en diagonale, avec ou sans prise)
	 * La PieceModel qui se trouve aux coordonnées passées en paramètre
	 * est capable de répondre à cette question (par l'intermédiare du ModelImplementor)
	 */
	boolean isMovePiecePossible(Coord toMovePieceCoord, Coord targetSquareCoord, boolean isPieceToCapture) { // TODO : remettre en "private" après test unitaires
		boolean isMovePossible = this.implementor.isMovePieceOk(toMovePieceCoord, targetSquareCoord, isPieceToCapture );
		System.out.println("isMovePiecePossible " + isMovePossible);
		return isMovePossible;
	}

	/**
	 * @param toMovePieceCoord
	 * @param targetSquareCoord
	 * Déplacement effectif de la PieceModel
	 */
	void movePiece(Coord toMovePieceCoord, Coord targetSquareCoord) { // TODO : remettre en "private" après test unitaires
		this.implementor.movePiece(toMovePieceCoord, targetSquareCoord);
	}

	/**
	 * @param toCapturePieceCoord
	 * Suppression effective de la pièce capturée
	 */
	private void remove(Coord toCapturePieceCoord) {
		this.implementor.removePiece(toCapturePieceCoord);
	}

	void switchGamer() { // TODO : remettre en "private" après test unitaires
		this.currentGamerColor = (PieceSquareColor.WHITE).equals(this.currentGamerColor) ?
				PieceSquareColor.BLACK : PieceSquareColor.WHITE;

	}


}