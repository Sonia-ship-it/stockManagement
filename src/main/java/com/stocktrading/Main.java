package com.stocktrading;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner sc=new Scanner(System.in);
        while(true) {
            System.out.print("\nEnter username: ");
            String username = sc.nextLine();
            if (username.matches("^[A-Za-z0-9_]{8,}$")) {
                System.out.println("\n===== STOCK TRADING PLATFORM =====\n");
                System.out.println("Welcome User " + username + "!\n");
                while (true) {
                    System.out.println("1. View Market Stocks");
                    System.out.println("2. Buy Stock");
                    System.out.println("3. Sell Stock");
                    System.out.println("4. View Portfolio");
                    System.out.println("5. Exit\n");
                    System.out.print("Enter your choice: ");
                    int choice = sc.nextInt();
                    sc.nextLine();
                    switch (choice) {
                        case 1 -> {
                            System.out.println("\nAvailable stocks:\n");
                            App.available();
                            System.out.println();
                        }
                        case 2 -> {
                            System.out.print("\nEnter stock symbol: ");
                            String symbol = sc.nextLine();
                            System.out.print("Enter quantity: ");
                            int quantity = sc.nextInt();
                            float boughtStock=App.boughtStock(symbol);
                            if(boughtStock==-1) {
                                System.out.println("\nPurchase failed!");
                                System.out.println("Enter correct symbol for stock\n");
                            }
                            else {
                                System.out.println("\n✔ Purchase successful!");
                                System.out.println("You bought " + quantity + " shares of " + symbol.toUpperCase() + " at $" + boughtStock + " each\n");
                                App.updatePort(symbol,quantity,boughtStock,username);
                            }
                        }
                        case 3 -> {
                            System.out.print("\nEnter stock symbol: ");
                            String symbol = sc.nextLine();
                            System.out.print("Enter quantity to sell: ");
                            int quantity = sc.nextInt();
                            float boughtStock=App.boughtStock(symbol);
                            int qAv=App.quantityAv(username,symbol);
                            if(boughtStock==-1) {
                                System.out.println("\nSale failed!");
                                System.out.println("Enter correct symbol for stock\n");
                            }
                            else if(qAv==-1) {
                                System.out.println("Sale failed!");
                                System.out.println("You don't have stock to sale");
                            }
                            else if(qAv < quantity) {
                                System.out.println("No enough quantity to sell!");
                            }
                            else {
                                System.out.println("\n✔ Sale successful!");
                                System.out.println("You sold " + quantity + " shares of " + symbol.toUpperCase() + "\n");
                                App.updatedPort(symbol,quantity,boughtStock,username);
                            }
                        }
                        case 4 -> App.viewPort(username);
                        case 5 -> {
                            System.out.println("Thanks for using stock management!");
                            return;
                        }
                        default -> System.out.println("Invalid input");
                    }
                }
            } else {
                System.out.println("❌ Invalid username");
                System.out.println("Rules:");
                System.out.println("- At least 8 characters");
                System.out.println("- Only letters, numbers, and underscore (_)");
            }
        }
    }
}
