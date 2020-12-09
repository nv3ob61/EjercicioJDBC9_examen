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
package negocio;

import datos.ConexionBaseDatos;
import datos.LibrosDatos;
import datos.LibrosPDF;
import datos.LibrosXML;
import encapsuladores.Libro;
import encapsuladores.ParametrosListado;
import java.sql.Connection;
import java.util.List;

public class LibrosNegocio {

  public Libro consultarPorIdLibro(Libro libro) throws Exception {
    Connection connection = null;
    Libro libroObtenido = null;
    ConexionBaseDatos conexionBaseDatos = new ConexionBaseDatos();
    try {
      connection = conexionBaseDatos.abrirConexion();
      libroObtenido = new LibrosDatos().consultarPorIdLibro(connection, libro);
    } catch (Exception excepcion) {
      throw excepcion;
    } finally {
      conexionBaseDatos.cerrarConexion(connection);
    }

    return libroObtenido;
  }

  public List<Libro> consultarTodos() throws Exception {
    Connection connection = null;
    ConexionBaseDatos conexionBaseDatos = new ConexionBaseDatos();
    List<Libro> listaLibros = null;

    try {
      connection = conexionBaseDatos.abrirConexion();
      listaLibros = new LibrosDatos().consultarTodos(connection);
    } catch (Exception excepcion) {
      throw excepcion;
    } finally {
      conexionBaseDatos.cerrarConexion(connection);
    }

    return listaLibros;
  }

  public Integer consultarNumeroFilas() throws Exception {
    Connection connection = null;
    ConexionBaseDatos conexionBaseDatos = new ConexionBaseDatos();
    Integer numFilas = null;

    try {
      connection = conexionBaseDatos.abrirConexion();
      numFilas = new LibrosDatos().consultarNumeroFilas(connection);
    } catch (Exception excepcion) {
      throw excepcion;
    } finally {
      conexionBaseDatos.cerrarConexion(connection);
    }

    return numFilas;
  }

  public void generarPDF() throws Exception {
    Connection connection = null;
    ConexionBaseDatos conexionBaseDatos = new ConexionBaseDatos();
    List<Libro> listaLibros;

    ParametrosListado parametrosListado = new ParametrosListado();
    parametrosListado.setNumeroFilasPagina(10);

    try {
      connection = conexionBaseDatos.abrirConexion();
      listaLibros = new LibrosDatos().consultarTodos(connection);
      new LibrosPDF().generarPDF(listaLibros, parametrosListado);
    } catch (Exception excepcion) {
      throw excepcion;
    } finally {
      conexionBaseDatos.cerrarConexion(connection);
    }
  }

  public void generarPDFid(Libro libro) throws Exception {
    Connection connection = null;
    Libro libroObtenido;
    ConexionBaseDatos conexionBaseDatos = new ConexionBaseDatos();
    try {
      connection = conexionBaseDatos.abrirConexion();
      libroObtenido = new LibrosDatos().consultarPorIdLibro(connection, libro);
      new LibrosPDF().generarPDFid(libroObtenido);
    } catch (Exception excepcion) {
      throw excepcion;
    } finally {
      conexionBaseDatos.cerrarConexion(connection);
    }

  }

  public void generarXMLtodos() throws Exception {
    Connection connection = null;
    ConexionBaseDatos conexionBaseDatos = new ConexionBaseDatos();
    List<Libro> listaLibros;

    ParametrosListado parametrosListado = new ParametrosListado();
    parametrosListado.setNumeroFilasPagina(10);

    try {
      connection = conexionBaseDatos.abrirConexion();
      listaLibros = new LibrosDatos().consultarTodos(connection);
      new LibrosXML().generarXML(listaLibros);
    } catch (Exception excepcion) {
      throw excepcion;
    } finally {
      conexionBaseDatos.cerrarConexion(connection);
    }

  }

  public void generarXMLid(Libro libro) throws Exception {
    Connection connection = null;
    Libro libroObtenido;
    ConexionBaseDatos conexionBaseDatos = new ConexionBaseDatos();
    try {
      connection = conexionBaseDatos.abrirConexion();
      libroObtenido = new LibrosDatos().consultarPorIdLibro(connection, libro);
      new LibrosXML().generarXMLid(libroObtenido);
    } catch (Exception excepcion) {
      throw excepcion;
    } finally {
      conexionBaseDatos.cerrarConexion(connection);
    }

  }

}
