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
import encapsuladores.ParametrosListado;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class LibrosPDF {

  public void generarPDF(List<Libro> listaLibros, 
          ParametrosListado parametrosListadoPDF, 
          String directorioCreacionPDFs) throws IOException {

    try (PDDocument pDDocument = new PDDocument()) {
      Libro[] lineasPagina = new Libro[parametrosListadoPDF.getNumeroFilasPagina()];
      int numeroPagina = 1;
      int contadorLineas = 0;

      inicializarLineasPagina(lineasPagina);

      for (int i = 0; i < listaLibros.size(); i++) {
        lineasPagina[contadorLineas] = listaLibros.get(i);
        contadorLineas++;
        if (contadorLineas == parametrosListadoPDF.getNumeroFilasPagina()) {
          escribirPagina(pDDocument, lineasPagina, numeroPagina++);
          inicializarLineasPagina(lineasPagina);
          contadorLineas = 0;
        }
      }

      if (contadorLineas > 0) {
        escribirPagina(pDDocument, lineasPagina, numeroPagina++);
      }

      String nombreArchivoPDF = directorioCreacionPDFs + "\\Listado_libros" 
              + new SimpleDateFormat("yyyyMMddHHmmss")
                      .format(new java.util.Date()) + ".pdf";
      
      pDDocument.save(nombreArchivoPDF);
    } catch (Exception e) {
      System.out.println("VAYA CAGADA, NO?");
    }
  }

  public void generarPDFid(Libro libro, String directorioCreacionPDFs) {
    try (PDDocument pDDocument = new PDDocument()) {

      int numeroPagina = 1;

      escribirPaginaSimple(pDDocument, libro, numeroPagina++);

      String nombreArchivoPDF = directorioCreacionPDFs + "\\libro_id" 
              + libro.getIdLibro() + ".pdf";
      
      pDDocument.save(nombreArchivoPDF);
    } catch (Exception e) {
      System.out.println("VAYA CAGADA, NO?");
    }
  }

  private void inicializarLineasPagina(Libro[] lineasPagina) {
    for (int i = 0; i < lineasPagina.length; i++) {
      lineasPagina[i] = null;
    }
  }

  private void escribirPagina(PDDocument pDDocument, 
          Libro[] lineasPagina, int numeroPagina) throws IOException {
    
    PDPage pDPage = new PDPage();
    pDDocument.addPage(pDPage);
    try (PDPageContentStream pDPageContentStream = new PDPageContentStream(pDDocument, pDPage)) {
      escribirCabecera(pDPageContentStream);
      int contadorLineas = escribirCuerpo(pDPageContentStream, lineasPagina);
      escribirPie(pDPageContentStream, numeroPagina, contadorLineas);
    }
  }

  private void escribirPaginaSimple(PDDocument pDDocument, 
          Libro libro, int numeroPagina) throws IOException {
    
    PDPage pDPage = new PDPage();
    pDDocument.addPage(pDPage);
    try (PDPageContentStream pDPageContentStream = 
            new PDPageContentStream(pDDocument, pDPage)) {
      
      escribirCabecera(pDPageContentStream);
      int contadorLineas = escribirCuerpoSimple(pDPageContentStream, libro);
      //anyadimos el +2 para que la linea del pie no salga tan pegada...
      escribirPie(pDPageContentStream, numeroPagina, contadorLineas +2);
    }
  }

  private void escribirCabecera(PDPageContentStream pDPageContentStream) throws IOException {

    escribirTexto(pDPageContentStream, PDType1Font.TIMES_BOLD, 12, 150, 730, "BIBLIOTECA MUNICIPAL DE VILLABOTIJO");
    escribirTexto(pDPageContentStream, PDType1Font.TIMES_BOLD, 10, 460, 705, "Fecha :");
    escribirTexto(pDPageContentStream, PDType1Font.TIMES_ROMAN, 10, 500, 705, new java.text.SimpleDateFormat("dd-MM-yyyy").format(new java.util.Date()));
    escribirLinea(pDPageContentStream, 30, 680, 580, 680);
    escribirTexto(pDPageContentStream, PDType1Font.TIMES_BOLD, 10, 52, 662, "Código");
    escribirTexto(pDPageContentStream, PDType1Font.TIMES_BOLD, 10, 93, 662, "Título");
    escribirTexto(pDPageContentStream, PDType1Font.TIMES_BOLD, 10, 252, 662, "Género");
    escribirTexto(pDPageContentStream, PDType1Font.TIMES_BOLD, 10, 382, 662, "Paginas");
    escribirTexto(pDPageContentStream, PDType1Font.TIMES_BOLD, 10, 432, 662, "Fecha");
    escribirLinea(pDPageContentStream, 30, 650, 580, 650);
  }

  private int escribirCuerpo(PDPageContentStream pDPageContentStream,
          Libro[] lineasPagina) throws IOException {

    int i;
    for (i = 0; i < lineasPagina.length; i++) {
      if (lineasPagina[i] != null) {
        escribirTexto(pDPageContentStream, PDType1Font.COURIER, 8, 55, 635 - (15 * (i)),
                String.format("%-7s", lineasPagina[i].getIdLibro())
                + String.format("%-30s", lineasPagina[i].getTitulo())
                + lineasPagina[i].getGenero() + " - "
                + String.format("%-32s", lineasPagina[i].getDescripcion())
                + String.format("%-8s", lineasPagina[i].getNumeroPaginas())
                + String.format("%-4s", lineasPagina[i].getFechaEdicion()));
      }
    }

    return i;
  }

  private int escribirCuerpoSimple(PDPageContentStream pDPageContentStream, 
          Libro lineasPagina) throws IOException {
    
    int i = 1;

    if (lineasPagina != null) {
      escribirTexto(pDPageContentStream, PDType1Font.COURIER, 8, 55, 635 - (15 * (i)),
              String.format("%-7s", lineasPagina.getIdLibro())
              + String.format("%-30s", lineasPagina.getTitulo())
              + lineasPagina.getGenero() + " - "
              + String.format("%-32s", lineasPagina.getDescripcion())
              + String.format("%-8s", lineasPagina.getNumeroPaginas())
              + String.format("%-4s", lineasPagina.getFechaEdicion()));
    }

    return i;
  }

  private void escribirPie(PDPageContentStream pDPageContentStream, 
          int numeroPagina, int contadorLineas) throws IOException {
    
    escribirLinea(pDPageContentStream, 30, 640 - (15 * (contadorLineas)), 580, 640 - (15 * (contadorLineas)));
    escribirTexto(pDPageContentStream, PDType1Font.TIMES_ROMAN, 8, 520, 625 - (15 * (contadorLineas)), "pág. " + numeroPagina);
  }

  private void escribirTexto(PDPageContentStream pDPageContentStream, 
          PDFont pdFont, float sizeFuente, float inicioH, float inicioV, String texto) throws IOException {
    
    pDPageContentStream.beginText();
    pDPageContentStream.setFont(pdFont, sizeFuente);
    pDPageContentStream.newLineAtOffset(inicioH, inicioV);
    pDPageContentStream.showText(texto);
    pDPageContentStream.endText();
  }

  private void escribirLinea(PDPageContentStream pDPageContentStream, 
          float inicioH, float inicioV, float finH, float finV) throws IOException {
    
    pDPageContentStream.moveTo(inicioH, inicioV);
    pDPageContentStream.lineTo(finH, finV);
    pDPageContentStream.stroke();
  }

}
