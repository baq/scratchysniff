import java.util.List;
import java.util.ArrayList;

public class OptimalCoins {
    
    static final int[] COIN_SET = {1, 7, 10};

    public static List<Integer> getCoins(int sum) {
	sum += 1;
	int[] coins = new int[sum];
	int[] min_num_coins = new int[sum];
	coins[0] = 0;
	min_num_coins[0] = 0;	
	for (int i = 1; i < min_num_coins.length; i++) {
	    min_num_coins[i] = Integer.MAX_VALUE;
	}
	for (int sum_i = 1; sum_i < sum; sum_i++) {
	    for (int j = 0; j < COIN_SET.length; j++) {
		int coin_j = COIN_SET[j];
		if (sum_i >= coin_j && min_num_coins[sum_i-coin_j]+1 < min_num_coins[sum_i]) {
		    min_num_coins[sum_i] = min_num_coins[sum_i-coin_j]+1;
		    coins[sum_i] = coin_j;
		}
	    }
	    //System.out.println(sum_i + ": " + coins[sum_i]);
	}

	sum -= 1;
	List<Integer> coinList = new ArrayList<Integer>();
	int currCoinValue = coins[coins.length - 1];
	coinList.add(currCoinValue);
	int remainingSum = sum - currCoinValue;
	while (remainingSum > 0) {
	    currCoinValue = coins[remainingSum];
	    coinList.add(currCoinValue);
	    remainingSum = remainingSum - currCoinValue;
	} 
	
	return coinList;
    }
    
    public static void main(String[] args) {
	System.out.println("Change for 35 is: " + getCoins(35));
	System.out.println("Change for 7 is: " + getCoins(7));
	System.out.println("Change for 32 is: " + getCoins(32));
	System.out.println("Change for 25 is: " + getCoins(25));
	System.out.println("Change for 0 is: " + getCoins(0));
	System.out.println("Change for 1 is: " + getCoins(1));
	System.out.println("Change for 4 is: " + getCoins(4));
    }
    
}