/**
 * RAHJ PingPong v.1 main class
 *
 * September 26, 2020
 * by Reynaldo A. Hipolito
 */
import com.rahj.Window;

public class Main
{
    public static void main(String[] args)
    {
        Window window = new Window();
        Thread t1 = new Thread(window);
        t1.start();
    }

}
