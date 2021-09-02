package ssafy;

import org.apache.hadoop.util.ProgramDriver;

public class Driver {
	public static void main(String[] args) {
		int exitCode = -1;
		ProgramDriver pgd = new ProgramDriver();
		try {
			//new src = new pgd.addclass
			pgd.addClass("wordcount", Wordcount.class, "A map/reduce program that performs word counting.");
			pgd.addClass("wordcount1char", Wordcount1char.class, "A map/reduce program that performs word first char.");
			pgd.addClass("wordcountsort", Wordcountsort.class, "A map/reduce program that performs word sort counting.");
			pgd.addClass("inverted", InvertedIndex.class, "A map/reduce program that performs word index.");
			pgd.addClass("matrixadd", MatrixAdd.class, "A map/reduce program that performs matrix add.");

      			pgd.driver(args);
			exitCode = 0;
		}
		catch(Throwable e) {
			e.printStackTrace();
		}

		System.exit(exitCode);
	}
}
