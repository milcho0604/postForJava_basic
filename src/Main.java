import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n= Integer.parseInt(br.readLine());
        String str = String.format(String.valueOf(n));
        String arr[] = new String[str.length()];

        for(int i=0; i<str.length(); i++){
            arr[i] = String.valueOf(str.charAt(i));
        }
        Arrays.sort(arr, Collections.reverseOrder());
        for(int i=0; i<arr.length; i++){
            System.out.print(arr[i]);
        }


    }
}