package br.com.alura.xstream;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import br.com.alura.xstream.converter.CompraDiferenteConverter;
import br.com.alura.xstream.converter.LivroConverter;
import br.com.alura.xstream.converter.MusicaConverter;

/**
 * Classe que testa o processo de serialização/desserialização de um objeto da
 * classe <strong>Compra</strong> fazendo uso da API da biblioteca XStream.
 * 
 * 
 * @author Pedro Junior
 *
 */
public class CompraTest {

	private XStream xstream;

	@Before
	public void before() {
		xstream = new XStream();
		configurarXStreamParaCompraEProduto();
	}

	@Test
	public void deveSerializarCadaProdutoDeUmaCompra() {
		// @formatter:off
		String xmlEsperado = "<compra>\n" + 
				"  <id>15</id>\n" + 
				"  <produtos>\n" + 
				"    <produto codigo=\"1587\">\n" + 
				"      <nome>Geladeira</nome>\n" + 
				"      <preco>1000.0</preco>\n" + 
				"      <descrição>Geladeira de duas portas</descrição>\n" + 
				"    </produto>\n" + 
				"    <produto codigo=\"1588\">\n" + 
				"      <nome>Ferro de Passar</nome>\n" + 
				"      <preco>100.0</preco>\n" + 
				"      <descrição>Ferro com vaporizador</descrição>\n" + 
				"    </produto>\n" + 
				"  </produtos>\n" + 
				"</compra>";
		// @formatter:on

		Compra compra = compraGeladeiraEFerro();

		String xmlGerado = xstream.toXML(compra);

		Assert.assertEquals(xmlEsperado, xmlGerado);
	}

	@Test
	public void deveSerializarColecoesImplicitas() {
		// @formatter:off
		String xmlEsperado = "<compra>\n" + 
				"  <id>15</id>\n" + 
				"  <produto codigo=\"1587\">\n" + 
				"    <nome>Geladeira</nome>\n" + 
				"    <preco>1000.0</preco>\n" + 
				"    <descrição>Geladeira de duas portas</descrição>\n" + 
				"  </produto>\n" + 
				"  <produto codigo=\"1588\">\n" + 
				"    <nome>Ferro de Passar</nome>\n" + 
				"    <preco>100.0</preco>\n" + 
				"    <descrição>Ferro com vaporizador</descrição>\n" + 
				"  </produto>\n" + 
				"</compra>";
		// @formatter:on

		Compra compra = compraGeladeiraEFerro();

		/*
		 * Essa configuração no objeto XStream, faz com que o atributo 'produtos' da
		 * classe Compra, que por sua vez é uma coleção do tipo List<Produto>, não seja
		 * serializada no xml gerado. Por isso, o nome do método: ImplicitCollection.
		 */
		xstream.addImplicitCollection(Compra.class, "produtos");
		String xmlGerado = xstream.toXML(compra);

		Assert.assertEquals(xmlEsperado, xmlGerado);
	}

	@Test
	public void deveDesserializarUmaCompraComCadaProdutoDoXml() {
		// @formatter:off
		String xmlOrigem = "<compra>\n" + 
				"  <id>15</id>\n" + 
				"  <produtos>\n" + 
				"    <produto codigo=\"1587\">\n" + 
				"      <nome>Geladeira</nome>\n" + 
				"      <preco>1000.0</preco>\n" + 
				"      <descrição>Geladeira de duas portas</descrição>\n" + 
				"    </produto>\n" + 
				"    <produto codigo=\"1588\">\n" + 
				"      <nome>Ferro de Passar</nome>\n" + 
				"      <preco>100.0</preco>\n" + 
				"      <descrição>Ferro com vaporizador</descrição>\n" + 
				"    </produto>\n" + 
				"  </produtos>\n" + 
				"</compra>";
		// @formatter:on

		Compra compraDeserializada = (Compra) xstream.fromXML(xmlOrigem);

		Compra compraEsperada = compraGeladeiraEFerro();

		Assert.assertEquals(compraEsperada, compraDeserializada);
	}

	@Test
	public void deveSerializarDuasGeladeirasIguais() {
		// @formatter:off
		String xmlEsperado = "<compra>\n" + 
				"  <id>15</id>\n" + 
				"  <produtos>\n" + 
				"    <produto codigo=\"1587\">\n" + 
				"      <nome>Geladeira</nome>\n" + 
				"      <preco>1000.0</preco>\n" + 
				"      <descrição>Geladeira de duas portas</descrição>\n" + 
				"    </produto>\n" + 
				"    <produto codigo=\"1587\">\n" + 
				"      <nome>Geladeira</nome>\n" + 
				"      <preco>1000.0</preco>\n" + 
				"      <descrição>Geladeira de duas portas</descrição>\n" + 
				"    </produto>\n" + 
				"  </produtos>\n" + 
				"</compra>";
		// @formatter:on

		Compra compra = compraDuasGeladeirasIguais();
		/*
		 * Essa configuração do XStream faz com que ele não mantenha referencias para
		 * objetos já serializados, dessa forma gerando tags com os mesmo valores
		 * quantas vezes for necessário. Neste tipo de configuração, deve se levar em
		 * consideração eventuais ciclos entre os objetos para que não haja um
		 * CircularReferenceException.
		 * 
		 * O valor padrão do 'Mode' é XStream.XPATH_RELATIVE_REFERENCES
		 */
		xstream.setMode(XStream.NO_REFERENCES);

		String xmlGerado = xstream.toXML(compra);

		Assert.assertEquals(xmlEsperado, xmlGerado);
	}

	@Test
	public void deveSerializarLivroEMusica() {
		// @formatter:off
		String xmlEsperado = "<compra>\n" + 
			"  <id>15</id>\n" + 
			"  <produtos>\n" + 
			"    <livro codigo=\"1589\">\n" + 
			"      <nome>O Pássaro Raro</nome>\n" + 
			"      <preco>100.0</preco>\n" + 
			"      <descricao>Dez histórias sobre a existência</descricao>\n" + 
			"    </livro>\n" + 
			"    <musica codigo=\"1590\">\n" + 
			"      <nome>Meu passeio</nome>\n" + 
			"      <preco>100.0</preco>\n" + 
			"      <descricao>Música livre</descricao>\n" + 
			"    </musica>\n" + 
			"  </produtos>\n" + 
			"</compra>";
		// @formatter:on

		Compra compra = compraComLivroEMusica();

		String xmlGerado = xstream.toXML(compra);

		Assert.assertEquals(xmlEsperado, xmlGerado);
	}

	@Test
	public void deveUtilizarUmConversorTotalmenteCustomizado() {
		// @formatter:off
		String xmlEsperado = "<compra estilo=\"novo\">\n" + 
				"  <id>15</id>\n" +
				"  <fornecedor>guilherme.silveira@caelum.com.br</fornecedor>\n" +
				"  <endereco>\n" +
				"    <linha1>Rua Vergueiro 3185</linha1>\n" +
				"    <linha2>8 andar - São Paulo - SP</linha2>\n" +
				"  </endereco>\n" +
				"  <produtos>\n" + 
				"    <produto codigo=\"1587\">\n" + 
				"      <nome>Geladeira</nome>\n" + 
				"      <preco>1000.0</preco>\n" + 
				"      <descrição>Geladeira de duas portas</descrição>\n" + 
				"    </produto>\n" + 
				"    <produto codigo=\"1587\">\n" + 
				"      <nome>Geladeira</nome>\n" + 
				"      <preco>1000.0</preco>\n" + 
				"      <descrição>Geladeira de duas portas</descrição>\n" + 
				"    </produto>\n" + 
				"  </produtos>\n" + 
				"</compra>";
		// @formatter:on

		Compra compra = compraDuasGeladeirasIguais();

		// registra o conversor global para serializar/deserializar o xml de uma classe
		// ou parte de algum xml que deve ter alguma característica específica.
		xstream.registerConverter(new CompraDiferenteConverter());
		xstream.setMode(XStream.NO_REFERENCES);

		String xmlGerado = xstream.toXML(compra);
		Assert.assertEquals(xmlEsperado, xmlGerado);

		Compra deserializada = (Compra) xstream.fromXML(xmlGerado);
		Assert.assertEquals(compra, deserializada);
	}

	private void configurarXStreamParaCompraEProduto() {
		xstream.alias("produto", Produto.class);
		xstream.aliasField("descrição", Produto.class, "descricao");
		xstream.useAttributeFor(Produto.class, "codigo");

		xstream.alias("compra", Compra.class);
		xstream.alias("livro", Livro.class);
		xstream.alias("musica", Musica.class);

		xstream.registerConverter(new LivroConverter());
		xstream.registerConverter(new MusicaConverter());
	}

	private Produto ferro() {
		return new Produto("Ferro de Passar", 100.0, "Ferro com vaporizador", 1588);
	}

	private Produto geladeira() {
		return new Produto("Geladeira", 1000.0, "Geladeira de duas portas", 1587);
	}

	private Compra compraGeladeiraEFerro() {
		List<Produto> produtos = new ArrayList<>();
		produtos.add(geladeira());
		produtos.add(ferro());

		return new Compra(15, produtos);
	}

	private Compra compraDuasGeladeirasIguais() {
		Produto geladeira = geladeira();

		List<Produto> produtos = new ArrayList<>();
		produtos.add(geladeira);
		produtos.add(geladeira);

		return new Compra(15, produtos);
	}

	private Compra compraComLivroEMusica() {
		Livro livro = new Livro("O Pássaro Raro", 100.0, "Dez histórias sobre a existência", 1589);
		Musica musica = new Musica("Meu passeio", 100.0, "Música livre", 1590);

		List<Produto> produtos = new ArrayList<>();
		produtos.add(livro);
		produtos.add(musica);

		return new Compra(15, produtos);
	}
}
