package ru.netology.page

import com.codeborne.selenide.SelenideElement

class StartPage {

    private SelenideElement headingStart = $("h2.heading");
    private SelenideElement paymentButton = $$(".button").find(exactText("Купить"));
    private SelenideElement creditButton = $$(".button").find(exactText("Купить в кредит"));

    public StartPage() {
        headingStart.shouldBe(visible);
    }

    public PaymentPage payment() {
        paymentButton.click();
        return new PaymentPage();
    }

    public CreditPage paymentOnCredit() {
        creditButton.click();
        return new CreditPage();
    }
}
