package edu.uci.ics.jindongy.service.billing.models.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;


 class Amount{
    @JsonProperty("total")
    String total;
    @JsonProperty("currency")
    String currency;

     public Amount(String total, String currency) {
         this.total = total;
         this.currency = currency;
     }
 }

 class TransactionFee{
    @JsonProperty("value")
    String value;
    @JsonProperty("currency")
    String currency;

     public TransactionFee(String value, String currency) {
         this.value = value;
         this.currency = currency;
     }
 }

class Item{
    @JsonProperty("email")
    String email;
    @JsonProperty("movie_id")
    String movie_id;
    @JsonProperty("quantity")
    int quantity;
    @JsonProperty("unit_price")
    float unit_price;
    @JsonProperty("discount")
    float discount;
    @JsonProperty("sale_date")
    String sale_date;

    public Item(String email, String movie_id, int quantity, float unit_price, float discount, String sale_date) {
        this.email = email;
        this.movie_id = movie_id;
        this.quantity = quantity;
        this.unit_price = unit_price;
        this.discount = discount;
        this.sale_date = sale_date;
    }
}

public class transactionModel{
    @JsonProperty("capture_id")
    public String getCapture_id() {
        return this.capture_id; }
    public void setCapture_id(String capture_id) {
        this.capture_id = capture_id; }
    String capture_id;
    @JsonProperty("state")
    public String getState() {
        return this.state; }
    public void setState(String state) {
        this.state = state; }
    String state;
    @JsonProperty("amount")
    public Amount getAmount() {
        return this.amount; }
    public void setAmount(Amount amount) {
        this.amount = amount; }
    Amount amount;
    @JsonProperty("transaction_fee")
    public TransactionFee getTransaction_fee() {
        return this.transaction_fee; }
    public void setTransaction_fee(TransactionFee transaction_fee) {
        this.transaction_fee = transaction_fee; }
    TransactionFee transaction_fee;
    @JsonProperty("create_time")
    public String getCreate_time() {
        return this.create_time; }
    public void setCreate_time(String create_time) {
        this.create_time = create_time; }
    String create_time;
    @JsonProperty("update_time")
    public String getUpdate_time() {
        return this.update_time; }
    public void setUpdate_time(String update_time) {
        this.update_time = update_time; }
    String update_time;
    @JsonProperty("items")
    List<Item> items = new ArrayList<>();

    public transactionModel(String capture_id, String state, String amountTotal, String amountCurrency, String feeValue, String feeCurrency,
                            String create_time, String update_time) {
        this.capture_id = capture_id;
        this.state = state;
        this.amount = new Amount( amountTotal,  amountCurrency);
        this.transaction_fee = new TransactionFee(feeValue,feeCurrency) ;
        this.create_time = create_time;
        this.update_time = update_time;
    }

    public void addItems(String email, String movie_id, int quantity, float unit_price, float discount, String sale_date) {
        items.add(new Item(email,movie_id,quantity,unit_price,discount,sale_date));
    }
}


