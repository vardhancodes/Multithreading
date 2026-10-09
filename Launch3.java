import java.util.Scanner;
class Telusko implements Runnable{
    
    public void run()
    {
        String name = Thread.currentThread().getName();

        if(name.equals("REG"))
        {
            registration();
        }
        else if(name.equals("COURSE"))
        {
            courseInfo();
        }
        else
        {
            printingStar();
        }

        
    }

    
    public void registration()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your id");
        int id = sc.nextInt();

        System.out.println("Enter your age");
        int age = sc.nextInt();
        System.out.println("id: " + id +" age: "+age);
    }


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

    public void printingStar()
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


}

public class Launch3{
    public static void main(String[] args) {
        System.out.println("Main thread started");
        Telusko t = new Telusko();

        Thread t1 = new Thread(t);
        Thread t2 = new Thread(t);
        Thread t3 = new Thread(t);

        t1.setName("REG");
        t2.setName("COURSE");
        t3.setName("STAR");

        t1.start();
        t2.start();
        t3.start();

    }
}