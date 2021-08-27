package br.com.alura.xstream.converter;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import br.com.alura.xstream.Livro;

public class LivroConverter implements Converter {
	public boolean canConvert(Class clazz) {
		return clazz.equals(Livro.class);
	}

	public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
		Livro livro = (Livro) value;
		writer.addAttribute("codigo", String.valueOf(livro.getCodigo()));
		writer.startNode("nome");
		writer.setValue(livro.getNome());
		writer.endNode();
		writer.startNode("preco");
		writer.setValue(String.valueOf(livro.getPreco()));
		writer.endNode();
		writer.startNode("descricao");
		writer.setValue(livro.getDescricao());
		writer.endNode();
	}

	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		int codigo = Integer.parseInt(reader.getAttribute("codigo"));
		reader.moveDown();
		String nome = reader.getValue();
		reader.moveUp();
		return new Livro(nome, 1.0, "", codigo);
	}
}
