import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws Exception {
        
        ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));
        System.out.println("Listening on port " + args[0]);

        Socket socket = serverSocket.accept();
        System.out.println("Connection established");

        InputStream is = socket.getInputStream();
        DataInputStream dis = new DataInputStream(is);

        String fileN = dis.readUTF();
        long fileS = dis.readLong();

        FileOutputStream fos = new FileOutputStream("received" + File.separator + fileN);
        BufferedOutputStream bos = new BufferedOutputStream(fos);

        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = dis.read(buffer)) != -1) {
            bos.write(buffer, 0, bytesRead);
        }
        bos.close();
        System.out.println(fileN + " has been successfully received");

        serverSocket.close();
    }
}
