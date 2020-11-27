import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
public class Driver{
    public static void main(String args[]) throws FileNotFoundException {
        int numTestCases;
        //File file = new File("test51.in");
        Scanner sc = new Scanner(System.in);
        numTestCases = sc.nextInt();
        while(numTestCases-->0){
            int size;
            size = sc.nextInt();
            A2DynamicMem obj = new A2DynamicMem(size, 2);
            int numCommands = sc.nextInt();
            while(numCommands-->0) {
                String command;
                command = sc.next();
                //System.out.println(command);
                int argument;
                argument = sc.nextInt();
                int result = -5;
                boolean toPrint = true;
                switch (command) {
                    case "Allocate":
                        result = obj.Allocate(argument);
                        break;
                    case "Free":
                        result = obj.Free(argument);
                        break;
                    case "Defragment":
                        obj.Defragment();
                        toPrint = false;
                        break;
                    default:
                        break;
                }
                if(toPrint)
                    System.out.println(result);
            }
            // System.out.println("Free Block");
            //     Dictionary temp = obj.freeBlk.getFirst();
            //     for(;temp != null; temp = temp.getNext()){
            //         int endaddress = temp.size + temp.address;
            //         System.out.println(temp.address + " -> " + endaddress);
            //     }
            //     temp = obj.allocBlk.getFirst();
            //     System.out.println("Allocate block");
            //     for(;temp != null; temp = temp.getNext()){
            //         int endaddress = temp.size + temp.address;
            //         System.out.println(temp.address + " -> " + endaddress);
            //     }
        }
    }
}