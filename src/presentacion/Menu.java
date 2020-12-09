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
package presentacion;

import encapsuladores.Libro;
import excepciones.GenericaExcepcion;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.List;
import negocio.LibrosNegocio;

public class Menu {

  public void ejecutarMenu() {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    byte opcionTecleada = 0;

    do {
      try {
        System.out.println("---------------------------- GESTIÓN LIBROS ----------------------------");
        System.out.println("1.- Consultar todos");
        System.out.println("2.- Generar PDF con lista de libros");
        System.out.println("3.- Generar XML con lista de libros");
        System.out.println("--------------------------------------");
        System.out.println("4.- Consultar por identificador de libro");
        System.out.println("5.- Generar PDF con ID libro");
        System.out.println("6.- Generar XML con ID libro");
        System.out.println("0.- Finalizar");
        System.out.println("------------------------------------------------------------------------");
        System.out.print("Seleccione opción : ");
        opcionTecleada = (byte) Integer.parseInt(bufferedReader.readLine());
        switch (opcionTecleada) {
          case 1:
            consultarTodos();
            break;
          case 2:
            generarPDFtodos();
            break;
          case 3:
            generarXMLtodos();
            break;
          case 4:
            consultarPorIdLibro(bufferedReader);
            break;
          case 5:
            generarPDFid(bufferedReader);
            break;
          case 6:
            generarXMLid(bufferedReader);
            break;
        }

      } catch (Exception exception) {
        gestionarExcepcion(exception);
      }
    } while (opcionTecleada != 0);
  }

<<<<<<< HEAD
=======
  private void gestionarExcepcion(Exception excepcion) {
    int codigoError = 0;
    String mensajeError = "";

    if (excepcion instanceof GenericaExcepcion) {
      GenericaExcepcion genericaExcepcion = (GenericaExcepcion) excepcion;
      codigoError = genericaExcepcion.getCodigoError();
      switch (genericaExcepcion.getCodigoError()) {
        case 50:
          mensajeError = "Se ha producido una situación de error como consecuencia de problemas con la conexión a la BD";
          break;
        case 83:
          mensajeError = "Se ha producido una situación de error en la BD al intentar consultar por identificador de libro";
          break;
        case 84:
          mensajeError = "Se ha producido una situación de error en la BD al intentar consultar todos los libros";
          break;
        case 85:
          mensajeError = "Se ha producido una situación de error en la BD al intentar consultar el número de libros";
          break;
      }
    } else {
      if (excepcion instanceof NumberFormatException) {
        mensajeError = "La totalidad de los dígitos deben ser numéricos";
      } else {
        mensajeError = excepcion.getMessage();
      }
    }
    System.out.println("Código de error: " + codigoError + "  -  " + mensajeError);
  }

>>>>>>> 26fd2a37335c80bc6eb55232d150103a56a7f8a0
  private void consultarPorIdLibro(BufferedReader bufferedReader) throws Exception {
    System.out.println("----------- CONSULTAR POR IDENTIFICADOR DE LIBRO -----------------");
    Libro libro = new Libro();
    System.out.print("identificador de libro a consultar : ");
    libro.setIdLibro(bufferedReader.readLine());
    Libro libroObtenido = new LibrosNegocio().consultarPorIdLibro(libro);
    if (libroObtenido != null) {
      System.out.println("SE HA ENCONTRADO EL LIBRO : ");
      System.out.println("identificador de libro : " + libroObtenido.getIdLibro());
      System.out.println("título : " + libroObtenido.getTitulo());
      System.out.println("género : " + libroObtenido.getGenero() + " - " + libroObtenido.getDescripcion());
      System.out.println("fecha edición : " + new SimpleDateFormat("yyyy-MM-dd").format(libroObtenido.getFechaEdicion()));
      System.out.println("número páginas : " + libroObtenido.getNumeroPaginas());
      String premiado = "NO";
      if (libroObtenido.isPremiado()) {
        premiado = "SI";
      }
      System.out.println("premiado : " + premiado);
    } else {
      System.out.println("NO EXISTE UN LIBRO CON EL IDENTIFICADOR INTRODUCIDO");
    }
  }

  private void generarXMLid(BufferedReader bf) throws Exception {
    Libro libro = new Libro();
    System.out.print("identificador de libro a consultar : ");
<<<<<<< HEAD
    libro.setIdLibro(bf.readLine());
    new LibrosNegocio().generarXMLid(libro);
  }

  private void generarPDFid(BufferedReader bf) throws Exception {
    Libro libro = new Libro();
    System.out.print("identificador de libro a consultar : ");
    libro.setIdLibro(bf.readLine());
    new LibrosNegocio().generarPDFid(libro);

=======
    libro.setIdLibro(bufferedReader.readLine());
    Libro libroObtenido = new LibrosNegocio().consultarPorIdLibro(libro);
    if (libroObtenido != null) {
      libro = libroObtenido;
    } else {
      System.out.println("NO EXISTE UN LIBRO CON EL IDENTIFICADOR INTRODUCIDO");
    }
    return libro;
  }
  
  private void generarXMLid(BufferedReader bf) throws Exception{
    Libro libro = consultarIdLibro(bf);
    if(libro != null){
      new LibrosNegocio().generarXMLid(libro);
    }
  }
  
  private void generarPDFid(BufferedReader bf) throws Exception{
    Libro libro = consultarIdLibro(bf);
    if(libro != null){
      new LibrosNegocio().generarPDFid(libro);
    }
>>>>>>> 26fd2a37335c80bc6eb55232d150103a56a7f8a0
  }

  private void consultarTodos() throws Exception {
    System.out.println("---------------------- LISTADO LIBROS ----------------------------");
    List<Libro> listaLibros = new LibrosNegocio().consultarTodos();
    for (int i = 0; i < listaLibros.size(); i++) {
      Libro libro = listaLibros.get(i);
      System.out.println("identificador de libro : " + libro.getIdLibro());
      System.out.println("título : " + libro.getTitulo());
      System.out.println("género : " + libro.getGenero() + " - " + libro.getDescripcion());
      System.out.println("fecha edición : " + new SimpleDateFormat("yyyy-MM-dd").format(libro.getFechaEdicion()));
      System.out.println("número páginas : " + libro.getNumeroPaginas());
      String premiado = "NO";
      if (libro.isPremiado()) {
        premiado = "SI";
      }
      System.out.println("premiado : " + premiado);
      System.out.println("--------------------------------------------------");
    }
  }

  private void generarPDFtodos() throws Exception {
    new LibrosNegocio().generarPDF();
  }

  private void generarXMLtodos() throws Exception {
    new LibrosNegocio().generarXMLtodos();
  }

  private void gestionarExcepcion(Exception excepcion) {
    int codigoError = 0;
    String mensajeError = "";

    if (excepcion instanceof GenericaExcepcion) {
      GenericaExcepcion genericaExcepcion = (GenericaExcepcion) excepcion;
      codigoError = genericaExcepcion.getCodigoError();
      switch (genericaExcepcion.getCodigoError()) {
        case 50:
          mensajeError = "Se ha producido una situación de error como consecuencia de problemas con la conexión a la BD";
          break;
        case 71:
          mensajeError = "No existe un libro con ese identificador";
          break;
        case 80:
          mensajeError = "Se ha producido una situación de error en la BD al intentar insertar libro";
          break;
        case 81:
          mensajeError = "Se ha producido una situación de error en la BD al intentar actualizar premiado";
          break;
        case 82:
          mensajeError = "Se ha producido una situación de error en la BD al intentar eliminar libro";
          break;
        case 83:
          mensajeError = "Se ha producido una situación de error en la BD al intentar consultar por identificador de libro";
          break;
        case 84:
          mensajeError = "Se ha producido una situación de error en la BD al intentar consultar todos los libros";
          break;
        case 85:
          mensajeError = "Se ha producido una situación de error en la BD al intentar consultar el número de libros";
          break;
        case 90:
          mensajeError = "Se ha producido una situación de error en la BD al intentar consultar la relación de géneros";
          break;
      }
    } else {
      if (excepcion instanceof NumberFormatException) {
        mensajeError = "La totalidad de los dígitos deben ser numéricos";
      } else {
        mensajeError = excepcion.getMessage();
      }
    }
    System.out.println("Código de error: " + codigoError + "  -  " + mensajeError);
  }
}
