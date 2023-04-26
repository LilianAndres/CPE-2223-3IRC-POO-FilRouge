package model;

import java.util.Objects;

/**
 * @author francoiseperrin
 *
 * Coordonnées des PieceModel
 */
public class Coord implements Comparable<Coord> {
	
	private char colonne; 	// ['a'..'j']
	private int ligne;		// [10..1]
	static final int MAX = ModelConfig.LENGTH;	// 10

	public Coord(char colonne, int ligne) {
		super();
		this.colonne = colonne;
		this.ligne = ligne;
	}

	public char getColonne() {
		return colonne;
	}

	public int getLigne() {
		return ligne;
	}

	@Override
	public String toString() {
		return "["+ colonne + "," + ligne + "]";
	}

	/**
	 * @param coord
	 * @return true si 'a' <= col < 'a'+MAX et 1 < lig <= MAX
	 */
	public static boolean coordonnees_valides(Coord coord){
		return ((coord.colonne >= 'a' && coord.colonne < 'a' + MAX) && (coord.ligne >= 1 && coord.ligne <= MAX));
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null) return false;
		if (!(obj instanceof Coord)) return false;
		Coord other = (Coord) obj;
		return (this.colonne == other.colonne && this.ligne == other.ligne);
	}

	@Override
	public int hashCode() {
		final int prime = 31; // prime number > MAX (avoid hash collision)
		int result = 1;
		result = prime * result + ligne;
		result = prime * result + colonne;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * 
	 * La méthode compareTo() indique comment comparer un objet à l'objet courant
	 * selon l'ordre dit naturel
	 * Dans cet application, nous décidons que l'ordre naturel est celui 
	 * correspondant au N° de la case d'un tableau 2D représenté par la Coord
	 * ainsi le N° 1 correspond à la Coord ['a', 10], le N° 100 correspond à la Coord ['j', 1]  
	 */
	@Override
	public int compareTo(Coord obj) {
		int thisNum = (MAX - this.ligne) * 10 + (this.colonne - 'a');
		int otherNum = (MAX - obj.ligne) * 10 + (obj.colonne - 'a');
		return Integer.compare(otherNum, thisNum);
	}
}
