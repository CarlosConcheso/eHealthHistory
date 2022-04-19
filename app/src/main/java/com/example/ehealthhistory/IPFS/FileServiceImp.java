package com.example.ehealthhistory.IPFS;

public interface FileServiceImp {

    String saveText(String nameFile);

    byte[] loadFile(String hash);
}
