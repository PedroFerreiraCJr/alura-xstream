package br.com.alura.xstream;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import br.com.alura.xstream.converter.PrecoConverter;
import br.com.alura.xstream.converter.PrecoSimplesConverter;

/**
 * Classe que testa o processo de serialização/desserialização de um objeto da
 * classe <strong>Produto</strong> fazendo uso da API da biblioteca XStream.
 * 
 * 
 * @author Pedro Junior
 *
 */
public class ProdutoTest {

	private static XStream xstream;

	@BeforeClass
	public static void setUp() {
		xstream = new XStream();
	}

	@Test
	public void deveSerializarXmlComNomePrecoDescricaoAdequados() {
		// @formatter:off
		String xmlEsperado =
			"<produto codigo=\"1587\">\n" + 
			"  <nome>Geladeira</nome>\n" + 
			"  <preco>1000.0</preco>\n" + 
			"  <descrição>Geladeira de duas portas</descrição>\n" + 
			"</produto>";
		// @formatter:on

		Produto geladeira = new Produto("Geladeira", 1000.0, "Geladeira de duas portas", 1587);

		// configuração para que o xml gerado pelo xstream deva trocar o nome da classe
		// totalmente qualificado pela tag <produto></produto>;
		xstream.alias("produto", Produto.class);

		// configuração para que o xml gerado pelo xstream deva trocar o nome da tag
		// para o atributo 'descricao' da classe Produto para <descrição></descrição>;
		xstream.aliasField("descrição", Produto.class, "descricao");

		// configuração para que o xml gerado pelo xstream deva, ao invés de gerar uma
		// tag <codigo></codigo>, gere um atributo da tag <produto
		// codigo="1587"></produto>;
		xstream.useAttributeFor(Produto.class, "codigo");
		String xmlGerado = xstream.toXML(geladeira);

		Assert.assertEquals(xmlEsperado, xmlGerado);
	}

	@Test
	public void deveSerializarXmlComNomePrecoNoFormatoDaMoedaBrasileira() {
		// @formatter:off
		String xmlEsperado =
			"<produto codigo=\"1587\">\n" + 
			"  <nome>Geladeira</nome>\n" + 
			"  <preco>R$ 1.000,00</preco>\n" + 
			"  <descrição>Geladeira de duas portas</descrição>\n" + 
			"</produto>";
		// @formatter:on

		Produto geladeira = new Produto("Geladeira", 1000.0, "Geladeira de duas portas", 1587);

		// configuração para que o xml gerado pelo xstream deva trocar o nome da classe
		// totalmente qualificado pela tag <produto></produto>;
		xstream.alias("produto", Produto.class);

		// configuração para que o xml gerado pelo xstream deva trocar o nome da tag
		// para o atributo 'descricao' da classe Produto para <descrição></descrição>;
		xstream.aliasField("descrição", Produto.class, "descricao");

		// configuração para que o xml gerado pelo xstream deva, ao invés de gerar uma
		// tag <codigo></codigo>, gere um atributo da tag <produto
		// codigo="1587"></produto>;
		xstream.useAttributeFor(Produto.class, "codigo");

		// configuração para que o xml gerado pelo xstream deva formatar o valor do
		// atributo preco da classe produto para o formato moeda brasileira;
		xstream.registerLocalConverter(Produto.class, "preco", new PrecoSimplesConverter());
		
		String xmlGerado = xstream.toXML(geladeira);

		Assert.assertEquals(xmlEsperado, xmlGerado);
	}
}
