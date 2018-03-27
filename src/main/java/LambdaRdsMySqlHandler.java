import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.sql.*;


public class LambdaRdsMySqlHandler implements RequestHandler<Object, String> {

    public String handleRequest(Object input, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("AWS Lambda Java 8 RDS Super Power\n");

        String currentTime = "unavailable";
        try {

            String endpoint = System.getenv("RDS_MYSQL_ENDPOINT");
            String port = System.getenv("RDS_MYSQL_PORT");
            String username = System.getenv("RDS_MYSQL_USERNAME");
            String password = System.getenv("RDS_MYSQL_PASSWORD");;
            String dbName = System.getenv("RDS_MYSQL_DB_NAME");

            Connection conn = DriverManager.getConnection("jdbc:mysql://"+ endpoint + ":" + port + "/" + dbName + "?useSSL=false", username, password);

            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT NOW()");

            if (resultSet.next()) {
                currentTime = resultSet.getObject(1).toString();
            }

            logger.log("Succeeded! Result: " + currentTime + "\n");


        } catch (SQLException e) {
            logger.log("Caught Exception: " + e.getMessage() + "\n");
        }


        return currentTime;
    }
}
