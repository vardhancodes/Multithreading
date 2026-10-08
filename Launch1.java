import java.util.Scanner;
class Alpha extends Thread{

    Scanner sc = new Scanner(System.in);
    public void registration()
    {
       System.out.println("Enter your id");
       int id = sc.nextInt();

       System.out.println("Enter your age");
       int age = sc.nextInt();
       System.out.println("id: " + id +" age: "+age);
    }

    @Override
    public void run()
    {
        registration();
    }

    
}
class Beta extends Thread
{
    public void courseInfo()
    {
        for(int i = 0 ; i < 5 ; i++)
        {
            System.out.println("Visit telusko.com for more courses");
            try{
                Thread.sleep(2000);
            }
            catch (InterruptedException e){ 
                e.printStackTrace();

            }
        }
    }
     @Override 
        public void run()
        {
            courseInfo();
        }
}
class Gamma extends Thread{
    public void printingStasr()
    {
        for(int i = 0 ; i < 5 ; i++)
        {
            System.out.println('*');
            try{
                Thread.sleep(2000);
            }
            catch (InterruptedException e){ 
                e.printStackTrace();

            }
        }
    }
     @Override 
        public void run()
        {
            printingStasr();
        }
}
public class Launch1 {
    public static void main(String[] args) throws InterruptedException{
        // System.out.println("thread started its work");

        // Thread.sleep(6000);
        // Thread t1 = Thread.currentThread();

        // System.out.println(t1.getName());
        // System.out.println(t1.getPriority());
        // t1.setName("Telusko");
        // t1.setPriority(9);
        // System.out.println(t1.getPriority());
        // System.out.println(t1.getName());

        // System.out.println("Thread completed its work");

        Alpha a = new Alpha();
        Beta b = new Beta();
        Gamma g = new Gamma();
        a.start();
        b.start();
        g.start(); 
        a.registration();
        b.courseInfo();
        g.printingStasr();


    }
}
