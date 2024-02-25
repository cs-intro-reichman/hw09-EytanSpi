import java.util.HashMap;
import java.util.Random;

public class LanguageModel {

    // The map of this model.
    // Maps windows to lists of charachter data objects.
    HashMap<String, List> CharDataMap;
    
    // The window length used in this model.
    int windowLength;
    
    // The random number generator used by this model. 
	private Random randomGenerator;

    /** Constructs a language model with the given window length and a given
     *  seed value. Generating texts from this model multiple times with the 
     *  same seed value will produce the same random texts. Good for debugging. */
    public LanguageModel(int windowLength, int seed) {
        this.windowLength = windowLength;
        randomGenerator = new Random(seed);
        CharDataMap = new HashMap<String, List>();
    }

    /** Constructs a language model with the given window length.
     * Generating texts from this model multiple times will produce
     * different random texts. Good for production. */
    public LanguageModel(int windowLength) {
        this.windowLength = windowLength;
        randomGenerator = new Random();
        CharDataMap = new HashMap<String, List>();
    }

    /** Builds a language model from the text in the given file (the corpus). */
	public void train(String fileName) {
        String window = "";
        char c;
        In in = new In(fileName);

        // build first window
        for (int i = 1; i <= windowLength; i++) { 
            if (!in.hasNextChar()) return;
            window += in.readChar();
        }

        while (!in.isEmpty()) {
            c = in.readChar();
            List probs = CharDataMap.get(window);
            if (probs == null) {
                probs = new List ();
                CharDataMap.put(window, probs);
            }
            probs.update(c);

            // move window fowards by 1 character
            window += c;
            window = window.substring(1, window.length());
        }
        // The entire file has been processed, and all the characters have been counted.
        // Proceeds to compute and set the p and cp fields of all the CharData objects
        // in each linked list in the map.

        for (String key : CharDataMap.keySet()) {
            List probs = CharDataMap.get(key);
            calculateProbabilities(probs);
        }
            
    }

    // Computes and sets the probabilities (p and cp fields) of all the
	// characters in the given list. */
	public void calculateProbabilities(List probs) {				
        int numOfLetters = 0;
        Node pointer = probs.first;
        while (pointer != null) {
            numOfLetters += pointer.cd.count;
            pointer = pointer.next;
        }
        pointer = probs.first;
        double comCP = 0;
        while (pointer != null) {
            CharData cd = pointer.cd;
            cd.p = (double)cd.count/numOfLetters;
            comCP += cd.p;
            cd.cp = comCP;
            pointer = pointer.next;
        }
    }

    // Returns a random character from the given probabilities list.
	public char getRandomChar(List probs) {
        double rand = Math.random();
        Node pointer = probs.first;
        while (pointer != null) {
            if (rand < pointer.cd.cp) return pointer.cd.chr;
            pointer = pointer.next;
        }
        return ' '; // will never reach this return
	}


    /**
	 * Generates a random text, based on the probabilities that were learned during training. 
	 * @param initialText - text to start with. If initialText's last substring of size numberOfLetters
	 * doesn't appear as a key in Map, we generate no text and return only the initial text. 
	 * @param numberOfLetters - the size of text to generate
	 * @return the generated text
	 */
	public String generate(String initialText, int textLength) {
		// Your code goes here
        return "0";

	}

    /** Returns a string representing the map of this language model. */
    public String toString() {
		StringBuilder str = new StringBuilder();
		for (String key : CharDataMap.keySet()) {
			List keyProbs = CharDataMap.get(key);
			str.append(key + " : " + keyProbs + "\n");
		}
		return str.toString();
	}



    public static void main(String[] args) {
    
        /* calculate probabilities test
        LanguageModel model = new LanguageModel(3);
        String word = "computer_science";
        List list = new List(); 
        for (int i = 0; i < word.length(); i++) {
            list.update(word.charAt(word.length() - 1 - i));
        }
        model.calculateProbabilities(list);
        System.out.println(list);


        String resString = "((o 1 0.0625 0.0625) (m 1 0.0625 0.125) (p 1 0.0625 0.1875) (u 1 0.0625 0.25) (t 1 0.0625 0.3125) (r 1 0.0625 0.375) (_ 1 0.0625 0.4375) (s 1 0.0625 0.5) (i 1 0.0625 0.5625) (n 1 0.0625 0.625) (c 3 0.1875 0.8125) (e 3 0.1875 1.0))";
        System.out.println(resString);


        boolean res = list.toString().equals(resString);
        System.out.println(res);


        if (!res){
            System.out.println("Expected: " + resString);
            System.out.println("Actual: " + list.toString());
        }      
*/

    /* train test
       LanguageModel lm = new LanguageModel(2);
       lm.train("galileocorpus.txt");
       System.out.println(lm);
    */

    /* stress test
        String word = "committee ";
        List list = new List();

        for (int i = 0; i<word.length(); i++) {
            System.out.println(i);
            list.update(word.charAt(i));
            System.out.println(list);
        }        
    
        int[] arr = new int[word.length()];
        LanguageModel lm = new LanguageModel(10);
        lm.calculateProbabilities(list);  
        System.out.println(list);
  
        for (int i=1; i<=10000000; i++) {
            char ch = lm.getRandomChar(list);
            switch (ch) {
                case 'c': { arr[0]++; break; }
                case 'o': { arr[1]++; break; }
                case 'm': { arr[2]++; break; }
                case 'i': { arr[3]++; break; }
                case 't': { arr[4]++; break; }
                case 'e': { arr[5]++; break; }
                case ' ': { arr[6]++; break; }
            }
       }
        for (int i=0; i<7; i++) {
            System.out.print(arr[i] + ", ");
        }
        System.out.println();
        System.out.println();
        System.out.println();
        */
    }
}
