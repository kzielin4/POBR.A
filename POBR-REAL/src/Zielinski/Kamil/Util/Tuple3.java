/*
 *  Klasa template przechowująca 3 zmienne dowolnego typu
 */
package Zielinski.Kamil.Util;

public class Tuple3<T1, T2, T3>
{

	public final T1 _1;
	public final T2 _2;
	public final T3 _3;

	private Tuple3(T1 _1, T2 _2, T3 _3) {
		this._1 = _1;
		this._2 = _2;
		this._3 = _3;
	}

	public static <T1, T2, T3> Tuple3<T1, T2, T3> from(T1 t1, T2 t2, T3 t3)
	{
		return new Tuple3<>(t1, t2, t3);
	}
}