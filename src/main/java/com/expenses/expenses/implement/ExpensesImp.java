package com.expenses.expenses.implement;

import com.expenses.expenses.DBConnection;
import com.expenses.expenses.interfaces.ExpensesInt;
import com.expenses.expenses.models.Expenses;

import java.sql.*;
import java.util.ArrayList;

public class ExpensesImp implements ExpensesInt {
    @Override
    public ArrayList<Expenses> getAllExpenses(long userId) {
        //select * from expenses where userid=userId
        //create empty array list of expenses
        //while rs.next, create new expense model
        //add all items to model
        //add model to arraylist

        //return arratlist

        Connection con = DBConnection.connect();
        String query = "select * from expenses where userid="+userId;
        ArrayList expenses = new ArrayList<>();

        try{
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()){
                Expenses expense = new Expenses();
                expense.setId(rs.getLong("id"));
                expense.setType(rs.getString("type"));
                expense.setName(rs.getString("name"));
                expense.setPrice(rs.getDouble("price"));
                expense.setDate(rs.getString("date"));
                expense.setCategory(rs.getString("category"));
                expense.setUserId(rs.getLong("userId"));
                expenses.add(expense);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return expenses;
    }

    @Override
    public Expenses getOneExpense(long id) {
        //select * from expenses where id=id
        //create new expense model
        //while rs.next add items to model
        //return model
        Connection con = DBConnection.connect();
        String query = "select * from expenses where id="+id;
        Expenses exp = new Expenses();

        try{
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()){
                exp.setId(rs.getLong("id"));
                exp.setName(rs.getString("name"));
                exp.setType(rs.getString("type"));
                exp.setPrice(rs.getDouble("price"));
                exp.setDate(rs.getString("date"));
                exp.setCategory(rs.getString("category"));
                exp.setUserId(rs.getLong("userId"));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return exp;
    }

    @Override
    public void addExpense(Expenses exp) {
            //insert into expenses values(null, ?, ?, ?, ?)
        Connection con = DBConnection.connect();

        try{

            PreparedStatement pstmt = con.prepareStatement("insert into expenses values(null,?, ?, ?, ?, ?, ?)");
            pstmt.setString(1, exp.getType());
            pstmt.setString(2, exp.getName());
            pstmt.setDouble(3, exp.getPrice());
            pstmt.setString(4, exp.getDate());
            pstmt.setString(5, exp.getCategory());
            pstmt.setLong(6, exp.getUserId());
            int result = pstmt.executeUpdate();
            if(result != 0){
                System.out.println("Expense added");
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateExpense(Expenses exp) {
        Connection con = DBConnection.connect();
        String query = "update expenses set type=?, name=?, price=?, date=?, category = ?, userId=? where id="+exp.getId();

        try{
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, exp.getType());
            pstmt.setString(2, exp.getName());
            pstmt.setDouble(3, exp.getPrice());
            pstmt.setString(4, exp.getDate());
            pstmt.setString(5, exp.getCategory());
            pstmt.setLong(6, exp.getUserId());
            int result = pstmt.executeUpdate();
            if(result != 0){
                System.out.println("Expense updated");
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteExpense(long id) {
            Connection con = DBConnection.connect();
            try{
                Statement stmt = con.createStatement();
                stmt.executeUpdate("delete from expenses where id="+id);
            }catch(SQLException e){
                System.out.println(e.getMessage());
        }
    }
}
