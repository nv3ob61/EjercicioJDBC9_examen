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
import excepciones.GenericaExcepcion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LibrosDatos {

  public Libro consultarPorIdLibro(Connection connection, Libro libro) throws Exception {
    Libro libroObtenido = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    try {
      String sql = "SELECT id_libro, titulo, generos.codigo, generos.descripcion, "
              + "fecha_edicion, numero_paginas, premiado FROM generos "
              + "INNER JOIN libros ON generos.codigo = libros.genero "
              + "WHERE id_libro = CAST(? AS CHAR(5))";
      preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, libro.getIdLibro());
      resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        libroObtenido = new Libro();
        libroObtenido.setIdLibro(resultSet.getString(1));
        libroObtenido.setTitulo(resultSet.getString(2));
        libroObtenido.setGenero(resultSet.getString(3));
        libroObtenido.setDescripcion(resultSet.getString(4));
        libroObtenido.setFechaEdicion(resultSet.getDate(5));
        libroObtenido.setNumeroPaginas(resultSet.getInt(6));
        byte premiado = resultSet.getByte(7);
        if (premiado == 1) {
          libroObtenido.setPremiado(true);
        } else {
          libroObtenido.setPremiado(false);
        }
      }
    } catch (SQLException excepcion) {
      throw new GenericaExcepcion(83);
    } finally {
      if (resultSet != null) {
        resultSet.close();
      }
      if (preparedStatement != null) {
        preparedStatement.close();
      }
    }

    return libroObtenido;
  }

  public List<Libro> consultarTodos(Connection connection) throws Exception {
    List<Libro> listaLibros = new ArrayList();
    ResultSet resultSet = null;
    Statement statement = null;
    try {
      String sql = "SELECT id_libro, titulo, generos.codigo, generos.descripcion, "
              + "fecha_edicion, numero_paginas, premiado FROM generos "
              + "INNER JOIN libros ON generos.codigo = libros.genero ORDER BY titulo";
      statement = connection.createStatement();
      resultSet = statement.executeQuery(sql);
      while (resultSet.next()) {
        Libro libro = new Libro();
        libro.setIdLibro(resultSet.getString(1));
        libro.setTitulo(resultSet.getString(2));
        libro.setGenero(resultSet.getString(3));
        libro.setDescripcion(resultSet.getString(4));
        libro.setFechaEdicion(resultSet.getDate(5));
        libro.setNumeroPaginas(resultSet.getInt(6));
        byte premiado = resultSet.getByte(7);
        if (premiado == 1) {
          libro.setPremiado(true);
        } else {
          libro.setPremiado(false);
        }
        listaLibros.add(libro);
      }
    } catch (SQLException excepcion) {
      throw new GenericaExcepcion(84);
    } finally {
      if (resultSet != null) {
        resultSet.close();
      }
      if (statement != null) {
        statement.close();
      }
    }
    return listaLibros;
  }

  public Integer consultarNumeroFilas(Connection connection) throws Exception {
    Integer numFilas = null;
    ResultSet resultSet = null;
    Statement statement = null;
    String sql = "SELECT COUNT(*) FROM libros";

    try {
      statement = connection.createStatement();
      resultSet = statement.executeQuery(sql);
      if (resultSet.next()) {
        numFilas = resultSet.getInt(1);
      }
    } catch (SQLException excepcion) {
      throw new GenericaExcepcion(85);
    } finally {
      if (resultSet != null) {
        resultSet.close();
      }
      if (statement != null) {
        statement.close();
      }
    }

    return numFilas;
  }
}
