/* 
 * Copyright (C) 2020 monmo
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package datos;

import encapsuladores.Libro;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class LibrosXML {

  public static final String RAIZ_LISTADO = "LISTADO";
  public static final String RAIZ_LIBRO = "LIBRO";
  
  //la ruta de XML_LISTADO por ejemplo:
  //C:\\Users\\nv3ob61\\LISTADO_LIBROS.xml
  public static final String XML_LISTADO = "C:\\Users\\ciclom\\Desktop\\exportsServidor\\LISTADO_LIBROS.xml";   //add file
  
  //la ruta de XML_LIBRO por ejemplo:
  //C:\\Users\\nv3ob61\\LIBRO.xml
  public static final String XML_LIBRO = "C:\\Users\\ciclom\\Desktop\\exportsServidor\\LIBRO.xml";  //add file
          
  //RUTA_FICHEROS es la carpeta donde se generan los ficheros:        
  //C:\\Users\\nv3ob61\\ficheros_libro\\
  public static final String RUTA_FICHEROS = "C:\\Users\\ciclom\\Desktop\\exportsServidor\\";  //add folder

  private static final DateFormat DF = new SimpleDateFormat("yyyy-mm-dd");

  public void generarXML(List<Libro> listaLibros) {
    Element raiz = new Element(RAIZ_LISTADO);

    //       L I N E A S
    Element lineas = new Element("LIBROS");
    raiz.addContent(lineas);

    Element libro;
    Element id;
    Element titulo;
    Element genero;
    Element descripcion;
    Element fecha;
    Element paginas;
    Element premiado;

    for (Libro l : listaLibros) {
      //       L I N E A S
      libro = new Element("LIBRO");
      lineas.addContent(libro);

      id = new Element("ID");
      id.setText(l.getIdLibro());
      libro.addContent(id);

      titulo = new Element("TITULO");
      titulo.setText(l.getTitulo());
      libro.addContent(titulo);

      genero = new Element("GENERO");
      genero.setText(l.getGenero());
      libro.addContent(genero);

      descripcion = new Element("DESCRIPCION");
      descripcion.setText(l.getDescripcion());
      libro.addContent(descripcion);

      fecha = new Element("FECHA");
      fecha.setText(DF.format(l.getFechaEdicion()));
      libro.addContent(fecha);

      paginas = new Element("PAGINAS");
      paginas.setText(String.valueOf(l.getNumeroPaginas()));
      libro.addContent(paginas);

      premiado = new Element("PREMIADO");
      premiado.setText(String.valueOf(l.isPremiado()));
      libro.addContent(premiado);
    }

    //creacion del fichero
    crearFichero(raiz);
  }

  public void generarXMLid(Libro libro) {
    Element raiz = new Element(RAIZ_LIBRO);

    //       L I N E A S
    Element id = new Element("ID");
    id.setText(libro.getIdLibro());
    raiz.addContent(id);

    Element titulo = new Element("TITULO");
    titulo.setText(libro.getTitulo());
    raiz.addContent(titulo);

    Element genero = new Element("GENERO");
    genero.setText(libro.getGenero());
    raiz.addContent(genero);

    Element descripcion = new Element("DESCRIPCION");
    descripcion.setText(libro.getDescripcion());
    raiz.addContent(descripcion);

    Element fecha = new Element("FECHA");
    fecha.setText(DF.format(libro.getFechaEdicion()));
    raiz.addContent(fecha);

    Element paginas = new Element("PAGINAS");
    paginas.setText(String.valueOf(libro.getNumeroPaginas()));
    raiz.addContent(paginas);

    Element premiado = new Element("PREMIADO");
    premiado.setText(String.valueOf(libro.isPremiado()));
    raiz.addContent(premiado);

    //creacion del fichero
    crearFichero(raiz);
  }

  public void crearFichero(Element raiz) {
    //       C R E A     F I C H E R O
    Document document = new Document(raiz);

    Format format = Format.getPrettyFormat();
    format.setEncoding("ISO_8859_1");
    format.setIndent("   ");

    try {
      File ruta = new File(RUTA_FICHEROS);

      if (!ruta.exists()) {
        System.out.println("No existe la ruta : " + ruta.getName());
        if (ruta.mkdirs()) {
          System.out.println("Ruta creada");
        }
      }

      if (raiz.getName().equals(RAIZ_LISTADO)) {
        new XMLOutputter(format)
                .output(document, new OutputStreamWriter(
                        new FileOutputStream(
                                new File(XML_LISTADO)),
                        StandardCharsets.ISO_8859_1));
      } else if (raiz.getName().equals(RAIZ_LIBRO)) {
        new XMLOutputter(format)
                .output(document, new OutputStreamWriter(
                        new FileOutputStream(
                                new File(XML_LIBRO)),
                        StandardCharsets.ISO_8859_1));
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }
}
