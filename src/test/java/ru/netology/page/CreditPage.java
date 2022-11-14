package ru.netology.page;


import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;


import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

    public class CreditPage {

        private final SelenideElement cardNumberField = $("input[placeholder='0000 0000 0000 0000']");
        private final SelenideElement monthField = $("input[placeholder='08']");
        private final SelenideElement yearField = $("input[placeholder='22']");
        private final SelenideElement cvcField = $("input[placeholder='999']");
        private final SelenideElement ownerField = $$(".input__control").get(3);
        private final SelenideElement continueButton = $$(".button").find(exactText("Продолжить"));

        public CreditPage() {
            SelenideElement headingCredit = $$("h3.heading").find(exactText("Кредит по данным карты"));
            headingCredit.shouldBe(visible);
        }

        public void getFillCardDetails(DataHelper cardInfo) {
            cardNumberField.setValue(cardInfo.getNumberCard());
            monthField.setValue(cardInfo.getMonth());
            yearField.setValue(cardInfo.getYear());
            ownerField.setValue(cardInfo.getOwnerCard());
            cvcField.setValue(cardInfo.getCvc());
            continueButton.click();
        }

        public void successfulPaymentCreditCard() {
            $(".notification_status_ok")
                    .shouldHave(text("Успешно Операция одобрена Банком.")).shouldBe(visible);
        }

        public void invalidPaymentCreditCard() {
            $(".notification_status_error")
                    .shouldHave(text("Ошибка! Банк отказал в проведении операции.")).shouldBe(visible);
        }

        public void checkInvalidFormat() {
            $(".input__sub").shouldBe(visible).shouldHave(text("Неверный формат"));
        }

        public void checkInvalidCardValidityPeriod() {
            $(".input__sub").shouldBe(visible)
                    .shouldHave(text("Неверно указан срок действия карты"));
        }

        public void checkCardExpired () {
            $(".input__sub").shouldBe(visible)
                    .shouldHave(text("Истёк срок действия карты"));
        }

        public void checkInvalidOwner() {
            $(".input__sub").shouldBe(visible)
                    .shouldHave(text("Введите имя и фамилию, указанные на карте"));
        }

        public void checkEmptyField() {
            $(".input__sub").shouldBe(visible)
                    .shouldHave(text("Поле обязательно для заполнения"));
        }

        public void incorrectOwner() {
            $(".input__sub").shouldBe(visible)
                    .shouldHave(text("Значение поля может содержать только латинские буквы и дефис"));
        }

        public void checkAllFieldsAreRequired() {
            $$(".input__sub").shouldHave(CollectionCondition.size(5))
                    .shouldHave(CollectionCondition.texts("Поле обязательно для заполнения"));
        }

    }

