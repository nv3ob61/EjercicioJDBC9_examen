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

import excepciones.GenericaExcepcion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBaseDatos {

  public Connection abrirConexion() throws Exception {
    Connection connection = null;
    try {
      //    CONEXION Oracle
      Class.forName("oracle.jdbc.driver.OracleDriver");
      connection
              = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl",
                      "biblioteca", "cambiame");

    } catch (SQLException excepcion) {
      throw new GenericaExcepcion(50);
    }

    return connection;
  }

  public void cerrarConexion(Connection connection) throws GenericaExcepcion {
    try {
      if (connection != null) {
        connection.close();
      }
    } catch (SQLException excepcion) {
      throw new GenericaExcepcion(50);
    }
  }
}
