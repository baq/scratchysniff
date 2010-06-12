package baq;

public class Math {
	
	private static double error = 1.0d/256.0d;
	
	private static boolean withinError(double x, double y) {
		if (y <= (x + error) && y >= (x - error)) {
			return true;
		}
		return false;
	}
	
	public static double sqrt(double x) {
		if (x == 0 || x == 1) {
			return x;
		}
		return findSqrt(x, 0, x);
	}
	
	public static int power(int x, int y) {
		int power = 1;
		for (int i=0; i<y; i++) {
			power *= x;
		}
		
		return power;
	}

	private static double findSqrt(double x, double lowerBound, double upperBound) {
		double attemptedSqrt = lowerBound + ((upperBound - lowerBound)/2);
		double square = attemptedSqrt * attemptedSqrt;
		
		if (withinError(x, square)) {
			return attemptedSqrt;
		} else if (square > x + error) {
			return findSqrt(x, lowerBound, attemptedSqrt);
		} else {
			return findSqrt(x, attemptedSqrt, upperBound);
		}
	}
	
	public static void main(String[] args) {
		double sqrt = sqrt(64.0);
		System.out.println(sqrt);
		
		System.out.println(power(2, 3));
	}
}
