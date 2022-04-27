package com.example.ehealthhistory.IPFS;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;

public class IPFSService implements FileServiceImp {

    private StringBuilder textToSave;
    IPFS ipfs;

    public IPFSService()
    {
        IPFSConfig ipfsConfig = new IPFSConfig();
        ipfs = ipfsConfig.ipfs;
    }

    @Override
    public String saveText(String namefile) {

        byte[] byteArray = String.valueOf(textToSave).getBytes();

        try {
            NamedStreamable.ByteArrayWrapper file = new NamedStreamable.ByteArrayWrapper(namefile, byteArray);
            MerkleNode addResult = ipfs.add(file).get(0);

            return addResult.hash.toBase58();

        } catch (Exception e){
            throw new RuntimeException("Error while saving text on IPFS Node: " + e);
        }
    }

    public StringBuilder getTextToSave() {
        return textToSave;
    }

    public void setTextToSave(StringBuilder textToSave) {
        this.textToSave = textToSave;
    }

    public void addToLog(String text)
    {
        textToSave.append(text).append("\n");
    }
}
