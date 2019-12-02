package exchange;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MoneyTest {
    @Test
    void testMultiplication() {
        Money fiveDollar = Money.dollar(5);
        assertThat(fiveDollar.times(2)).isEqualTo(Money.dollar(10));
        assertThat(fiveDollar.times(3)).isEqualTo(Money.dollar(15));

        Money fiveFran = Money.fran(5);
        assertThat(fiveFran.times(2)).isEqualTo(Money.fran(10));
        assertThat(fiveFran.times(3)).isEqualTo(Money.fran(15));

    }

    @Test
    void testEquals() {
        assertThat(Money.dollar(5)).isEqualTo(Money.dollar(5));
        assertThat(Money.dollar(5)).isNotEqualTo(Money.dollar(6));

        assertThat(Money.fran(5)).isEqualTo(Money.fran(5));
        assertThat(Money.fran(5)).isNotEqualTo(Money.fran(6));

        assertThat(Money.dollar(5)).isNotEqualTo(Money.fran(5));
    }

    @Test
    void testReduceMoney() {
        Bank bank = new Bank();
        Money result = bank.reduce(Money.dollar(1), "USD");
        assertThat(Money.dollar(1)).isEqualTo(result);
    }

    @Test
    void testReduce() {
        Expression sum = new Sum(Money.dollar(5), Money.dollar(5));
        Bank bank = new Bank();
        Money reduce = bank.reduce(sum, "USD");
        assertThat(Money.dollar(10)).isEqualTo(reduce);
        assertThat(Money.dollar(10)).isEqualTo(reduce);
        assertThat(Money.dollar(10)).isEqualTo(reduce);
    }

    @Test
    void testResultReturnsSum() {
        Sum sum = (Sum) new Sum(Money.dollar(5), Money.dollar(5));
        assertThat(sum.augend).isEqualTo(Money.dollar(5));
        assertThat(sum.addend).isEqualTo(Money.dollar(5));
    }

    @Test
    void testReduceMoneyDifferentCurrency() {
        Bank bank = new Bank();
        bank.addRate("CHF", "USD", 2);
        Money result = bank.reduce(Money.fran(2), "USD");
        assertThat(Money.dollar(1)).isEqualTo(result);
    }

    @Test
    void testMixedAddition() {
        Expression fiveBucks = Money.dollar(5);
        Expression tenFrans = Money.fran(10);
        Bank bank = new Bank();
        bank.addRate("CHF", "USD", 2);
        Money result = bank.reduce(fiveBucks.plus(tenFrans), "USD");
        assertThat(Money.dollar(10)).isEqualTo(result);
    }

    @Test
    void testSumPlusMoney() {
        Expression fiveBucks = Money.dollar(5);
        Expression tenFran = Money.fran(10);
        Bank bank = new Bank();
        Expression sum = new Sum(fiveBucks, tenFran).plus(fiveBucks);
        Money result = bank.reduce(sum, "USD");
        assertThat(Money.dollar(15)).isEqualTo(result);
    }
}