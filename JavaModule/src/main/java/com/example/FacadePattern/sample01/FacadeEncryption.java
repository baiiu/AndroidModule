package com.example.FacadePattern.sample01;

/**
 * author: baiiu
 * date: on 16/7/22 10:28
 * description:
 */
public class FacadeEncryption {

    private FileReader fileReader;
    private Encryption encryption;
    private FileWriter fileWriter;

    public FacadeEncryption() {
        this.fileReader = new FileReader();
        this.encryption = new Encryption();
        this.fileWriter = new FileWriter();
    }

    public void encrypt(String sourceFilePath, String desFilePath) {
        String s = fileReader.readFile(sourceFilePath);
        String encrypt = encryption.encrypt(s);
        fileWriter.write(encrypt,desFilePath);
    }

}
