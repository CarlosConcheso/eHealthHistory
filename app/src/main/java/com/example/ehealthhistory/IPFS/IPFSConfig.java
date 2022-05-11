package com.example.ehealthhistory.IPFS;

import android.os.StrictMode;

import java.io.IOException;

import io.ipfs.api.IPFS;

public class IPFSConfig {

    IPFS ipfs;

    public IPFSConfig() {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            String direccion = "/bdiez.duckdns.org:5001/webui";

            ipfs = new IPFS(direccion);
            ipfs.refs.local();
        }
        catch (IOException e)
        {
            System.out.println("Excepcion: " + e);
        }
    }
}
