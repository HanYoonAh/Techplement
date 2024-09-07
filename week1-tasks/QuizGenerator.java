package com.techplementInternship.quizz;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuizGenerator {
    private List<Quiz> quizzes;
    private Scanner scanner;

    public QuizGenerator() {
        quizzes = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("1. Create a new quiz");
            System.out.println("2. Take a quiz");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    createQuiz();
                    break;
                case 2:
                    takeQuiz();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void createQuiz() {
        System.out.print("Enter the title of the quiz: ");
        String title = scanner.nextLine();
        Quiz quiz = new Quiz(title);

        while (true) {
            System.out.print("Enter a question (or type 'done' to finish): ");
            String questionText = scanner.nextLine();
            if (questionText.equalsIgnoreCase("done")) {
                break;
            }

            List<String> options = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                System.out.print("Enter option " + (i + 1) + ": ");
                options.add(scanner.nextLine());
            }

            System.out.print("Enter the index of the correct answer (1-4): ");
            int correctAnswerIndex = Integer.parseInt(scanner.nextLine()) - 1;

            Question question = new Question(questionText, options, correctAnswerIndex);
            quiz.addQuestion(question);
        }

        quizzes.add(quiz);
        System.out.println("Quiz created successfully!");
    }

    private void takeQuiz() {
        if (quizzes.isEmpty()) {
            System.out.println("No quizzes available.");
            return;
        }

        System.out.println("Available quizzes:");
        for (int i = 0; i < quizzes.size(); i++) {
            System.out.println((i + 1) + ". " + quizzes.get(i).getTitle());
        }

        System.out.print("Select a quiz to take: ");
        int quizChoice = Integer.parseInt(scanner.nextLine()) - 1;

        if (quizChoice < 0 || quizChoice >= quizzes.size()) {
            System.out.println("Invalid quiz selection.");
            return;
        }

        Quiz selectedQuiz = quizzes.get(quizChoice);
        List<Question> questions = selectedQuiz.getQuestions();
        int score = 0;

        for (Question question : questions) {
            System.out.println("\n" + question.getQuestionText());
            List<String> options = question.getOptions();
            for (int i = 0; i < options.size(); i++) {
                System.out.println((i + 1) + ". " + options.get(i));
            }

            System.out.print("Your answer: ");
            int answer = Integer.parseInt(scanner.nextLine()) - 1;

            if (question.isCorrectAnswer(answer)) {
                System.out.println("Correct!");
                score++;
            } else {
                System.out.println("Incorrect. The correct answer was: " +
                        options.get(question.getCorrectAnswerIndex()));
            }
        }

        System.out.println("\nQuiz completed! Your score: " + score + "/" 
        + questions.size());
    }

    public static void main(String[] args) {
        QuizGenerator quizGenerator = new QuizGenerator();
        quizGenerator.start();
    }
}

