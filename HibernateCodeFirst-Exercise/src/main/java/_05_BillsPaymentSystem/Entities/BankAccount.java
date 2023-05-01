package _05_BillsPaymentSystem.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity(name = "bank_accounts")
public class BankAccount extends BillingDetail {
    @Column(name = "bank_name",nullable = false)
    private String bankName;
    @Column(name = "swift_code",nullable = false)
    private String swiftCode;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "billing_type")
    private final BillingType billingType;

    public BankAccount() {
        this.billingType=BillingType.BANK_ACCOUNT;
    }

    public BankAccount(String number, String bankName, String swiftCode) {
        setNumber(number);
        this.bankName = bankName;
        this.swiftCode = swiftCode;
        this.billingType = BillingType.BANK_ACCOUNT;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public BillingType getBillingType() {
        return billingType;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }
}
