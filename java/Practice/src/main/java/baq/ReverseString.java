package baq;

import java.util.ArrayList;

public class ReverseString {

	public static String reverseRecursive(String s) {
		if (s.length() <= 1) {
			return s;
		}
		
		return s.substring(s.length()-1, s.length()) + reverseRecursive(s.substring(0, s.length()-1));
	}
	
	public static String reverseNonRecursive(String s) {
		char[] array = s.toCharArray();
		reverseCharacters(array, 0, s.length());
		return new String(array);
	}
	
	private static void reverseCharacters(char[] array, int start, int end) {
		int i = start;
		int j = end-1;
		while (i < j) {
			char tmp = array[i];
			array[i] = array[j];
			array[j] = tmp;
			i++;
			j--;
		}

	}
	
	public static String dumbReverseSentence(String s) {
		long startTime = System.nanoTime();
		char[] a = s.toCharArray();
		ArrayList<String> words = new ArrayList<String>();
		String currentString = "";
		for (int i = 0; i < a.length; i++) {
			if (a[i] == ' ') {
				words.add(currentString);
				currentString = "";
			} else {
				currentString += a[i];
			}
		}
		words.add(currentString);
		
		String reverseSentence = "";
		for (int i = words.size() - 1; i > -1; i--) {
			reverseSentence += " " + words.get(i);
		}
		System.out.println(System.nanoTime()-startTime);
		return reverseSentence;
	}
	
	public static String smartReverseSentence(String s) {
		long startTime = System.nanoTime();
		char[] reversedCharacters = s.toCharArray();
		reverseCharacters(reversedCharacters, 0, s.length());
		
		int startIndex = 0;
		int i = 0;
		for (; i < s.length(); i++) {
			if (reversedCharacters[i] == ' ') {
				reverseCharacters(reversedCharacters, startIndex, i);
				startIndex = i + 1;
			}
		}
		reverseCharacters(reversedCharacters, startIndex, i);
		System.out.println(System.nanoTime()-startTime);
		return new String(reversedCharacters);
	}
	
	public static void main(String[] args) {
		String hello = "hello";
		System.out.println(hello.substring(hello.length()-1, hello.length()));
		System.out.println("Recursive: " + reverseRecursive(hello));
		System.out.println("Nonrecursive: " + reverseNonRecursive(hello));
		System.out.println("Dumb Reverse sentence: " + dumbReverseSentence("hello world"));
		System.out.println("Smart Reverse sentence: " + smartReverseSentence("hello world"));		
	}
}
