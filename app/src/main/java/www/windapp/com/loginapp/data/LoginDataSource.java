package www.windapp.com.loginapp.data;

import android.util.Log;

import www.windapp.com.loginapp.data.model.LoggedInUser;
import www.windapp.com.loginapp.ui.login.LoginActivity;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    LoggedInUser currentUser;

    public Result<LoggedInUser> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication
      //      Log.i("username:",username+password);


            Result<LoggedInUser> result;

             currentUser= new LoggedInUser(java.util.UUID.randomUUID().toString(),
                            "invalid_user");


     String loginUserPass=       LoginActivity.getSharedpreferences().getString(username,"invalid");



             if(loginUserPass.equals(password)){

                currentUser= new LoggedInUser(java.util.UUID.randomUUID().toString(),
                        username);

            }
else {
    throw new Exception();
             }





            return new Result.Success<>(currentUser);
        } catch (Exception e) {

            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
        currentUser= new LoggedInUser(java.util.UUID.randomUUID().toString(),
                "invalid_user");
    }
}
