import java.util.LinkedList;
import java.util.List;

public class FlowNetwork {

    public static void main(String[] args) {

        int numOfNodes = (int)((Math.random() * 7) + 4);
        int[] arr = new int[numOfNodes];
        System.out.println(numOfNodes);

        for(int i = 0; i<arr.length; i++){
            arr[i] = (int)((Math.random() * 16) + 5);
            System.out.println(arr[i]);
        }


    }
}
