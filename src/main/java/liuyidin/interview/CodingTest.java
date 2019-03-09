package liuyidin.interview;

import java.util.*;

public class CodingTest {
    /*
    Hi Amy,
    This class implements the coding test we discussed this noon.
    It will automatically generate 5 test cases and the time slots for each person is 10.
    If you would like to test differently, you can simply change the numbers below.
     */

    public static final int numberOfTestCase = 5;
    public static final int availableTimeLength = 10;
    public static final int numberOfPeople = 5;

    public static void main(String[] args) {
        for(int i = 0; i<numberOfTestCase ; i++){
            List<List<TimeSlot>> input = testDataGenerator();
            List<TimeSlot> commonTime = get3CommonTime(input);
            dataPrinter(input, commonTime);
        }
    }

    /**
     * Print the formatted input data and output data
     * @param timeSlots input data
     * @param commonTime output data
     */
    public static void dataPrinter(List<List<TimeSlot>> timeSlots,List<TimeSlot> commonTime){
        System.out.println("--------------------------INPUT--------------------------");
        for(List<TimeSlot> ts_list:timeSlots){
            for(TimeSlot ts:ts_list){
                System.out.printf("%d\t", ts.time);
            }
            System.out.println();
        }
        System.out.println("--------------------------OUTPUT-------------------------");
        for(TimeSlot ts:commonTime){
            System.out.printf("%d\t", ts.time);
        }
        System.out.print("\n\n\n");
    }

    /**
     * Automatically generate the test data
     * @return A list of list which contains the available time info for each person
     */
    public static List<List<TimeSlot>> testDataGenerator() {
        Random rand = new Random();

        int actualNumberOfPeople = rand.nextInt(numberOfPeople)+1;
        List<List<TimeSlot>> input = new ArrayList<>();
        for (int i = 0; i < actualNumberOfPeople; i++){
            List<TimeSlot> person = new ArrayList<>();
            while(person.size()<availableTimeLength){
                TimeSlot time = new TimeSlot(rand.nextInt(15));
                if(person.contains(time)) continue;
                person.add(time);
            }
            Collections.sort(person);
            input.add(person);
        }
        return input;
    }

    /**
     * Get the earliest 3 common time for all the people. If the number of common time is less than 3, it will return
     * the current available common time. e.g If we only have 2, it will return 2 common time.
     * @param timeSlots The available time for each person
     * @return A list of common time
     */
    public static List<TimeSlot> get3CommonTime(List<List<TimeSlot>> timeSlots){
        List<TimeSlot> common3Time = new ArrayList<>();
        //Use the HashMap to track the frequency of each time.
        HashMap<Integer, Integer> common_time = new HashMap<>();
        int numberOfPeople = timeSlots.size();

        //Add time to the HashMap
        for(List<TimeSlot> list_ts:timeSlots){
            for(TimeSlot ts:list_ts) {
                if (common_time.containsKey(ts.time)) {
                    common_time.put(ts.time, common_time.get(ts.time) + 1);
                } else {
                    common_time.put(ts.time, 1);
                }
            }
        }

        //Get the 3 earliest time from the HashMap
        for(TimeSlot ts: timeSlots.get(numberOfPeople-1)){
            if(common_time.containsKey(ts.time) && common_time.get(ts.time) == numberOfPeople){
                common3Time.add(ts);
            }

            //If we've already found 3 common time, then return.
            if(common3Time.size() == 3) return common3Time;
        }

        //If less than 3, it will return the current list
        return common3Time;
    }

    static class TimeSlot implements Comparable<TimeSlot>{
        int time;
        TimeSlot(int time) {
            this.time = time;
        }

        @Override
        public int compareTo(TimeSlot other) {
            if(this.time>other.time) return 1;
            else if(this.time==other.time) return 0;
            else return -1;
        }

        @Override
        public boolean equals(Object object)
        {
            TimeSlot other = (TimeSlot)object;
            if(other.time == this.time) return true;
            else return false;
        }
    }
}
