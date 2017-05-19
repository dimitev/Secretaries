package k3secretary;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.Socket;
import k3.Document;
public class K3Secretary {

    public static void main(String[] args) throws IOException, ClassNotFoundException
    {
        Document s,n;
        char r='0';
        Socket so=null;
        do{
        try{
            so= new Socket("localhost",6061);
        }
        catch(ConnectException a)
        {
            System.out.println("wainting request");
        }
        }while(so==null);
        ObjectInputStream in=new ObjectInputStream(so.getInputStream());
        PrintStream out=new PrintStream(so.getOutputStream());
        s=(Document)in.readObject();
        n=(Document)in.readObject();
        if(s.getIncome()<300&&s.isSpecial()&&s.getScore()>5.3)
        {
            if(n==null)r='n';
            else if(n.getSpecial()<5-n.getSucces())
                {
                    r='l';
                }
                else r='0';
        }
        if(s.getIncome()<500&& !s.isSpecial()&&s.getScore()>5.5)
        {
            if(n==null)
            {
                r='N';
            }
            else
            {
                if(n.getSucces()==0)
                {
                    r='s';
                }
                else r='0';
            }
        }
        System.out.println("Job done");
        out.print(r);
        in.close();
        out.close();
        so.close();
    }

}
