package be.huygebaert.freebay.DAO;

import android.os.AsyncTask;

public class DAO extends AsyncTask<Void,Void,String> {
    private String ipServer;
    private String port;
    private String repertory;

    public DAO(String ipServer,String port,String repertory){
        this.ipServer = ipServer;
        this.port = port;
        this.repertory = repertory;

    }

    public String getBasedURL(){
        return "http://"+this.ipServer+":"+this.port+"/"+this.repertory+"/";
    }

    @Override
    protected String doInBackground(Void... voids) {
        return null;
    }
}
