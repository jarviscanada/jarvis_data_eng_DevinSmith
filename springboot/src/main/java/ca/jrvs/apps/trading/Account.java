package ca.jrvs.apps.trading;

public class Account {
	
	int id;
	int traderId;
	double amount;

	//getters and setters

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTraderId() {
        return this.traderId;    
    }

    public void setTraderId(int traderId) {
        this.traderId = traderId;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

}