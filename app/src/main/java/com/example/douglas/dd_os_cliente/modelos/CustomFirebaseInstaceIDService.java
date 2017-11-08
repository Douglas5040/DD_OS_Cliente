package com.example.douglas.dd_os_cliente.modelos;

import com.example.douglas.dd_os_cliente.controler.UserClienteCtrl;
import com.example.douglas.dd_os_cliente.helper.SQLiteHandler;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Douglas on 08/11/2017.
 */

public class CustomFirebaseInstaceIDService extends FirebaseInstanceIdService {

    private SQLiteHandler db;
    @Override
    public void onTokenRefresh() {
        db = new SQLiteHandler(this);
        String token = FirebaseInstanceId.getInstance().getToken();

        UserClienteCtrl user = db.getUserDetails();
        user.setToken(token);
        db.deleteUsers();
        db.addUser(user);
        SPUtil.saveStatusTokenServer( this, false );


    }
}
