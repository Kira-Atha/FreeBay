package be.huygebaert.freebay.accessDb;

import android.os.AsyncTask;

public class config extends AsyncTask<Void,Void,String> {
    private final String ipServer;
    private final String repertory;
    private final String port;

    public config(String ipServer, String port, String repertory){
        this.ipServer = ipServer;
        this.port = port;
        this.repertory = repertory;
    }

    public String getBasedURL(){
        if(!port.equals("")){
            return "http://"+this.ipServer+":"+this.port+"/"+this.repertory+"/";
        }
        return "http://"+this.ipServer+"/"+this.repertory+"/";
    }

    @Override
    protected String doInBackground(Void... voids) {
        System.out.println("here");
        return null;
    }
}
