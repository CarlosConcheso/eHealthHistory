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

            ipfs = new IPFS("/ip4/192.168.1.150/tcp/5001");
            ipfs.refs.local();
        }
        catch (IOException e)
        {
            System.out.println("Excepcion: " + e);
        }
    }
}
