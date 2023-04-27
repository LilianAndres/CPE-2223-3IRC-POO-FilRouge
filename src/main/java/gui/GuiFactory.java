package gui;

import java.io.InputStream;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import nutsAndBolts.PieceSquareColor;


/**
 * @author francoise.perrin
 * 
 * Cette classe est responsable de :
 * 		créer les cases noires et blanches et les positionner au bon endroit sur le damier
 * 		créer les pions noirs et blancs en leur affectant une image et les positionner sur leur case initiale
 *		promouvoir les pions en dame en changeant leur image
 */
public class GuiFactory {

	
	/**
	 * @param col
	 * @param ligne
	 * @return Une case noire ou blanche en alternance
	 * la case en bas à gauche est noire
	 */
	public static SquareGui createSquare(int col, int ligne) {

		SquareGui square = null;
		PieceSquareColor squareColor;

		// sélection de la couleur de la case
		if ((col % 2 == 0 && ligne % 2 == 0) || (col % 2 != 0 && ligne % 2 != 0)) {
			squareColor = PieceSquareColor.WHITE;
		} else {
			squareColor = PieceSquareColor.BLACK;
		}
		square = new SquareGui(squareColor);
		
		// la couleur est définie par les valeurs par défaut de configuration
		Color color = PieceSquareColor.BLACK.equals(squareColor) ? GuiConfig.CASEBLACK : GuiConfig.CASEWHITE;
		square.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
		square.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		
		return square;
	}

	/**
	 * @param col
	 * @param ligne
	 * @return une PieceGui si col/ligne correspond à cases noires
	 * des 4 lignes du haut (piece noire) et du bas du damier (piece blanche)
	 */
	public static PieceGui createPiece(int col, int ligne) {

		PieceGui pieceGui = null;
		PieceSquareColor pieceColor = null;

		if  ( !((col % 2 == 0 && ligne % 2 == 0) || (col % 2 != 0 && ligne % 2 != 0)) ) {
			if (ligne < 4)
				pieceColor = PieceSquareColor.BLACK;
			if (ligne > 5)
				pieceColor = PieceSquareColor.WHITE;
		}
		if (pieceColor != null) {
			Image image = GuiFactory.createImage(pieceColor, true);
			pieceGui = new PieceGui(image,pieceColor);
		}

		return pieceGui;
	}

	/**
	 * @param piece
	 * @param promotedPieceColor
	 * la promotion consiste à changer l'image de la PieceGui
	 */
	public static void PromotePiece(ImageView piece, PieceSquareColor promotedPieceColor) {

		// ToDo Atelier 3
		
	}
	
	/**
	 * @param pieceColor
	 * @param ispawn
	 * @return une image créée à partir d'un fichier png
	 */
	private static Image createImage(PieceSquareColor pieceColor, boolean ispawn) {

		Image image = null;
		String nomImageFile = null;
		InputStream imageFileInputStream = null;

		if (ispawn) {
			nomImageFile = pieceColor == PieceSquareColor.BLACK ? "PionNoir.png" : "PionBlanc.png";
		}
		else {	
			nomImageFile = pieceColor == PieceSquareColor.BLACK ? "DameNoire.png" : "DameBlanche.png";
		}

		// Deux aspects à prendre en compte pour comprendre le .getResourceAsStream() ci-après :
		//
		// 1- Les bonnes pratiques pour résoudre des chemins relatifs ; lire :
		//		* https://docs.oracle.com/javase/tutorial/uiswing/components/icon.html#getresource__1
		//
		// 2- La gestion des ressources par Maven dans sa configuration par défaut ; lire :
		//		* https://maven.apache.org/plugins/maven-resources-plugin/
		//		* https://maven.apache.org/plugins/maven-resources-plugin/examples/resource-directory.html
		//
		imageFileInputStream = GuiFactory.class.getResourceAsStream("/images/" + nomImageFile);

		if (imageFileInputStream != null) {
			image = new Image(imageFileInputStream);
		} else {
			System.err.printf(
				"Fichier '%s' non trouvé pour la pièce %s (ispawn=%s)\n", 
				nomImageFile, pieceColor, ispawn);
		}
		return image;
	}


}


