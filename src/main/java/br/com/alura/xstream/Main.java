package br.com.alura.xstream;

import java.text.NumberFormat;
import java.util.Locale;

public class Main {
	public static void main(String[] args) {
		Locale brasil = new Locale("pt", "br");
		NumberFormat formatter = NumberFormat.getCurrencyInstance(brasil);
		String formatado = formatter.format(1000.0);
		System.out.println(formatado);
	}
}
