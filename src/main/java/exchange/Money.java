package exchange;

import java.util.Objects;

public class Money implements Expression {
    protected int amount;
    protected String currency;

    Money times(int times) {
        return new Money(this.amount * times, currency);
    }

    Money(int amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    static Money dollar(int amount) {
        return new Money(amount, "USD");
    }

    static Money fran(int amount) {
        return new Money(amount, "CHF");
    }

    public Expression plus(Expression addend) {
        return new Sum(this, addend);
    }

    @Override
    public Money reduce(Bank bank, String currency) {
        int rate = bank.rate(this.currency, currency);
        return new Money(amount / rate, currency);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        Money money = ( Money ) o;
        return amount == money.amount && Objects.equals(currency, money.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }

    @Override
    public String toString() {
        return "Money{" + "amount=" + amount + ", currency='" + currency + '\'' + '}';
    }
}
