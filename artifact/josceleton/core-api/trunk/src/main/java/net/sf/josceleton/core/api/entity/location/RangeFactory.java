package net.sf.josceleton.core.api.entity.location;

/**
 * @since 0.5
 */
public interface RangeFactory {

	// TODO 7.0 fuer Z als default range real end ist zuviel... schaun... evtl auf 3 (maximal 4) geben!
	/**
	 * Creates a <code>Range</code> object with default from start/end set to 0.0/1.0 (or 7.0 if it is Z) 
	 * 
	 * @since 0.5
	 */
	Range create(Direction direction, int toStart, int toEnd);

	/**
	 * @since 0.5
	 */
	Range createSpecific(float fromStart, float fromEnd, int toStart, int toEnd);
	
}
