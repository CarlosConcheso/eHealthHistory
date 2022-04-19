package com.example.ehealthhistory.IPFS;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;

public class IPFSService implements FileServiceImp {

    private IPFSConfig ipfsConfig;
    private StringBuilder textToSave;
    IPFS ipfs;

    public IPFSService()
    {
        ipfsConfig = new IPFSConfig();
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

    @Override
    public byte[] loadFile(String hash) {

        try {
            Multihash filePointer = Multihash.fromBase58(hash);
            return ipfs.cat(filePointer);

        } catch (Exception e) {
            throw new RuntimeException("Error while loading file on IPFS: " + e);
        }
    }

    public void addToLog(String text)
    {
        textToSave.append(text).append("\n");
    }
}
