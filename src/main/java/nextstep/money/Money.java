package nextstep.money;

import java.util.Objects;

public class Money {
    public static final Money ZERO = Money.wons(0);

    private final Long amount;

    public static Money wons(long amount) {
        return new Money(amount);
    }

    private Money(long amount) {
        if (amount < 0L) {
            throw new IllegalArgumentException("음수는 허용하지 않습니다");
        }

        this.amount = amount;
    }

    public Long getAmount() {
        return amount;
    }

    public boolean isLessThan(Money other) {
        return this.amount.compareTo(other.amount) < 0;
    }

    public boolean isZero() {
        return this.equals(ZERO);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Money money = (Money) other;
        return Objects.equals(amount, money.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}
