package com.zero.pelican.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.zero.pelican.main.lexer.Lexer;
import com.zero.pelican.main.lexer.Token;

/*Это основной класс пеликана тут происходит интерактивный режим + запуск из файла*/

public class Pelican {
    // Тут определяем статическую переменную, говорящую об ошибке
    static boolean hadError = false;

    // основная функция
    public static void main(String[] args) throws IOException{
        // Проверяем длину аргументов
        if (args.length > 1) {
            System.out.println("Usage: pelican [path to script]");
            System.exit(64);
        } else if (args.length == 1) {
            if (args[0].equals("help")){
                System.out.println(""" 
                        Usage - pelican [path to script] - to run the file
                        Usage - pelican - to run interactively
                        Usage - pelican help - to output this help.
                        """);
            } else {
                runFile(args[0]);
            }
        } else {
            runPrompt();

        }
    }

    private static void runFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));
        if (hadError) System.exit(65);
    }


    private static void runPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        while (true) {
            System.out.print(">> ");
            String line = reader.readLine();
            if (line == null) break;
            run(line);
            hadError = false;
        }
    }

    private static void run(String source) {
        Lexer lexer = new Lexer(source);
        List<Token> tokens = lexer.tokenize();
        for (Token token : tokens) {
            System.out.println(token);
        }
    }

    public static void error(int line, String message) {
        report(line, "", message);
    }

    private static void report(int line, String where, String message) {
        System.err.println(
                "[line " + line + "] Error" + where + ": " + message);
        hadError = true;
    }
}
