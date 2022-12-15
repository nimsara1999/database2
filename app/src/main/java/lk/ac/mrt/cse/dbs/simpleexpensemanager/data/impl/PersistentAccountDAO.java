/*
 * Copyright 2015 Department of Computer Science and Engineering, University of Moratuwa.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *                  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

/**
 * This is an In-Memory implementation of the AccountDAO interface. This is not a persistent storage. A HashMap is
 * used to store the account details temporarily in the memory.
 */
public class PersistentAccountDAO implements AccountDAO {
    private DataBaseHelper dbHelper;

    public PersistentAccountDAO(Context context) {
        dbHelper=new DataBaseHelper(context);
    }

    @Override
    public List<String> getAccountNumbersList() {
        Cursor cursor = dbHelper.getData();
        ArrayList<String> AccountNames = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                AccountNames.add(cursor.getString(0));
            }while(cursor.moveToNext());
            return AccountNames;
        }
        return null;
    }

    @Override
    public List<Account> getAccountsList() {
        Cursor cursor = dbHelper.
    }
        return new ArrayList<>(accounts.values());
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        if (accounts.containsKey(accountNo)) {
            return accounts.get(accountNo);
        }
        String msg = "Account " + accountNo + " is invalid.";
        throw new InvalidAccountException(msg);
    }

    @Override
    public void addAccount(Account account) {
        dbHelper.addNewAccount(account);
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        Cursor cursor = dbHelper.removeAccountData(accountNo);
        if (cursor == null) {
            String msg = "Account " + accountNo + " is invalid.";
            throw new InvalidAccountException(msg);
        }
        dbHelper.removeAccountData(cursor.getString(0));
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
        Cursor cursor = dbHelper.xxxxxxxxx(accountNo);
        if (cursor == null) {
            String msg = "Account " + accountNo + " is invalid.";
            throw new InvalidAccountException(msg);
        }
        if (cursor.moveToFirst()) {
            do {
                Account account = new Account(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getDouble(1));
                // specific implementation based on the transaction type
                switch (expenseType) {
                    case EXPENSE:
                        account.setBalance(account.getBalance() - amount);
                        break;
                    case INCOME:
                        account.setBalance(account.getBalance() + amount);
                        break;
                }

            }
        }
    }
