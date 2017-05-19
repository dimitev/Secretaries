package k3;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;

public class K3
{
    public static void main(String[] args) throws IOException
    {
        Document d = new Document("nameX",2+Math.random()*4,100+Math.random()*600,Math.random()>0.5);
        Socket s=null;
        do
        {
            try{
                s=new Socket("localhost",6060);
            }
            catch (ConnectException a)
            {
                System.out.println("Waiting server");
                s=null;
            }
        }while(s==null);
        ObjectOutputStream send = new ObjectOutputStream(s.getOutputStream());
        send.writeObject(d);
        System.out.println("Succes");
        s.close();
        send.close();
    }

}
