package cloud.filter;

import cloud.existenceTrie.TST;

import java.io.FileWriter;

public class Filter {
	
	private TST tst;
	
	public Filter() {
		this.tst = new TST();
		load();
	}
	
	public boolean isSentenceSafe(String sentence) {
		String[] words = sentence.split(" ");
		
		for (String word : words) {
			String current = word + "$";
			if(tst.exists(current)) {
				return false;
			}
		}
		
		return true;
	}
	
	private void load() {
		String[] strings = BadWords.bad_words.split(",");
		
		for(String s : strings) {
			String current = s;
			current = current.replace("\n", "").replace("\r", "");
			current += cloud.existenceTrie.TST.END;
			tst.insert(current);
			
		}
	}

	public static void pln(){System.out.println();}
	public static void pln(String s){System.out.println(s);}
	public static void pln(int s){System.out.println(s);}
	public static void pln(Object o){System.out.println(o);}

	public static void main(String args[]) {
		Filter filter = new Filter();
		String s1 = "emir is an asshole sometimes";
		pln(filter.isSentenceSafe(s1));
	}
	
	

}
