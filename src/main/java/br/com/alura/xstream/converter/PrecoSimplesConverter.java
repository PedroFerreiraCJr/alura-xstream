package br.com.alura.xstream.converter;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.SingleValueConverter;

public class PrecoSimplesConverter implements SingleValueConverter {

	@Override
	public boolean canConvert(Class type) {
		return type.isAssignableFrom(Double.class);
	}

	@Override
	public String toString(Object object) {
		return getFormatter().format(object);
	}

	@Override
	public Object fromString(String value) {
		try {
			return getFormatter().parse(value);
		} catch (ParseException e) {
			throw new ConversionException("Não consegui converter: " + value, e);
		}
	}

	private NumberFormat getFormatter() {
		Locale brasil = new Locale("pt", "br");
		NumberFormat formatter = NumberFormat.getCurrencyInstance(brasil);
		return formatter;
	}
}
