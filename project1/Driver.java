
import org.apache.hadoop.util.ProgramDriver;

public class Driver {
	public static void main(String[] args) {
		int exitCode = -1;
		ProgramDriver pgd = new ProgramDriver();
		try {

			pgd.addClass("wordcount", Wordcount.class, "word counting.");
			pgd.addClass("wordcount1char", Wordcount1char.class, "word char");
			pgd.addClass("wordcountsort", Wordcountsort.class, "word sort");
			pgd.addClass("inverted", InvertedIndex.class, "word idx");
			pgd.addClass("matrixadd", MatrixAdd.class, "matrix add.");
			pgd.driver(args);
			exitCode = 0;
		} catch (Throwable e) {
			e.printStackTrace();
		}

		System.exit(exitCode);
	}
}
