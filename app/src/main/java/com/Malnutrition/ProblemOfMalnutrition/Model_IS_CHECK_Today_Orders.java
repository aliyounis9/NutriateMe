package com.Malnutrition.ProblemOfMalnutrition;

public class Model_IS_CHECK_Today_Orders
{
    Boolean is_checked_today_order;
    Boolean breakfast_ordered;
    Boolean lunch_ordered;
    Boolean Dinner_ordered;


    public Boolean getIs_checked_today_order() {
        return is_checked_today_order;
    }

    public void setIs_checked_today_order(Boolean is_checked_today_order) {
        this.is_checked_today_order = is_checked_today_order;
    }

    public Model_IS_CHECK_Today_Orders(Boolean breakfast_ordered, Boolean lunch_ordered, Boolean dinner_ordered) {
        this.breakfast_ordered = breakfast_ordered;
        this.lunch_ordered = lunch_ordered;
        Dinner_ordered = dinner_ordered;
    }

    public Boolean getBreakfast_ordered() {
        return breakfast_ordered;
    }

    public void setBreakfast_ordered(Boolean breakfast_ordered) {
        this.breakfast_ordered = breakfast_ordered;
    }

    public Boolean getLunch_ordered() {
        return lunch_ordered;
    }

    public void setLunch_ordered(Boolean lunch_ordered) {
        this.lunch_ordered = lunch_ordered;
    }

    public Boolean getDinner_ordered() {
        return Dinner_ordered;
    }

    public void setDinner_ordered(Boolean dinner_ordered) {
        Dinner_ordered = dinner_ordered;
    }

    public Model_IS_CHECK_Today_Orders(Boolean is_checked_today_order) {
        this.is_checked_today_order = is_checked_today_order;
    }

    public Model_IS_CHECK_Today_Orders(Boolean is_checked_today_order, Boolean breakfast_ordered, Boolean lunch_ordered, Boolean dinner_ordered) {
        this.is_checked_today_order = is_checked_today_order;
        this.breakfast_ordered = breakfast_ordered;
        this.lunch_ordered = lunch_ordered;
        Dinner_ordered = dinner_ordered;
    }

}
