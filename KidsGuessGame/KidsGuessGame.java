import java.util.Random;
import java.util.Scanner;

public class KidsGuessGame {

    private static final Random RAND = new Random();
    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("🎯 Welcome to the Kids Number Guessing Game! 🎯");
        boolean keepPlaying = true;

        while (keepPlaying) {
            int min = 1;
            int max = chooseRange(); // lets player choose difficulty
            int secret = RAND.nextInt(max - min + 1) + min;
            playRound(min, max, secret);

            System.out.print("🔄 Do you want to play again? (yes/no): ");
            String again = SC.nextLine().trim().toLowerCase();
            keepPlaying = again.equals("yes") || again.equals("y");
            System.out.println();
        }

        System.out.println("👋 Thanks for playing! See you next time!");
        SC.close();
    }

    // Let player choose range/difficulty
    private static int chooseRange() {
        System.out.println("\nChoose difficulty:");
        System.out.println("1) Easy (1 - 20)");
        System.out.println("2) Normal (1 - 50)");
        System.out.println("3) Hard (1 - 100)");
        System.out.print("Pick 1, 2, or 3 (press Enter for Normal): ");

        String choice = SC.nextLine().trim();
        switch (choice) {
            case "1": return 20;
            case "3": return 100;
            default:  return 50;
        }
    }

    // Single round of the game
    private static void playRound(int min, int max, int secret) {
        System.out.println("\nI have picked a number between " + min + " and " + max + ".");
        System.out.println("I'll give hints (hot/warm/cold) and fun guessing ideas along the way!");
        System.out.println("Type 'idea' if you want a playful guessing idea. Ready? Let's go!\n");

        int attempts = 0;
        boolean guessed = false;

        while (!guessed) {
            System.out.print("👉 Enter your guess (or 'idea'): ");
            String input = SC.nextLine().trim();

            if (input.equalsIgnoreCase("idea")) {
                System.out.println("💡 " + randomGuessingIdea(secret));
                continue;
            }

            int guess;
            try {
                guess = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("🚫 Please type a number or 'idea' for a fun clue!");
                continue;
            }

            if (guess < min || guess > max) {
                System.out.println("⚠️ Please guess between " + min + " and " + max + ".");
                continue;
            }

            attempts++;

            if (guess == secret) {
                System.out.println("🎉 Correct! You guessed it in " + attempts + " tries! 🏆");
                guessed = true;
            } else {
                // direction
                if (guess < secret) System.out.println("⬆️ Too low! Try higher.");
                else System.out.println("⬇️ Too high! Try lower.");

                // closeness category
                giveClosenessHint(guess, secret);

                // even/odd hint occasionally (every 2 attempts)
                if (attempts % 2 == 0) {
                    System.out.println("💡 Hint: The number is " + (secret % 2 == 0 ? "even ⚖" : "odd ⚙") + ".");
                }
            }
            System.out.println(); // blank line for readability
        }
    }

    // Give categories like Very Hot / Warm / Cool / Cold
    private static void giveClosenessHint(int guess, int secret) {
        int diff = Math.abs(secret - guess);
        if (diff <= 2) {
            System.out.println("🔥 Very hot! You are super close!");
        } else if (diff <= 6) {
            System.out.println("🌞 Warm — you're getting closer!");
        } else if (diff <= 12) {
            System.out.println("🌤 Cool — a bit farther away.");
        } else {
            System.out.println("❄️ Cold — you're far away, try a different area!");
        }
    }

    // Fun guessing ideas — playful and sometimes hinting without giving away the number
    private static String randomGuessingIdea(int secret) {
        String[] ideas = {
            "Is it the number of fingers on both hands of a friend? 🤔",
            "Maybe it's the number of your favourite snacks today 🍪",
            "Is it a small number you'd see on a dice? 🎲",
            "Could it be your lucky number? ✨",
            "Try thinking of how many pets you'd like 🐶🐱",
            "Is it higher than 10? (Try a number above 10) 🔎",
            "Think of your age plus 3 — is it close? 👧➕3",
            "Try a number that is even (2, 4, 6...) — just a thought! ⚖",
            "Is it a teen number (between 13 and 19)? 🧒",
            "Maybe try a number that ends with 5 — like 5, 15, 25... 💭"
        };
        // pick a random idea; sometimes give a slightly stronger clue
        int pick = RAND.nextInt(ideas.length);
        // 1 in 6 chance to give a stronger nudge (not revealing)
        if (RAND.nextInt(6) == 0) {
            if (secret <= 10) return "Little nudge: it's a smaller number (≤10).";
            if (secret <= 25) return "Little nudge: it's a medium number (between 11 and 25).";
            return "Little nudge: it's a bigger number (above 25).";
        }
        return ideas[pick];
    }
}
