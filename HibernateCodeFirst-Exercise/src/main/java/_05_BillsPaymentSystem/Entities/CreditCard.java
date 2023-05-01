package _05_BillsPaymentSystem.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.Month;
import java.time.Year;

@Entity(name = "credit_cards")
public class CreditCard extends BillingDetail {
    @Column(name = "card_type", nullable = false)
    private String cardType;
    @Column(name = "expiration_month", nullable = false)
    private Month expirationMonth;
    @Column(name = "expiration_year", nullable = false)
    private Year expirationYear;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "billing_type")
    private final BillingType billingType;

    public CreditCard() {
        this.billingType=BillingType.CREDIT_CARD;
    }

    public CreditCard(String number, String cardType, Month expirationMonth, Year expirationYear) {
        setNumber(number);
        this.cardType = cardType;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
        this.billingType = BillingType.CREDIT_CARD;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public Month getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(Month expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    public Year getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(Year expirationYear) {
        this.expirationYear = expirationYear;
    }

    public BillingType getBillingType() {
        return billingType;
    }
}
