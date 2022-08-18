package be.huygebaert.freebay.accessDb;

import android.os.AsyncTask;

public class config extends AsyncTask<Void,Void,String> {
    // Modifier à un seul endroit les paramètres qui vous sont propres
    private final String ipServer = "10.0.2.2";
    private final String repertory = "android";
    private final String port = "";
    protected String signUp ="sign_up.php";
    protected String get_all_users = "get_users.php";
    protected String get_all_items = "get_items.php";
    protected String add_item = "add_item.php";
    protected String add_item_to_list = "add_item_to_list.php";

    public config(){}

    protected String getBasedURL(){
        if(!port.equals("")){
            return "http://"+this.ipServer+":"+this.port+"/"+this.repertory+"/";
        }
        return "http://"+this.ipServer+"/"+this.repertory+"/";
    }

    @Override
    protected String doInBackground(Void... voids) {
        return null;
    }
}
