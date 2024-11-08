import java.sql.*;

public class changePassword {

    public boolean change(int type, String Username, String NewPassword){
        Connection myConn = null;
        CallableStatement myStmt = null;

        try {
            //myConn = DriverManager.getConnection();    //<<<NEED TO FIGURE OUT WHAT IM CONNECTING TO
            if (type == 1) { //change login password
                myStmt = myConn.prepareCall("{call change_login_password(?, ?)}");
            }else if(type == 2){
                myStmt = myConn.prepareCall("{call change_stored_password(?, ?)}");
            }
            myStmt.setString(1, Username);
            myStmt.setString(2, NewPassword);
            myStmt.execute();

            return true;
        }
        catch(SQLException e){
            System.out.println("Problem occurred while trying to change password");
            return false;
        }
        finally{
            try{
                myConn.close();
                myStmt.close();
            }
            catch(SQLException e){
                System.out.println("Problem occurred while trying to close the connection in password changer");
            }
        }
    }

}
