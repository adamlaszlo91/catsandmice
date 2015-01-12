package hu.atw.eve_hci001.catsandmice.model.species;

import java.util.BitSet;

/**
 * This class represents a mouse.
 * 
 * @author L�szl� �d�m
 *
 */
public class Mouse extends Species {

	public Mouse(int generation, BitSet geneSequence) {
		super(generation, geneSequence);
	}

	@Override
	public String toString() {
		String s = "Mouse";
		s += "\ngeneration: " + generation;
		s += "\nspeed:" + phenomeValue;
		s += "\ngene sequence: " + getStringValueOfGenome();
		s += "\ndetailed: " + getstringValueOfCaluclation();
		return s;
	}

}
