/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import java.io.Serializable;

/**
 *
 * @author Cartman
 */
public enum Operation implements Serializable {

    LOGIN,
    GET_ALL_MANUFACTURERS,
    GET_ALL_PRODUCTS,
    ADD_PRODUCT,
    EDIT_PRODUCT,
    DELETE_PRODUCT,
    ADD_INVOICE, ADD_CATEGORY, GET_ALL_CATEGORIES, ADD_MONTHLY_BUDGET, GET_ALL_BUDGETITEMS, GET_MONTHLY_BUDGET_BY_DATE, ADD_EXPENSE, EDIT_MONTHLY_BUDGET, EDIT_BUDGET_ITEM, DELETE_BUDGET_ITEM, ADD_BUDGET_ITEM, GET_ALL_EXPENSES, DELETE_EXPENSE, GET_MONTHLY_BUDGET_BY_DATE_USER, GET_MONTHLY_BUDGET_BY_USER, GET_ALL_MONTHLY_BUDGETS, GET_ALL_MONTHLY_BUDGET_ITEMS, EDIT_ALL_BUDGET_ITEMS
}
