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

            String dns = "bdiez.duckdns.org";
            //String ip = "192.168.1.150";

            String conexionDNS = "/dnsaddr/"+ dns +"/tcp/5001";
            //String conexionip = "/ip4/"+ ip +"/tcp/5001";


            ipfs = new IPFS(conexionDNS);
            ipfs.refs.local();
        }
        catch (IOException e)
        {
            System.out.println("Excepcion: " + e);
        }
    }
}
