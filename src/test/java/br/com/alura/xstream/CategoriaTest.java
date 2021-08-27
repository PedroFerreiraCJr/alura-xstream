package br.com.alura.xstream;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.TreeMarshaller.CircularReferenceException;

/**
 * Classe que testa o processo de serialização de um objeto da classe
 * <strong>Categoria</strong> fazendo uso da API da biblioteca XStream.
 * 
 * 
 * @author Pedro Junior
 *
 */
public class CategoriaTest {

	private static XStream xstream;

	@BeforeClass
	public static void setUp() {
		xstream = new XStream();
		xstream.alias("categoria", Categoria.class);
	}

	@Test(expected = CircularReferenceException.class)
	public void naoDeveSerializarUmCicloNoModoSemReferencias() {
		Categoria esporte = new Categoria(null, "esporte");
		Categoria futebol = new Categoria(esporte, "futebol");
		Categoria geral = new Categoria(futebol, "geral");
		esporte.setPai(geral);

		xstream.setMode(XStream.NO_REFERENCES);
		xstream.toXML(esporte);
	}

	@Test
	public void deveSerializarUmCicloNoModoReferenciaId() {
		// @formatter:off
		String xmlEsperado = "<categoria id=\"1\">\n" +
	        "  <pai id=\"2\">\n" +
	        "    <pai id=\"3\">\n" +
	        "      <pai reference=\"1\"/>\n" +
	        "      <nome>futebol</nome>\n" +
	        "    </pai>\n" +
	        "    <nome>geral</nome>\n" +
	        "  </pai>\n" +
	        "  <nome>esporte</nome>\n" +
	        "</categoria>";
		// @formatter:on

		Categoria esporte = new Categoria(null, "esporte");
		Categoria futebol = new Categoria(esporte, "futebol");
		Categoria geral = new Categoria(futebol, "geral");
		esporte.setPai(geral);

		xstream.setMode(XStream.ID_REFERENCES);
		String xmlGerado = xstream.toXML(esporte);

		Assert.assertEquals(xmlEsperado, xmlGerado);
	}
}
