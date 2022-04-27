package com.example.ehealthhistory.IPFS;

import com.google.common.net.HttpHeaders;

public class IPFSController {

    IPFSService ipfsService;

    public IPFSController() {
        this.ipfsService = new IPFSService();
    }

    public String saveText(String namefile) {
        return ipfsService.saveText(namefile);
    }

    public void addToLog(String text)
    {
        ipfsService.addToLog(text);
    }
}
