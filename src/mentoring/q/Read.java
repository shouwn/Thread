package mentoring.q;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Read {

    public static void main(String[] args){
        System.out.println(question1("1 2 3 4 5 6 7 8 9"));
    }

    public static List<Integer> question1(String s){
        // ans1
        List<Integer> list1 = Arrays.stream(s.split(" "))
                .map(Integer::valueOf)
                .collect(Collectors.toList());

        // ans2
        List<Integer> list2 = new ArrayList<>();

        for(String tmp : s.split(" "))
            list2.add(Integer.valueOf(tmp));

        // ans3
        List<Integer> list3 = new ArrayList<>();

        String[] strings = s.split(" ");

        for(int i = 0; i < strings.length; i++)
            list3.add(Integer.valueOf(strings[i]));

        if(!list1.equals(list2))
            throw new IllegalStateException("list1와 list2 불일치");

        if(!list2.equals(list3))
            throw new IllegalStateException("list2와 list3 불일치");

        if(!list2.equals(list1))
            throw new IllegalStateException("list1와 list3 불일치");

        System.out.println("모두 일치");

        return list1;
    }
}
