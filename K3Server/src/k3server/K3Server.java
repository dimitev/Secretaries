package k3server;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import k3.Document;
public class K3Server
{
    public static LinkedList<Document> inList= new LinkedList<>();
    public static HashMap<Integer,Document> accepted= new HashMap<Integer,Document>();
    public static LinkedList<Document> refused= new LinkedList<>();
    public static void main(String[] args)throws IOException, ClassNotFoundException
    {
        SecretaryThread st1=new SecretaryThread("secretary1");
        SecretaryThread st2=new SecretaryThread("secretary2");
        st1.start();
        st2.start();
        ClientThread t;
        ServerSocket so=new ServerSocket(6060);
        while(true)
        {
            System.out.println("Wainting for clients");
            Socket se= so.accept();
            System.out.println("New client, docs- "+inList.size());
            t=new ClientThread("Client",se);
            t.start();
        }
    }
    public static class ClientThread extends Thread
    {
        private Socket se;
        public ClientThread(String name,Socket se)
        {
            super(name);
            this.se=se;
        }
        public void run()
        {
            try {
                Document s;
                ObjectInputStream recieve=new ObjectInputStream(se.getInputStream());
                s=(Document)recieve.readObject();
                inList.add(s);
                recieve.close();
                se.close();
            } catch (IOException | ClassNotFoundException ex) {
               System.out.println("Client error");
            }
        }
    }
    public static class SecretaryThread extends Thread
    {
        public SecretaryThread(String name)
        {
            super(name);
        }
        @Override
        public void run()
        {
            char c;
            while(true)
            {
                try {
                    ObjectOutputStream os=null;
                    ServerSocket ss=null;
                    Socket se=null;
                    Document s;
                    Document n;
                    System.out.println("no docs");
                    while(true)
                    {
                        System.out.println(inList.size()+" Documents");
                        if(inList.size()>0)break;
                    }
                    
                    s=inList.pop();
                    System.out.println("working");
                    do{
                        try {
                            ss=new ServerSocket(6061);
                            se= ss.accept();
                            os= new ObjectOutputStream(se.getOutputStream());
                        } catch (IOException ex) {
                            System.out.println("cannot connect");
                        }
                    }while(os==null);
                    Scanner in=new Scanner(se.getInputStream());
                    n=accepted.get(s.hashCode());
                    os.writeObject(s);
                    os.writeObject(n);
                    c=(char) in.nextByte();
                    if(c=='0')
                    {
                        refused.add(s);
                        System.out.println("doc refused");
                    }
                    if(c=='l')
                    {
                        n.incSpecial();
                        System.out.println("inc spec");
                    }
                    if(c=='s')
                    {
                        n.incSuccess();
                        System.out.println("inc success");
                    }
                    if(c=='n')
                    {
                        s.incSpecial();
                        accepted.put(s.hashCode(),s);
                        System.out.println("add spec");
                    }
                    if(c=='N')
                    {
                        s.incSuccess();
                        accepted.put(s.hashCode(),s);
                        System.out.println("add success");
                    }
                    os.close();
                    ss.close();
                    se.close();
                } catch (IOException ex) {
                    System.out.println("cannot send document");
                }
            }
        }
    }
}
