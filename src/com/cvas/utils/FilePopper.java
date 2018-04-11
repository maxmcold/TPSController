package com.cvas.utils;

import com.cvas.Configuration;

import java.io.*;

public class FilePopper {


    public void appendline(String line,String file) throws IOException {


        Writer output = new BufferedWriter(new FileWriter(file ,true));
        output.append(line);
        output.close();
    }

    public void popLine(String line, String file) throws IOException {

        RandomAccessFile raf = new RandomAccessFile(file, "rw");

        long writePosition = raf.getFilePointer();
        raf.readLine();

        long readPosition = raf.getFilePointer();

        //raf.seek(writePosition);

        byte[] buff = new byte[8];
        int n;
        while (-1 != (n = raf.read(buff))) {
            raf.seek(writePosition);
            raf.write(buff, 0, n);
            readPosition += n;
            writePosition += n;
            raf.seek(readPosition);
        }//*/
        raf.seek(writePosition);
        raf.write(line.getBytes());
        writePosition += line.length();
        raf.setLength(writePosition);
        raf.close();

    }

}
