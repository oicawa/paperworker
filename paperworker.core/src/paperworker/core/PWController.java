package paperworker.core;

import java.io.Closeable;

public abstract class PWController implements Closeable {
	
	protected SqlAccesser accesser;
	
	public PWController() throws PWError {
		// TODO: SQL Driver class path must be get from System property or some outer resources?
		//accesser = SqlAccesser.getAccesser("jdbc:h2:~/paperworker.db");
		accesser = SqlAccesser.getAccesser("jdbc:h2:tcp://localhost/~/paperworker.db");
	}
	
	public void close() {
		accesser.close();
	}
}
