/*
 *  $Id: SqlAccesser.java 2013/09/21 3:03:36 Masamitsu Oikawa $
 *
 *  ===============================================================================
 *
 *   Copyright (C) 2013  Masamitsu Oikawa  <oicawa@gmail.com>
 *   
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *   
 *   The above copyright notice and this permission notice shall be included in
 *   all copies or substantial portions of the Software.
 *   
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *   THE SOFTWARE.
 *
 *  ===============================================================================
 */

package paperworker.core;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class SqlAccesser implements Closeable {
	
	private Connection connection;
	
	public static SqlAccesser getAccesser(String connectionString) throws PWError{
		String driverClassPath = "org.h2.Driver"; 
		try {
			Class.forName(driverClassPath).newInstance();
		} catch (InstantiationException e) {
			throw new PWError(e, "JDBC Driver class could not be instantiate. [%s]", driverClassPath);
		} catch (IllegalAccessException e) {
			throw new PWError(e, "JDBC Driver class was accessed illegally. [%s]", driverClassPath);
		} catch (ClassNotFoundException e) {
			throw new PWError(e, "JDBC Driver class was not found [%s]", driverClassPath);
		}
		
		Properties properties = new Properties();
//		props.put("user", "sa");
//		props.put("password", "");
//		Connection connection = DriverManager.getConnection("jdbc:h2:tcp://localhost:9092/MyDB", props);
		Connection connection;
		try {
			connection = DriverManager.getConnection(connectionString, properties);
		} catch (SQLException e) {
			throw new PWError(e, "SQL Connection could not be open. [%s]", connectionString);
		}
		
		boolean autoCommit = false;
		try {
			connection.setAutoCommit(autoCommit);
		} catch (SQLException e) {
			throw new PWError(e, "SQL Connection could not be set 'AutoCommit'. [value = %s]", autoCommit);
		}
		
		return new SqlAccesser(connection);
	}
	
	private SqlAccesser(Connection connection) {
		this.connection = connection;
	}
	
	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO: Must write to logger.
		}
	}
	
	public boolean existTable(String tableName) {
		DatabaseMetaData metadata;
		try {
			metadata = connection.getMetaData();
	        String types[] = { "TABLE" };
	        ResultSet rs = metadata.getTables(null, null, "%", types);	// TODO: what is the schema name?
	        while (rs.next()) {
	        	String dbSideName = rs.getString("TABLE_NAME").toLowerCase();
	        	String targetName = tableName.toLowerCase();
	        	if (dbSideName.equals(targetName))
	        		return true;
	        }
	        return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return true;
	}
	
	public void select(PWQuery query, PWAfterQuery<ResultSet> afterQuery) throws PWError, PWWarning {
        PreparedStatement st;
		try {
			st = connection.prepareStatement(query.getQuery());
			for (int i = 0; i < query.getValues().size(); i++) {
				Object value = query.getValues().get(i);
				st.setObject(i + 1, value);
			}
			try {
		        ResultSet rs = st.executeQuery();
		        try {
		        	afterQuery.run(rs);
		        } catch (Exception e) {
					throw new PWWarning(e, "SQL Result could not be gotton.");
		        } finally {
		            rs.close();
		        }
	        } catch (SQLException e) {
				throw new PWError(e, "SQL Result could not be gotton.");
			} finally {
		        st.close();
			}
		} catch (SQLException e) {
			throw new PWError(e, "SQL Statement could not be created.");
		}
	}
	
	public void execute(PWQuery... queries) throws PWError, PWWarning {
		try {
			for (PWQuery query : queries) {
				PreparedStatement st;
				st = connection.prepareStatement(query.getQuery());
	            try {
	    			for (int i = 0; i < query.getValues().size(); i++) {
	    				Object value = query.getValues().get(i);
	    				if (value != null && value.getClass().isEnum()) {
		    				st.setObject(i + 1, value.toString());
	    				} else {
		    				st.setObject(i + 1, value);
	    				}
	    			}
					st.execute();
				} catch (SQLException e) {
					throw new PWError(e, "SQL execute was failed.");
				} finally {
			        st.close();
				}
			}
	        connection.commit();
		} catch (SQLException e) {
			throw new PWError(e, "SQL PreparedStatement could not be created.");
		}
	}
}
