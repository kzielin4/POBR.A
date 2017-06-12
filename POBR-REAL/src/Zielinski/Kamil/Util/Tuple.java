/*
 *  Klasa template przechowuj¹ca 2 zmienne dowolnego typu
 */
package Zielinski.Kamil.Util;

public class Tuple<T1, T2>
{

	public final T1 _1;
	public final T2 _2;

	private Tuple(T1 _1, T2 _2) {
		this._1 = _1;
		this._2 = _2;
	}

	public static <T1, T2> Tuple<T1, T2> from(T1 t1, T2 t2)
	{
		return new Tuple<>(t1, t2);
	}
}
