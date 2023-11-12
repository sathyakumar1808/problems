package com.sk.dicechallenge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class DiceChallenge {
    private static final ArrayList<Integer> DIE_A = new ArrayList<>(Arrays.asList(1,2,3,4,5,6));
    private static final ArrayList<Integer> DIE_B = new ArrayList<>(Arrays.asList(1,2,3,4,5,6));
    private static final int FACES = 6;
    private static HashMap<Integer,Integer> totalVsCount = new HashMap<>();
    /**
     * Storing default dies sum with times they occur in combination
     */
    DiceChallenge(){
        totalVsCount = calculateDiesSumCount(DIE_A,DIE_B);
    }
    /**
     * Calculate dice sum vs times they occur in combinations
     * @param diceA Contains spots count on each face of Dice A
     * @param diceB Contains spots count on each face of Dice B
     * @return dice combination sum vs times they can occur in combinations
     */
    private HashMap<Integer,Integer> calculateDiesSumCount(ArrayList<Integer> diceA, ArrayList<Integer> diceB){
        HashMap<Integer,Integer> result = new HashMap<>();
        for(Integer diceASpots : diceA){
            for(Integer diceBSpots : diceB){
                Integer spotsSum = diceASpots + diceBSpots;
                result.put(spotsSum, result.containsKey(spotsSum)?result.get(spotsSum)+1 : 1);
            }
        }
        return result;
    }
    /**
     * Display all probabilities of sum value of two dice when rolled
     * @param diceA Contains spots count on each face of Dice A
     * @param diceB Contains spots count on each face of Dice B
     * @param totalCount Contains sum value of spots count on both dice Vs their possibility in set of combinations
     */
    private void displaySumsProbability(ArrayList<Integer> diceA, ArrayList<Integer> diceB, HashMap<Integer,Integer> totalCount){
        System.out.println("Probability are displayed below\nSum\t|\tProbability percentage\n-----------------------------------------");
        for(Integer key : totalCount.keySet()){
            double probability = ((double)totalCount.get(key))* 100 / getTotalCombination(diceA,diceB);
            System.out.println(key + "\t|\t"+ String.format("%.2f",probability));
        }
    }
    /**
     * Probability of independent event will be multiple of individual probability
     * @param diceA Contains spots count on each face of Dice A
     * @param diceB Contains spots count on each face of Dice B
     * @return will return multiple of individual probability
     */
    public int getTotalCombination(ArrayList<Integer> diceA , ArrayList<Integer> diceB){
        return diceA.size() * diceB.size();
    }
    /**
     * Displays possibility combinations of Dice A and Dice B when rolled
     * @param diceA Contains spots count on each face of Dice A
     * @param diceB Contains spots count on each face of Dice B
     */
    public void displayCombination(ArrayList<Integer> diceA , ArrayList<Integer> diceB){
        for(Integer diceASpots : diceA){
            for(Integer diceBSpots : diceB){
                System.out.print("("+diceASpots +","+ diceBSpots+")\t");
            }
            System.out.println();
        }
    }
    /**
     * Calculating dice value as per loki condition and storing it in diceA and diceB
     * @param diceA Dice value after original value changed
     * @param diceB Dice value after original value changed
     */
    public void solution(ArrayList<Integer> diceA , ArrayList<Integer> diceB){
        //Checking whether spots in faces matches sum count 
        HashMap<Integer,Integer> newTotalVsCount = (HashMap<Integer,Integer>) totalVsCount.clone();
        for(Integer key : newTotalVsCount.keySet()){
            if(newTotalVsCount.get(key)!=0){
                //Also check previous sum count value also get affected
                for (Integer diceBSpots : diceB) {
                    for (Integer diceASpots : diceA) {
                        if (diceASpots + diceBSpots == key) {
                            newTotalVsCount.put(key, newTotalVsCount.get(key) - 1);
                        }
                    }
                }
                //if sum value differs, changing dice spots
                if(newTotalVsCount.get(key) != 0 && diceA.get(diceA.size()-1) + diceB.get(diceB.size()-1) < key){
                    if(diceA.size() < FACES){
                        diceA.add(diceA.get(diceA.size()-1)+1);
                        solution(diceA,diceB);
                    }
                    else{
                        diceB.add(key - diceA.get(diceA.size()-1));
                        solution(diceA,diceB);
                    }
                }
                else if(diceA.size() < FACES){
                    if(newTotalVsCount.get(key)<0){
                        diceA.remove(diceA.size()-1);
                        diceA.add(diceA.get(diceA.size()-1)+1);
                        solution(diceA,diceB);
                    }else if(newTotalVsCount.get(key)>0){
                        diceA.add(diceA.get(diceA.size()-1));
                        solution(diceA,diceB);
                    }
                }else if(diceA.size() == FACES && newTotalVsCount.get(key)<0){
                    Integer temp = diceB.get(diceB.size()-1);
                    diceB.remove(diceB.size()-1);
                    diceB.add(temp+1);
                    if(diceA.get(diceA.size()-1) <4){
                        diceA.remove(diceA.size()-1);
                        diceA.add(diceA.get(diceA.size()-1)+1);
                    }
                    solution(diceA,diceB);
                }
            }
        }
    }
    /**
     * Calculating new dice values
     */
    public void lokiGame(){
        ArrayList<Integer> diceA = new ArrayList<>(), diceB = new ArrayList<>();
        diceA.add(1);
        diceB.add(1);
        solution(diceA, diceB);
        System.out.println("New Value of Dice A : "+ diceA);
        System.out.println("New Value of Dice B : "+ diceB);

        System.out.println("Distribution of all possible combinations with new Dices: ");
        displayCombination(diceA,diceB);

        System.out.println("Probability with new Dices to confirm probaility remains unchanged : ");
        displaySumsProbability(diceA,diceB,calculateDiesSumCount(diceA,diceB));
    }
    public static void main(String[] args) {
        DiceChallenge diceChallenge = new DiceChallenge();
        System.out.println("1.\tTotal combinations are possible : "+ diceChallenge.getTotalCombination(DIE_A,DIE_B));
        System.out.println("2.\tDistribution of all possible combinations : ");
        diceChallenge.displayCombination(DIE_A,DIE_B);
        System.out.println("3.\tProbability of all Possible Sums : ");
        diceChallenge.displaySumsProbability(DIE_A,DIE_B,totalVsCount);

        System.out.println("\n\n\nResult of new dies after Loki dooms ");
        diceChallenge.lokiGame();
    }
}
