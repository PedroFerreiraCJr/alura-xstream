package br.com.alura.xstream.converter;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import br.com.alura.xstream.Musica;

public class MusicaConverter implements Converter {
	public boolean canConvert(Class clazz) {
		return clazz.equals(Musica.class);
	}

	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
		Musica musica = (Musica) value;
		writer.addAttribute("codigo", String.valueOf(musica.getCodigo()));
		writer.startNode("nome");
		writer.setValue(musica.getNome());
		writer.endNode();
		writer.startNode("preco");
		writer.setValue(String.valueOf(musica.getPreco()));
		writer.endNode();
		writer.startNode("descricao");
		writer.setValue(musica.getDescricao());
		writer.endNode();
	}

	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		int codigo = Integer.parseInt(reader.getAttribute("codigo"));
		reader.moveDown();
		String nome = reader.getValue();
		reader.moveUp();
		return new Musica(nome, 1.0, "", codigo);
	}
}
