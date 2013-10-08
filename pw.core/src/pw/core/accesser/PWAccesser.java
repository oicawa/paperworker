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

package pw.core.accesser;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;

import pw.core.PWError;
import pw.core.PWPropertyLoader;

public class PWAccesser implements Closeable {
	
	private Connection connection;
	private static HashMap<String, PWAccesser> connections = new HashMap<String, PWAccesser>();
	
	public static PWAccesser getAccesser(String userId) {
		
		if (connections.containsKey(userId)) {
			return connections.get(userId);
		}
		
		String driverClassPath = PWPropertyLoader.getValue("jdbcDriver");
		try {
			Class.forName(driverClassPath).newInstance();
		} catch (InstantiationException e) {
			throw new PWError(e, e.getMessage());
		} catch (IllegalAccessException e) {
			throw new PWError(e, e.getMessage());
		} catch (ClassNotFoundException e) {
			throw new PWError(e, e.getMessage());
		}
		
		Properties properties = new Properties();
//		props.put("user", "sa");
//		props.put("password", "");
		Connection connection;
		try {
			String connectionString = PWPropertyLoader.getValue("jdbcConnection");
			connection = DriverManager.getConnection(connectionString, properties);
		} catch (SQLException e) {
			throw new PWError(e, e.getMessage());
		}
		
		boolean autoCommit = false;
		try {
			connection.setAutoCommit(autoCommit);
		} catch (SQLException e) {
			throw new PWError(e, "SQL Connection could not be set 'AutoCommit'. [value = %s]", autoCommit);
		}
		
		connections.put(userId, new PWAccesser(connection));
		
		return connections.get(userId);
	}
	
	private PWAccesser(Connection connection) {
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
			throw new PWError(e, e.getMessage());
		}
	}
	
	public void select(PWQuery query, PWAfterQuery<ResultSet> afterQuery) {
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
					throw new PWError(e, e.getMessage());
		        } finally {
		            rs.close();
		        }
	        } catch (SQLException e) {
				throw new PWError(e, e.getMessage());
			} finally {
		        st.close();
			}
		} catch (SQLException e) {
			throw new PWError(e, e.getMessage());
		}
	}
	
	public void execute(PWQuery... queries) {
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
					throw new PWError(e, e.getMessage());
				} finally {
			        st.close();
				}
			}
	        connection.commit();
		} catch (SQLException e) {
			throw new PWError(e, e.getMessage());
		}
	}
}
