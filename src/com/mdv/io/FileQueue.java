package com.mdv.io;

import com.mdv.data.Message;
import com.mdv.io.Queue;
import com.mdv.throttle.Configuration;

import java.io.*;

public class FileQueue implements Queue {

    @Override
    public synchronized void popMessage() throws IOException{

        RandomAccessFile raf = new RandomAccessFile(Configuration.IO_FILE, "rw");
        //Initial write position
        long writePosition = raf.getFilePointer();
        raf.readLine();
        // Shift the next lines upwards.
        long readPosition = raf.getFilePointer();

        byte[] buff = new byte[1024];
        int n;
        while (-1 != (n = raf.read(buff))) {
            raf.seek(writePosition);
            raf.write(buff, 0, n);
            readPosition += n;
            writePosition += n;
            raf.seek(readPosition);
        }
        raf.setLength(writePosition);
        raf.close();
    }

    @Override
    public synchronized void getMessage() throws IOException{

        RandomAccessFile randomAccessFile = new RandomAccessFile(Configuration.IO_FILE, "rw");

        byte b;
        long length = randomAccessFile.length() ;
        if (length != 0) {
            do {
                length -= 1;
                randomAccessFile.seek(length);
                b = randomAccessFile.readByte();
            } while (b != 10 && length > 0);
            randomAccessFile.setLength(length);

            randomAccessFile.close();
        }

    }

    @Override
    public synchronized void pushMessage(String message) throws IOException {

        File f = new File(Configuration.IO_FILE);
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(f,true)), false);
        out.append(message);
        out.flush();
        out.close();
    }
    public synchronized void clean() throws IOException{
        PrintWriter writer = new PrintWriter(Configuration.IO_FILE);
        writer.print("");
        writer.close();
    }

}
