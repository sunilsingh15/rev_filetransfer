import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) throws UnknownHostException, IOException {
        Socket socket = new Socket(args[0], Integer.parseInt(args[1]));
        System.out.println("Connection established");

        File file = new File(args[2]);
        String fName = file.getName();
        long fileSize = file.length();

        System.out.println("File size: " + fileSize);

        FileInputStream fis = new FileInputStream(file);
        BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
        DataOutputStream dos = new DataOutputStream(bos);

        dos.writeUTF(fName);
        dos.writeLong(fileSize);

        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = fis.read(buffer)) != -1) {
            bos.write(buffer, 0, bytesRead);
        }
        bos.flush();
        System.out.println(fName + " has been successfully sent to the server");

        fis.close();
        socket.close();
    }
}
