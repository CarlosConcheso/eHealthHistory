package com.example.ehealthhistory.IPFS;

import io.ipfs.api.IPFS;

public class IPFSConfig {

    IPFS ipfs;

    public IPFSConfig() {
        ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001");
    }
}
