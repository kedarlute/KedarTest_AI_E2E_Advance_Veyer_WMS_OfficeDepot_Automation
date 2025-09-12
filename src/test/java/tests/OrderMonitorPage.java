package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class OrderMonitorPage extends BasePage {

    private final By orderMonitorButton = By.xpath("//nav//ul//a[contains(.,'Orders Monitor')]");
    private final By orderMonitorTab = By.xpath("//button[normalize-space()='Orders']");
    private final By createButton = By.xpath("//button[normalize-space()='Create']");
    private final By numberInput = By.name("number");
    private final By taskIdInput = By.name("taskid");
    private final By poInput = By.name("customerPurchaseOrder");
    private final By valueInput = By.name("value");
    private final By notesInput = By.name("notes");
    private final By transportDropdown = By.xpath("//label[contains(.,'Transportation Method')]/following-sibling::div");

    private final By shippingAddressNameInput = By.name("shipAddress.name");
    private final By shippingAddressAddress1Input = By.name("shipAddress.address1");
    private final By shippingAddressAddress2Input = By.name("shipAddress.address2");
    private final By shippingAddressCityInput = By.name("shipAddress.city");
    private final By shippingAddressStateInput = By.name("shipAddress.state");
    private final By shippingAddressZipCodeInput = By.name("shipAddress.postalCode");
    private final By shippingAddressPhoneInput = By.name("shipAddress.phone");
    private final By shippingAddressEmailInput = By.name("shipAddress.email");
    private final By shippingAddressCountryInput = By.name("shipAddress.country");

    private final By billingAddressNameInput = By.name("billAddress.name");
    private final By billingAddressAddress1Input = By.name("billAddress.address1");
    private final By billingAddressAddress2Input = By.name("billAddress.address2");
    private final By billingAddressCityInput = By.name("billAddress.city");
    private final By billingAddressStateInput = By.name("billAddress.state");
    private final By billingAddressZipCodeInput = By.name("billAddress.postalCode");
    private final By billingAddressPhoneInput = By.name("billAddress.phone");
    private final By billingAddressEmailInput = By.name("billAddress.email");
    private final By billingAddressCountryInput = By.name("billAddress.country");

    private final By addNewOrderLineButton = By.xpath("//button[normalize-space()='Add']");
    private final By skuInput = By.name("lines[0].number");
    private final By skuOption = By.xpath("//div[@role='presentation' and @data-popper-placement and contains(@style, 'position: absolute')]");
    private final By packageQtyInput = By.name("lines[0].requestedQuantity");
    private final By salesPriceInput = By.name("lines[0].salePrice");
    private final By saveButton = By.xpath("//button[normalize-space()='Save']");
    private final By successMessage = By.id("notistack-snackbar");
    private final By hiddenButtonFile = By.id("icon-button-file");
    private final By errorImportDialogBox = By.xpath("//div[@id='alert-dialog-slide-title']//h2");

    public OrderMonitorPage(WebDriver driver) {
        super(driver);
    }

    public void clickCreateButton() {
        clickElement(createButton);
    }

    public void clickOrderMonitorButton() {
        clickElement(orderMonitorButton);
    }

    public void clickOrderMonitorTab() {
        clickElement(orderMonitorTab);
    }

    public void enterShippingOrderDetails(String number, String taskId, String purchaseOrder, String orderValue, String notes, String transportMethod) {
        type(numberInput, number);
        type(taskIdInput, taskId);
        type(poInput, purchaseOrder);
        type(valueInput, orderValue);
        type(notesInput, notes);
        clickElement(transportDropdown);
        clickElement(By.xpath("//li[normalize-space()='" + transportMethod + "']"));
    }

    public void enterShippingAddressDetails(String name, String address1, String address2, String city,
            String state, String zip, String phone, String email, String country) {
        type(shippingAddressNameInput, name);
        type(shippingAddressAddress1Input, address1);
        type(shippingAddressAddress2Input, address2);
        type(shippingAddressCityInput, city);
        type(shippingAddressStateInput, state);
        type(shippingAddressZipCodeInput, zip);
        type(shippingAddressPhoneInput, phone);
        type(shippingAddressEmailInput, email);
        type(shippingAddressCountryInput, country);
    }

    public void enterBillingAddressDetails(String name, String address1, String address2, String city,
            String state, String zip, String phone, String email, String country) {
        type(billingAddressNameInput, name);
        type(billingAddressAddress1Input, address1);
        type(billingAddressAddress2Input, address2);
        type(billingAddressCityInput, city);
        type(billingAddressStateInput, state);
        type(billingAddressZipCodeInput, zip);
        type(billingAddressPhoneInput, phone);
        type(billingAddressEmailInput, email);
        type(billingAddressCountryInput, country);
    }

    public void clickAddNewOrderLineButton() {
        clickElement(addNewOrderLineButton);
    }

    public void selectSkuOrder(String sku) {
        type(skuInput, sku);
        clickElement(skuInput);
        hover(skuInput);
        isDisplayed(skuOption);
        type(skuInput, Keys.ARROW_DOWN);
        type(skuInput, Keys.ENTER);
    }

    public void enterPackageQuantity(int quantity) {
        type(packageQtyInput, String.valueOf(quantity));
    }

    public void enterSalesPrice(int price) {
        type(salesPriceInput, String.valueOf(price));
    }

    public void clickSaveButton() {
        clickElement(saveButton);
    }

    public boolean isSuccessMessageDisplayed() {
        return isDisplayed(successMessage);
    }

    public void uploadCsvFile(String string) {
        addAttributeToElement(hiddenButtonFile, "style", "display: block;");
        type(hiddenButtonFile, string);
        
    }

    public boolean isErrorMessageDisplayed() {
        return isDisplayed(errorImportDialogBox);
    }

    public String getErrorMessageText() {
        return getText(errorImportDialogBox);
    }
}
